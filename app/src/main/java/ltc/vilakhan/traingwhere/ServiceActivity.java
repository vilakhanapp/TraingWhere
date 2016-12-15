package ltc.vilakhan.traingwhere;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ServiceActivity extends FragmentActivity implements OnMapReadyCallback {

    // Explicit
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Criteria criteria; // info location
    private double latADouble, lngADouble;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);

        // Setup
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false); // dont't get z get only x,y


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    } // Main method

    @Override
    protected void onResume() {
        super.onResume();

        latADouble = 17.969979;
        lngADouble = 102.612407;

        // Error 300m
        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);

        if (networkLocation != null) {
            latADouble = networkLocation.getLatitude();
            lngADouble = networkLocation.getLongitude();
        }

        /// Error 50m
        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);

        if (gpsLocation != null) {
            latADouble = gpsLocation.getLatitude();
            lngADouble = gpsLocation.getLongitude();
        }

        Log.d("15decV1", "Lat = " + latADouble);
        Log.d("15decV1", "lng = " + lngADouble);

    } // on Resume




    @Override
    protected void onStop() {
        super.onStop();

        locationManager.removeUpdates(locationListener);

    }


    //// Return location method
    public Location myFindLocation(String strProvider) {


        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {
            // min time request every tim by min, min Distance is distance location
            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(strProvider);
        }


        return location;
    }


    /// Listener location
    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            latADouble = location.getLatitude();
            lngADouble = location.getLongitude();


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            /// change net, sim

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(latADouble, lngADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        myAddMarker(latADouble, lngADouble);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                myAddMarker(latLng.latitude, latLng.longitude);
            }
        });

    } // OnMap

    private void myAddMarker(double latADouble, double lngADouble) {

        LatLng latLng = new LatLng(latADouble, lngADouble);
        mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.build6)));


    }
} // Main class
