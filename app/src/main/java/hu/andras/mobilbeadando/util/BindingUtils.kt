package hu.andras.mobilbeadando.ui.income

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import hu.andras.mobilbeadando.R
import hu.andras.mobilbeadando.database.Expense
import hu.andras.mobilbeadando.database.Income
import hu.andras.mobilbeadando.util.Util
import java.text.SimpleDateFormat

val util = Util()

@SuppressLint("SetTextI18n")
@BindingAdapter("incomeIdFormatted")
fun TextView.setincomeIdFormatted(item: Income) {
    text = "Kulcs: " + item.incomeId.toString()
}

@BindingAdapter( "incomeDateFormatted")
fun TextView.setIncomeDateFormatted(item: Income){
    text = util.convertLongToDateString(item.date)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("incomeAmountFormatted")
fun TextView.setIncomeAmountFormatted(item: Income){
    text = "Összeg: " + item.amount.toString()
}

@BindingAdapter("incomeDescFormatted")
fun TextView.setIncomeDescFormatted(item: Income){
    text = item.description
}

@SuppressLint("SetTextI18n")
@BindingAdapter("expenseIdFormatted")
fun TextView.setExpenseIdFormatted(item: Expense) {
    text = "Kulcs: " + item.expenseId.toString()
}

@BindingAdapter( "expenseDateFormatted")
fun TextView.setExpenseDateFormatted(item: Expense){
    text = util.convertLongToDateString(item.date)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("expenseAmountFormatted")
fun TextView.setExpenseAmountFormatted(item: Expense){
    text = "Összeg: " + item.amount.toString()
}

@BindingAdapter("expenseDescFormatted")
fun TextView.setExpenseDescFormatted(item: Expense){
    text = item.description
}



