package hu.andras.mobilbeadando.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income_table")
class Income (

    @PrimaryKey(autoGenerate = true)
    var incomeId: Long = 0L,

    @ColumnInfo(name = "time_milli")
    val date: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "amount")
    var amount: Int = 0,
    @ColumnInfo(name = "description")
    var description: String = ""

)