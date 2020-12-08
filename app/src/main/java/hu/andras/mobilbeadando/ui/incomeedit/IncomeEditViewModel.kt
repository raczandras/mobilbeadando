package hu.andras.mobilbeadando.ui.incomeedit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.andras.mobilbeadando.database.Income
import hu.andras.mobilbeadando.database.TransactionDatabaseDao
import kotlinx.coroutines.launch

class IncomeEditViewModel(
        val database: TransactionDatabaseDao,
        application: Application
) : AndroidViewModel(application) {

    val description = MutableLiveData<String>("")
    val updateId = MutableLiveData<String>("")
    val amount = MutableLiveData<String>("")
    val deleteId = MutableLiveData<String>("")

    private var _showUnknownIdSnackBarEvent = MutableLiveData<Boolean?>()
    val showUnknownIdSnackbarEvent: LiveData<Boolean?>
        get() = _showUnknownIdSnackBarEvent

    private var _showWrongSnackBarEvent = MutableLiveData<Boolean?>()
    val showWrongSnackBarEvent: LiveData<Boolean?>
        get() = _showWrongSnackBarEvent

    private var _showDeleteSnackBar = MutableLiveData<Boolean?>()
    val showSuccessfullDeleteSnackbar: LiveData<Boolean?>
        get() = _showDeleteSnackBar

    private var _showDeleteAllSnackBar = MutableLiveData<Boolean?>()
    val showSuccessfullDeleteAllSnackbar: LiveData<Boolean?>
        get() = _showDeleteAllSnackBar

    private var _showUpdateSnackBar = MutableLiveData<Boolean?>()
    val showUpdateSnackBar: LiveData<Boolean?>
        get() = _showUpdateSnackBar

    private var _closeKeyboard = MutableLiveData<Boolean?>()
    val closeKeyboard: LiveData<Boolean?>
        get() = _closeKeyboard

    fun onUpdateClicked(){
        _closeKeyboard.value = true

        if(checkUpdateData()) {
            viewModelScope.launch {
                val income = Income(updateId.value!!.toLong(), System.currentTimeMillis(), amount.value!!.toInt(), description.value.toString())
                update(income)
            }
            _showUpdateSnackBar.value = true
            resetTexts()
        }
    }

    private suspend fun update(income: Income) {
        database.update(income)
    }

    private fun checkUpdateData(): Boolean {
        if(updateId.value!!.isEmpty() || amount.value!!.isEmpty() || description.value!!.isEmpty()){
            _showWrongSnackBarEvent.value = true
            return false
        }
        if( database.queryIncomeId(updateId.value!!.toLong()).value == false ){
            _showUnknownIdSnackBarEvent.value = true
            return false
        }
        return true
    }

    fun onDeleteClicked(){
        _closeKeyboard.value = true

        if(checkDeleteData()){
            viewModelScope.launch {
                delete()
            }
            _showDeleteSnackBar.value = true
            resetTexts()
        }
    }

    private suspend fun delete() {
        database.deleteIncome(deleteId.value!!.toLong())
    }

    private fun checkDeleteData(): Boolean {
        _showWrongSnackBarEvent.value = true
        if(deleteId.value!!.isEmpty()){
            return false

        }
        if(database.queryIncomeId(deleteId.value!!.toLong()).value == false){
            _showUnknownIdSnackBarEvent.value = true
            return false
        }
        return true
    }

    fun onDeleteAllClicked(){
        viewModelScope.launch {
            deleteAll()
        }
        _showDeleteAllSnackBar.value = true
    }

    private suspend fun deleteAll() {
        database.deleteAllIncome()
    }

    fun doneShowingSnackbar() {
        _showDeleteAllSnackBar.value = null
        _showDeleteSnackBar.value = null
        _showUnknownIdSnackBarEvent.value = null
        _showUpdateSnackBar.value = null
    }

    fun resetTexts() {
        description.value = ""
        updateId.value = ""
        amount.value = ""
        deleteId.value = ""
    }
}