package hu.andras.mobilbeadando.ui.incomeedit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.andras.mobilbeadando.database.TransactionDatabaseDao
import hu.andras.mobilbeadando.ui.incomeedit.IncomeEditViewModel
import java.lang.IllegalArgumentException

class IncomeEditViewModelFactory(
        private val dataSource: TransactionDatabaseDao,
        private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(IncomeEditViewModel::class.java)){
            return IncomeEditViewModel( dataSource, application ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}