package hu.andras.mobilbeadando.util

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.core.text.HtmlCompat
import hu.andras.mobilbeadando.R
import hu.andras.mobilbeadando.database.Expense
import hu.andras.mobilbeadando.database.Income
import java.text.SimpleDateFormat

class Util {

    @SuppressLint("SimpleDateFormat")
    fun convertLongToDateString(systemTime: Long): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm")
                .format(systemTime).toString()
    }

    fun sumOfIncomes(incomes: List<Income>?, resources: Resources?): Any {
        var sum = 0
        if( !incomes.isNullOrEmpty()){
            incomes.forEach {
                sum = sum + it.amount
            }
            return sum.toString()
        }
        else{
            return "0"
        }
    }

    fun sumOfExpenses(expenses: List<Expense>?, resources: Resources?): Any {
        var sum = 0
        if( !expenses.isNullOrEmpty()){
            expenses.forEach {
                sum = sum + it.amount
            }
            return sum.toString()
        }
        else{
            return "0"
        }
    }
}