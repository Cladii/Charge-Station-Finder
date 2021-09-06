package com.example.g52010mobg5.app.maps

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.example.g52010mobg5.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindow(context: Context) : GoogleMap.InfoWindowAdapter {

    private val mWindow: View =
        (context as Activity).layoutInflater.inflate(R.layout.snippet_marker, null)

    override fun getInfoContents(marker: Marker): View? {
        val title = mWindow.findViewById<TextView>(R.id.title)
        val snippet = mWindow.findViewById<TextView>(R.id.snippet)
        title.text = marker.title
        snippet.text = marker.snippet
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}