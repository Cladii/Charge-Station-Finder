package com.example.g52010mobg5.app.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.g52010mobg5.R
import com.example.g52010mobg5.app.database.user.favorite.FavoriteDatabase
import com.example.g52010mobg5.app.network.ChargeStation
import com.example.g52010mobg5.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.round

class MapsFragment : Fragment(), GoogleMap.OnInfoWindowClickListener {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var viewModel: MapsFragmentViewModel

    private lateinit var locationManager: LocationManager
    private val REQUEST_LOCATION_PERMISSION = 1

    private val callback = OnMapReadyCallback { googleMap ->
        enableMyLocation(googleMap)
        if (isPermissionGranted()) {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                viewModel.setPos(LatLng(location.latitude, location.longitude))
                initCamera(location, googleMap)
                viewModel.getChargeStation()
                viewModel.response.observe(
                    viewLifecycleOwner,
                    Observer<List<ChargeStation>> { stations ->
                        setMarker(googleMap, stations)
                    })
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_maps,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = MapsFragmentViewModelFactory(
            FavoriteDatabase.getInstance(application).favoriteDatabaseDao,
            application
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(MapsFragmentViewModel::class.java)
        binding.mapsFragmentViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setSpinner()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private fun setSpinner() {
        val spinner = binding.spinnerFilter
        val spinnerElements = Type.values().map { it.classicName }
        spinner.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            spinnerElements
        )
        spinner.onItemSelectedListener = SpinnerActivity(viewModel)
    }

    private fun enableMyLocation(map: GoogleMap) {
        if (isPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    // Checks that users have given permission
    private fun isPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun initCamera(location: Location, googleMap: GoogleMap) {
        val latitude = location.latitude
        val longitude = location.longitude
        val homeLatLng = LatLng(latitude, longitude)
        val zoomlevel = 13f
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomlevel))
    }

    private fun setMarker(googleMap: GoogleMap, stations :  List<ChargeStation>){
        googleMap.clear()
        for (station in stations) {
            val snippet = createSnippet(station)
            val stationLatLng =
                LatLng(station.AddressInfo.Latitude, station.AddressInfo.Longitude)
            googleMap.addMarker(
                MarkerOptions()
                    .position(stationLatLng)
                    .title("Informations")
                    .snippet(snippet)
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_RED
                        )
                    )
            )
            googleMap.setInfoWindowAdapter(CustomInfoWindow(requireContext()))
            googleMap.setOnInfoWindowClickListener(this)
        }
    }

    override fun onInfoWindowClick(p0: Marker) {
        val text = p0.snippet
        val action = MapsFragmentDirections.actionNavMapsToDetailMarkerFragment(text)
        view?.findNavController()?.navigate(action)
    }

    private fun createSnippet(station : ChargeStation): String{
        val connectionTypeStr = getConnectionTypeStr(station)
        val usageTypeStr: String = if (station.UsageType == null) {
            "Unknown"
        } else {
            station.UsageType.Title!!
        }
        return "Adresse : " + station.AddressInfo.AddressLine1 + "\n" +
                "Coordonnées : " + station.AddressInfo.Latitude.round(4) + " / " + station.AddressInfo.Longitude.round(4) + "\n" +
                "Type : " + connectionTypeStr + "\n" +
                "Usage : " + usageTypeStr
    }

    private fun getConnectionTypeStr(station : ChargeStation) : String{
        var connectionTypeStr = ""
        if (station.Connections.isEmpty()) {
            connectionTypeStr = "Unknown"
        } else {
            var i = 2
            for (connection in station.Connections) {
                if (connection.ConnectionType!!.Title != "Unknown") {
                    connectionTypeStr += connection.ConnectionType.Title
                    if (i <= station.Connections.size) {
                        connectionTypeStr += " / "
                    }
                }
                ++i
            }
        }
        return connectionTypeStr
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }


}