package hu.andras.mobilbeadando.ui.expenseedit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.andras.mobilbeadando.database.TransactionDatabaseDao
import java.lang.IllegalArgumentException

class ExpenseEditViewModelFactory(
        private val dataSource: TransactionDatabaseDao,
        private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ExpenseEditViewModel::class.java)){
            return ExpenseEditViewModel( dataSource, application ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}