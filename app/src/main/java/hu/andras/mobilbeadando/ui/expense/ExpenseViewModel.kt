package hu.andras.mobilbeadando.ui.expense

import android.app.Application
import androidx.lifecycle.*
import hu.andras.mobilbeadando.database.Expense
import hu.andras.mobilbeadando.database.TransactionDatabaseDao
import hu.andras.mobilbeadando.util.Util
import kotlinx.coroutines.launch

class ExpenseViewModel(
        val database: TransactionDatabaseDao,
        application: Application
) : AndroidViewModel(application) {

    private val util = Util()

    val expenses = database.getAllExpenses()
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
                val expense = Expense(0L, System.currentTimeMillis(), amount.value!!.toInt(), description.value.toString())
                insert(expense)
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

    private suspend fun insert( expense: Expense){
        database.insert(expense)
    }

    private fun resetTexts(){
        description.value = ""
        amount.value = ""
    }

}