package hu.andras.mobilbeadando.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDatabaseDao{
    @Insert
    suspend fun insert(income: Income)
    @Insert
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(income: Income)
    @Update
    suspend fun update(expense: Expense)

    @Query("Select * from income_table where incomeId = :key")
    suspend fun getIncome(key: Long): Income?
    @Query("Select * from expense_table where expenseId = :key")
    suspend fun getExpense(key: Long): Expense?

    @Query("Select * from income_table")
    fun getAllIncomes(): LiveData<List<Income>>
    @Query("Select * from expense_table")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("Delete from income_table where incomeId = :key" )
    suspend fun deleteIncome(key: Long)

    @Query( "Delete from expense_table where expenseId = :key")
    suspend fun deleteExpense(key: Long)

    @Query( "Select ((Select SUM(amount) FROM INCOME_TABLE) - (Select SUM(amount) FROM expense_table))")
    fun getBalance(): LiveData<Int>

    @Query( "Select EXISTS( Select * from expense_table where expenseId = :key)")
    fun queryExpenseId(key: Long): LiveData<Boolean>

    @Query( "Select EXISTS( Select * from income_table where incomeId = :key)")
    fun queryIncomeId(key: Long): LiveData<Boolean>

    @Query("Delete FROM expense_table")
    suspend fun deleteAllExpense()

    @Query("Delete FROM income_table")
    suspend fun deleteAllIncome()
}