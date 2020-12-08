package hu.andras.mobilbeadando.ui.home

import android.app.Application
import androidx.lifecycle.*
import hu.andras.mobilbeadando.database.TransactionDatabaseDao
import hu.andras.mobilbeadando.ui.income.util

class HomeViewModel(
        val database: TransactionDatabaseDao,
        application: Application
) : AndroidViewModel(application) {

    val incomes = database.getAllIncomes()
    val expenses = database.getAllExpenses()


    val incomeAmount =  Transformations.map(incomes) {
        incomes -> util.sumOfIncomes(incomes, application.resources)
    }

    val expenseAmount = Transformations.map(expenses) {
        expenses -> util.sumOfExpenses(expenses, application.resources)
    }

    var balance = database.getBalance()




}