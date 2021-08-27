package com.tahadroid.wing.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.directions.route.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.tahadroid.wing.R
import kotlinx.android.synthetic.main.activity_map.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MapActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleApiClient.OnConnectionFailedListener, RoutingListener {

    private lateinit var mMap: GoogleMap

    //current and destination location objects
    var myLocation: Location? = null
    var destinationLocation: Location? = null
    protected var start: LatLng? = null
    protected var end: LatLng? = null
    private var flag = true

    //to get location permissions.
    private val LOCATION_REQUEST_CODE = 23
    var locationPermission = false

    //polyline object
    var polylines: ArrayList<Polyline>? = null
    var listPoints: ArrayList<LatLng>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        listPoints = ArrayList()
        imageButtonPickUp.setOnClickListener {
            if (listPoints!!.size == 2) {
                listPoints!!.clear()
                mMap.clear()
            }

            start = mMap.getCameraPosition().target
            listPoints!!.add(start!!)
            val markerOptions = MarkerOptions()
            markerOptions.position(start!!)
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            mMap.addMarker(markerOptions)
            textViewPickUp.text = getAddress(start!!)
            textViewPickUp.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textViewPickUp.setHorizontallyScrolling(true);
            textViewPickUp.setMarqueeRepeatLimit(-1);
            textViewPickUp.setFocusable(true);
            textViewPickUp.setFocusableInTouchMode(true);
            textViewPickUp.setSelected(true)

            if (start != null && end != null) {
                mMap.clear()
                Findroutes(start, end)
            }

        }
        imageButtonDelivery.setOnClickListener {
            if (listPoints!!.size == 2) {
                listPoints!!.clear()
                mMap.clear()
            }

            end = mMap.getCameraPosition().target
            listPoints!!.add(end!!)
            val markerOptions = MarkerOptions()
            markerOptions.position(end!!)
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            mMap.addMarker(markerOptions)
            textViewDelivery.text = getAddress(end!!)
            textViewDelivery.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textViewDelivery.setHorizontallyScrolling(true);
            textViewDelivery.setMarqueeRepeatLimit(-1);
            textViewDelivery.setFocusable(true);
            textViewDelivery.setFocusableInTouchMode(true);
            textViewDelivery.setSelected(true)

            if (start != null && end != null) {
                mMap.clear()
                Findroutes(start, end)
            }

        }

        //request location permission.
        requestPermision()

        //init google map fragment to show map.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

    }


    private fun requestPermision() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_REQUEST_CODE
            )
        } else {
            locationPermission = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    //if permission granted.
                    locationPermission = true
                    getMyLocation()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }

    private fun getAddress(latLng: LatLng): String? {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())
        return try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            val address = addresses[0].getAddressLine(0)
            address
        } catch (e: IOException) {
            e.printStackTrace()
            "No Address Found"
        }
    }

    //to get user location
    private fun getMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled = true

        mMap.setOnMyLocationChangeListener { location ->
            if (flag) {
                myLocation = location
                val ltlng = LatLng(location.latitude, location.longitude)
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    ltlng, 16f
                )
                mMap.animateCamera(cameraUpdate)
                flag = false
            }
        }

        //get destination location when user click on map
//        mMap.setOnMapClickListener { latLng ->
//            end = latLng
//            mMap.clear()
//            start = LatLng(myLocation!!.latitude, myLocation!!.longitude)
//            textViewPickUp.text = getAddress(start!!)
//            textViewPickUp.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            textViewPickUp.setHorizontallyScrolling(true);
//            textViewPickUp.setMarqueeRepeatLimit(-1);
//            textViewPickUp.setFocusable(true);
//            textViewPickUp.setFocusableInTouchMode(true);
//            textViewPickUp.setSelected(true)
//
//            textViewDelivery.text = getAddress(end!!)
//            textViewDelivery.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            textViewDelivery.setHorizontallyScrolling(true);
//            textViewDelivery.setMarqueeRepeatLimit(-1);
//            textViewDelivery.setFocusable(true);
//            textViewDelivery.setFocusableInTouchMode(true);
//            textViewDelivery.setSelected(true)
//            //start route finding
//            Findroutes(start, end)
//        }
    }

    // function to find Routes.
    fun Findroutes(Start: LatLng?, End: LatLng?) {
        if (Start == null || End == null) {
            Toast.makeText(this@MapActivity, "Unable to get location", Toast.LENGTH_LONG).show()
        } else {
            val routing = Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(Start, End)
                .key("AIzaSyAvKCQ5Ync-aLf0_fZcxvMhlST6o2MMh2U") //also define your api key here.
                .build()
            routing.execute()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        if (locationPermission) {
            getMyLocation()
        }

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Findroutes(start, end)
    }

    override fun onRoutingCancelled() {
        Findroutes(start, end)
    }

    override fun onRoutingStart() {
        Toast.makeText(this@MapActivity, "Finding Route...", Toast.LENGTH_LONG).show()

    }

    override fun onRoutingFailure(e: RouteException?) {
        val parentLayout = findViewById<View>(android.R.id.content)
        val snackbar: Snackbar = Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG)
        snackbar.show()
//        Findroutes(start,end);
    }

    override fun onRoutingSuccess(route: ArrayList<Route>, shortestRouteIndex: Int) {
        //  val center = CameraUpdateFactory.newLatLng(start)
        val zoom = CameraUpdateFactory.zoomTo(16f)
        polylines = ArrayList()
        if (polylines != null) {
            polylines!!.clear()
        }
        val polyOptions = PolylineOptions()
        var polylineStartLatLng: LatLng? = null
        var polylineEndLatLng: LatLng? = null


        //add route(s) to the map using polyline
        //add route(s) to the map using polyline
        for (i in route.indices) {
            if (i == shortestRouteIndex) {
                polyOptions.color(resources.getColor(R.color.darkGreen))
                polyOptions.width(12f)
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints())
                val polyline = mMap.addPolyline(polyOptions)
                polylineStartLatLng = polyline.points[0]
                val k = polyline.points.size
                polylineEndLatLng = polyline.points[k - 1]
                polylines!!.add(polyline)
            } else {
            }
        }

        //Add Marker on route starting position

        //Add Marker on route starting position
        val startMarker = MarkerOptions()
        startMarker.position(polylineStartLatLng!!)
        startMarker.title("My Location")
        mMap.addMarker(startMarker)

        //Add Marker on route ending position

        //Add Marker on route ending position
        val endMarker = MarkerOptions()
        endMarker.position(polylineEndLatLng!!)
        endMarker.title("Destination")
        mMap.addMarker(endMarker)
    }

}