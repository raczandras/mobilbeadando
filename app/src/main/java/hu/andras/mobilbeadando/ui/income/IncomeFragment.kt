package hu.andras.mobilbeadando.ui.income

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hu.andras.mobilbeadando.R
import androidx.databinding.DataBindingUtil
import hu.andras.mobilbeadando.database.TransactionDatabase
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hu.andras.mobilbeadando.databinding.FragmentIncomeBinding

class IncomeFragment : Fragment() {

    private lateinit var incomeViewModel: IncomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val binding: FragmentIncomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_income, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TransactionDatabase.getInstance(application).transactionDatabaseDao
        val viewModelFactory = IncomeViewModelFactory( dataSource, application)
        val incomeViewModel = ViewModelProvider(this, viewModelFactory).get(IncomeViewModel::class.java)


        binding.incomeViewModel = incomeViewModel
        binding.setLifecycleOwner(this)

        incomeViewModel.showAddedSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.added),
                    Snackbar.LENGTH_SHORT
                ).show()
                incomeViewModel.doneShowingSnackbar()
            }
         })

        incomeViewModel.showWrongSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.wrong),
                        Snackbar.LENGTH_SHORT
                ).show()
                incomeViewModel.doneShowingSnackbar()
            }
        })

        incomeViewModel.closeKeyboard.observe(viewLifecycleOwner, Observer {
            if( it == true){
                val view = activity?.currentFocus
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view!!.windowToken, 0)
            }
        })

        val adapter = IncomeAdapter()
        binding.incomeList.adapter = adapter
        incomeViewModel.incomes.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.addHeaderAndSubmitList(it)
            }
        })

        return binding.root
    }
}