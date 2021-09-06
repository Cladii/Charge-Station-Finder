package com.example.g52010mobg5.app.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.g52010mobg5.R
import com.example.g52010mobg5.app.database.email.EmailDatabase
import com.example.g52010mobg5.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = EmailDatabase.getInstance(application).emailDatabaseDao
        val viewModelFactory = HomeFragmentViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeFragmentViewModel::class.java)
        binding.homeFragmentViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.status.observe(viewLifecycleOwner, Observer<Boolean> { isValid ->
            if (!isValid) Toast.makeText(context, "Incorrect email adress", Toast.LENGTH_SHORT)
                .show()
        })
        viewModel.name.observe(viewLifecycleOwner, Observer<String> { name ->
            binding.username.text = name
        })
        viewModel.emails.observe(viewLifecycleOwner, Observer { emails ->
            val emailsString = emails.map { it.emailId }
            binding.editTextTextEmailAddress.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    emailsString
                )
            )
        })

        return binding.root
    }
}