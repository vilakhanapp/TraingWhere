package ltc.vilakhan.traingwhere;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    private double latADouble, lngADouble, updateLatADouble, updateLngADouble;
    private String[] loginStrings;
    private TextView textView;
    private EditText editText;
    private ImageView imageView, takePhotoImageView;
    private String nameImageString;
    private Uri uri;
    private boolean aBoolean = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);

        //Bind Widget
        textView = (TextView) findViewById(R.id.textView5);
        editText = (EditText) findViewById(R.id.editText6);
        imageView = (ImageView) findViewById(R.id.imageView2);
        takePhotoImageView = (ImageView) findViewById(R.id.imageView3);

        // Setup
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false); // dont't get z get only x,y

        loginStrings = getIntent().getStringArrayExtra("Login");

        //Show view
        textView.setText(loginStrings[1]);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Take photo Controller
        takePhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        // Image Controller
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Please choose App"),1);
            }
        });


    } // Main method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            uri = data.getData();
            changeImage(uri);

            aBoolean = false;

        } // if

    } //On activity result

    private void changeImage(Uri uri) {
        try {

            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            imageView.setImageBitmap(bitmap);


        }catch (Exception e){
            Log.d("16decV1", "e changeImage ==> " + e.toString());
        }
    }

    public void clickSave(View view) {


        nameImageString = editText.getText().toString().trim();

        //Check space
        if (nameImageString.equals("")) {
            //Have Space
            MyAlert myAlert = new MyAlert(ServiceActivity.this,
                    getResources().getString(R.string.title_have_space),
                    getResources().getString(R.string.message_have_space),
                    R.drawable.doremon48);
            myAlert.myDialog();
        } else if (aBoolean) {
            //None choose Image
            MyAlert myAlert = new MyAlert(ServiceActivity.this,
                    getResources().getString(R.string.title_noimage),
                    getResources().getString(R.string.message_noimage),
                    R.drawable.kon48);
            myAlert.myDialog();

        } else {
            //Data OK



        }




    } // click save




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

        updateLatADouble = latADouble;
        updateLngADouble = lngADouble;
        Log.d("15decV2", "updateLat ==> " + updateLatADouble);
        Log.d("15decV2", "updateLng ==> " + updateLngADouble);

    }
} // Main class
