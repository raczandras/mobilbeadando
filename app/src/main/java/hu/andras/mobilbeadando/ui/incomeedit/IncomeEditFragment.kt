package hu.andras.mobilbeadando.ui.incomeedit

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
import hu.andras.mobilbeadando.databinding.FragmentIncomeEditBinding

class IncomeEditFragment : Fragment() {

    private lateinit var incomeEditViewModel: IncomeEditViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) : View? {
        val binding: FragmentIncomeEditBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_income_edit, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = TransactionDatabase.getInstance(application).transactionDatabaseDao
        val viewModelFactory = IncomeEditViewModelFactory(dataSource, application)
        val incomeEditViewModel = ViewModelProvider(this, viewModelFactory).get(IncomeEditViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.incomeEditViewModel = incomeEditViewModel

        incomeEditViewModel.showSuccessfullDeleteAllSnackbar.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.deletedallincomes),
                        Snackbar.LENGTH_SHORT
                ).show()
                incomeEditViewModel.doneShowingSnackbar()
            }
        })

        incomeEditViewModel.showSuccessfullDeleteSnackbar.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.deletedincome),
                        Snackbar.LENGTH_SHORT
                ).show()
                incomeEditViewModel.doneShowingSnackbar()
            }
        })

        incomeEditViewModel.showUnknownIdSnackbarEvent.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.unknownid),
                        Snackbar.LENGTH_SHORT
                ).show()
                incomeEditViewModel.doneShowingSnackbar()
            }
        })

        incomeEditViewModel.showUpdateSnackBar.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.updatedincome),
                        Snackbar.LENGTH_SHORT
                ).show()
                incomeEditViewModel.doneShowingSnackbar()
            }
        })

        incomeEditViewModel.showWrongSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if( it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.wrong),
                        Snackbar.LENGTH_SHORT
                ).show()
                incomeEditViewModel.doneShowingSnackbar()
            }
        })

        incomeEditViewModel.closeKeyboard.observe(viewLifecycleOwner, Observer {
            if( it == true){
                val view = activity?.currentFocus
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view!!.windowToken, 0)
            }
        })


        return binding.root
    }
}