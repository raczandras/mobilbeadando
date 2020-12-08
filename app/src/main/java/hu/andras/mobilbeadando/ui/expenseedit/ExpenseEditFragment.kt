package hu.andras.mobilbeadando.ui.expenseedit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import hu.andras.mobilbeadando.R
import hu.andras.mobilbeadando.database.TransactionDatabase
import hu.andras.mobilbeadando.database.TransactionDatabaseDao
import hu.andras.mobilbeadando.databinding.FragmentExpenseEditBinding

class ExpenseEditFragment : Fragment() {

    private lateinit var expenseEditViewModel: ExpenseEditViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) : View? {
        val binding: FragmentExpenseEditBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_expense_edit, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = TransactionDatabase.getInstance(application).transactionDatabaseDao
        val viewModelFactory = ExpenseEditViewModelFactory(dataSource, application)
        val expenseEditViewModel = ViewModelProvider(this, viewModelFactory).get(ExpenseEditViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.expenseEditViewModel = expenseEditViewModel

        expenseEditViewModel.showSuccessfullDeleteAllSnackbar.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.deletedallexpenses),
                        Snackbar.LENGTH_SHORT
                ).show()
                expenseEditViewModel.doneShowingSnackbar()
            }
        })

        expenseEditViewModel.showSuccessfullDeleteSnackbar.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.deletedexpense),
                        Snackbar.LENGTH_SHORT
                ).show()
                expenseEditViewModel.doneShowingSnackbar()
            }
        })

        expenseEditViewModel.showUnknownIdSnackbarEvent.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.unknownid),
                        Snackbar.LENGTH_SHORT
                ).show()
                expenseEditViewModel.doneShowingSnackbar()
            }
        })

        expenseEditViewModel.showUpdateSnackBar.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.updatedexpense),
                        Snackbar.LENGTH_SHORT
                ).show()
                expenseEditViewModel.doneShowingSnackbar()
            }
        })

        expenseEditViewModel.showWrongSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.wrong),
                        Snackbar.LENGTH_SHORT
                ).show()
                expenseEditViewModel.doneShowingSnackbar()
            }
        })

        expenseEditViewModel.closeKeyboard.observe(viewLifecycleOwner, Observer {
            if( it == true){
                val view = activity?.currentFocus
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view!!.windowToken, 0)
            }
        })

        return binding.root
    }
}