package hu.andras.mobilbeadando.ui.expense

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import hu.andras.mobilbeadando.R
import hu.andras.mobilbeadando.database.TransactionDatabase
import hu.andras.mobilbeadando.databinding.FragmentExpenseBinding

class ExpenseFragment : Fragment() {

    private lateinit var expenseViewModel: ExpenseViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentExpenseBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_expense, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource =  TransactionDatabase.getInstance(application).transactionDatabaseDao
        val viewModelFactory = ExpenseViewModelFactory(dataSource, application)
        val expenseViewModel = ViewModelProvider(this, viewModelFactory).get(ExpenseViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.expenseViewModel = expenseViewModel

        expenseViewModel.showAddedSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.added),
                        Snackbar.LENGTH_SHORT
                ).show()
                expenseViewModel.doneShowingSnackbar()
            }
        })

        expenseViewModel.showWrongSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.wrong),
                        Snackbar.LENGTH_SHORT
                ).show()
                expenseViewModel.doneShowingSnackbar()
            }
        })

        expenseViewModel.closeKeyboard.observe(viewLifecycleOwner, Observer {
            if( it == true){
                val view = activity?.currentFocus
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view!!.windowToken, 0)
            }
        })

        val adapter = ExpenseAdapter()
        binding.expenseList.adapter = adapter
        expenseViewModel.expenses.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.addHeaderAndSubmitList(it)
            }
        })


        return binding.root
    }
}