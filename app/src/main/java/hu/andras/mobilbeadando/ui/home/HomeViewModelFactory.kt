package hu.andras.mobilbeadando.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.andras.mobilbeadando.database.TransactionDatabaseDao
import java.lang.IllegalArgumentException


class HomeViewModelFactory(
        private val dataSource: TransactionDatabaseDao,
        private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel( dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}