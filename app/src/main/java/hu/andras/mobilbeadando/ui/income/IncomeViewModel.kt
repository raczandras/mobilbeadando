package hu.andras.mobilbeadando.ui.income

import android.app.Application
import hu.andras.mobilbeadando.database.Income
import hu.andras.mobilbeadando.database.TransactionDatabaseDao
import androidx.lifecycle.*
import hu.andras.mobilbeadando.util.Util
import kotlinx.coroutines.launch

class IncomeViewModel(
        val database: TransactionDatabaseDao,
        application: Application
) : AndroidViewModel(application) {


    private val util = Util()

    val incomes = database.getAllIncomes()
    val description = MutableLiveData<String>("")
    val amount = MutableLiveData<String>("")

    private var _showAddedSnackBarEvent = MutableLiveData<Boolean?>()
    val showAddedSnackBarEvent: LiveData<Boolean?>
        get() = _showAddedSnackBarEvent


    private var _showWrongSnackBarEvent = MutableLiveData<Boolean?>()
    val showWrongSnackBarEvent: LiveData<Boolean?>
        get() = _showWrongSnackBarEvent

    private var _closeKeyboard = MutableLiveData<Boolean?>()
    val closeKeyboard: LiveData<Boolean?>
        get() = _closeKeyboard


    fun onAddClicked(){
        _closeKeyboard.value = true
        if(checkData()) {
            viewModelScope.launch {
                val income = Income(0L, System.currentTimeMillis(), amount.value!!.toInt(), description.value.toString())
                insert(income)
            }
            resetTexts()
            _showAddedSnackBarEvent.value = true
        }

    }

    fun doneShowingSnackbar() {
        _showAddedSnackBarEvent.value = null
        _showWrongSnackBarEvent.value = null
    }


    private fun checkData(): Boolean {
        if(description.value!!.isEmpty() || amount.value!!.isEmpty()){
            _showWrongSnackBarEvent.value = true
            return false
        }
        return true
    }

    private suspend fun insert( income: Income){
        database.insert(income)
    }

    private fun resetTexts(){
        description.value = ""
        amount.value = ""
    }


}