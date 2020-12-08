package hu.andras.mobilbeadando.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
class Expense (
    @PrimaryKey(autoGenerate = true)
    var expenseId: Long = 0L,

    @ColumnInfo(name = "time_milli")
    val date: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "amount")
    val amount: Int = -1,
    @ColumnInfo(name = "description")
    val description: String = ""
)