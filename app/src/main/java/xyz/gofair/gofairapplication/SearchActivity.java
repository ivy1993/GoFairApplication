package xyz.gofair.gofairapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.io.File;
import java.util.List;

public class SearchActivity extends FragmentActivity implements OnMapReadyCallback ,TaskLoadedCallback{

    private GoogleMap mMap;

    private MarkerOptions place1, place2;
    Button getDirection;
    private Polyline currentPolyline;
    static double latitude1=0.00;
    static double longitude1=0.00;
    static double latitude2=0.00;
    static double longitude2=0.00;
    private final static int CAMERA_PIC_REQUEST = 704;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static String imageStoragePath;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    private static final int PERMISSION_REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Bundle b = getIntent().getExtras();
        latitude1 = b.getDouble("lat");
        longitude1 = b.getDouble("lon");
        latitude2 = b.getDouble("lat2");
        longitude2 = b.getDouble("lon2");

        Button scanqr =(Button) findViewById(R.id.scanqr);
        scanqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(SearchActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();


            }
        });


        Button scandr =(Button) findViewById(R.id.scandr);
        scandr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);


            }
        });

        Button searchdr =(Button) findViewById(R.id.searchdr);
        searchdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "No driver is avaiable right now", Toast.LENGTH_LONG).show();

            }
        });

        Button searchrd =(Button) findViewById(R.id.searchrd);
        searchrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "No Rider is avaiable right now", Toast.LENGTH_LONG).show();

            }
        });

//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.home);
        place1 = new MarkerOptions().position(new LatLng(latitude1, longitude1)).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        place2 = new MarkerOptions().position(new LatLng(latitude2, longitude2)).title("Destination").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");

            } else {
                Log.e("Scan", "Scanned");

              //  tv_qr_readTxt.setText(result.getContents());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    /*public void onSearch(View view) {

       // EditText location_tf = (EditText) findViewById(R.id.TFaddress);
        destination = (EditText) findViewById(R.id.desti);
        String loaction = destination.getText().toString();

        List<Address> adressList = null;
        if (loaction != null || !loaction.equals("")) {

            Geocoder geocoder = new Geocoder(this);
            try {
                adressList = geocoder.getFromLocationName(loaction, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = adressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            lat2=address.getLatitude();
            lon2=address.getLongitude();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Your Destination"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        }
    }  */

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng CurrentPos = new LatLng(latitude1, longitude1);
//        //mMap.addMarker(new MarkerOptions().position(CurrentPos).title("your Current Position"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(CurrentPos));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude1,longitude1), 12.0f));


        mMap.addMarker(place1);
        mMap.addMarker(place2);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(latitude1, longitude1));
        builder.include(new LatLng(latitude2, longitude2));
        LatLngBounds bounds = builder.build();
        bounds.getCenter();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        mMap.animateCamera(cu);

        new FetchURL(SearchActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");



    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyCIArpeNpBnv4tLx43OZsFUudyrJ2xTg4E";
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}
