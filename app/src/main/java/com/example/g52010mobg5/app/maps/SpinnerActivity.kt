package com.example.g52010mobg5.app.maps

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import kotlinx.coroutines.runBlocking

class SpinnerActivity(
    viewModel: MapsFragmentViewModel
) : Activity(),
    AdapterView.OnItemSelectedListener {

    private val mViewModel = viewModel
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val type = Type.parse(parent.getItemAtPosition(pos) as String)
        if (type != null) {
            when (type) {
                Type.DEFAULT -> mViewModel.getChargeStation()
                Type.FAVORITE -> {
                    runBlocking { mViewModel.getFavorite() }
                }
                else -> mViewModel.getTypeChargeStation(type)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
    }
}