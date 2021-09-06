package com.example.g52010mobg5.app.maps.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.g52010mobg5.R
import com.example.g52010mobg5.app.database.user.favorite.FavoriteDatabase
import com.example.g52010mobg5.databinding.FragmentDetailMarkerBinding


class DetailMarkerFragment : Fragment() {

    private lateinit var binding: FragmentDetailMarkerBinding
    private lateinit var viewModel: DetailMarkerFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments ?: return null
        val args =
            DetailMarkerFragmentArgs.fromBundle(
                bundle
            )
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_marker, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = FavoriteDatabase.getInstance(application).favoriteDatabaseDao
        val viewModelFactory =
            DetailMarkerFragmentViewModelFactory(
                dataSource,
                application
            )
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailMarkerFragmentViewModel::class.java)
        binding.detailMarkerFragmentViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.address.text = args.informations

        viewModel.isPresent.observe(viewLifecycleOwner, Observer<Boolean> { isPresent ->
            if (isPresent) {
                binding.addFav.visibility = View.VISIBLE
                binding.delFav.visibility = View.INVISIBLE
            } else {
                binding.addFav.visibility = View.INVISIBLE
                binding.delFav.visibility = View.VISIBLE
            }
        })
        viewModel.isPresent(viewModel.getFav(args.informations))

        return binding.root
    }
}