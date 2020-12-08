package hu.andras.mobilbeadando.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hu.andras.mobilbeadando.R
import hu.andras.mobilbeadando.database.TransactionDatabase
import hu.andras.mobilbeadando.databinding.FragmentHomeBinding
import hu.andras.mobilbeadando.ui.income.IncomeViewModel
import hu.andras.mobilbeadando.ui.income.IncomeViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TransactionDatabase.getInstance(application).transactionDatabaseDao
        val viewModelFactory = HomeViewModelFactory( dataSource, application)
        val homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.homeViewModel = homeViewModel
        binding.setLifecycleOwner(this)
        //homeViewModel.kezd()

        return binding.root
    }
}