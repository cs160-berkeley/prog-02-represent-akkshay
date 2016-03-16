package repr.khoslaa.com.representatives;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "GfQDtm4vHIDIHNXRXSjl0ibbd";
    private static final String TWITTER_SECRET = "VUsYw2yFnl7ljcNWmu0McxmVn5RNPON6iyHSy75d2kNahzYDmV";
    private Location mLastLocation;


    Button findRepsButton;
    Button findRepsButtonCurr;
    EditText editText;
//    EditText editText2;
    private GoogleApiClient mGoogleApiClient;
    String currLat = "";
    String currLong = "";
    private LocationRequest mLocationRequest;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)  // used for data layer API
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000) // 10000 ms = 10 s
                .setFastestInterval(1 * 1000); // 1000 ms = 1 s


//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//            if (mLastLocation != null) {
//                currLat = String.valueOf(mLastLocation.getLatitude());
//                currLong = String.valueOf(mLastLocation.getLongitude());
//            } else {
//                Log.d("T", "so mlastlocation is null in oncreate");
//            }
//        }

        editText = (EditText)findViewById(R.id.editText);



        findRepsButton = (Button) findViewById(R.id.findRepsButton);
        if (findRepsButton == null) {
            Log.d("T", "so findreps is null");
        }
        findRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isZip = false;
                String zipCode = "";
                if (editText.getText().toString() != null && editText.getText().toString() != "") {
                    zipCode = editText.getText().toString();
                    isZip = true;
                }

                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("CAT_NAME", "Fred");



                if (sendIntent != null) {
                    startService(sendIntent);
                }

                Intent intent = new Intent(getApplicationContext(), CongressionalActivity.class);
                intent.putExtra("IS_ZIPCODE", isZip);
                intent.putExtra("VALUE", zipCode);
                MainActivity.this.startActivity(intent);

            }
        });

        findRepsButtonCurr = (Button) findViewById(R.id.findRepsButtonCurr);
        findRepsButtonCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isZip = false;

                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("CAT_NAME", "Fred");
                if (sendIntent != null) {
                    startService(sendIntent);
                }

                Intent intent = new Intent(getApplicationContext(), CongressionalActivity.class);
                intent.putExtra("IS_ZIPCODE", isZip);
                Log.d("T", "so the lat long is");
                Log.d("T", "it is right now" + currLat);
                Log.d("T", currLong);
                intent.putExtra("LAT", currLat);
                intent.putExtra("LONG", currLong);
                MainActivity.this.startActivity(intent);

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi
                    .removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d("T", "connected to google maps");


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location == null) {
                LocationServices.FusedLocationApi
                        .requestLocationUpdates(mGoogleApiClient,
                                mLocationRequest, this);
            } else {
                handleNewLocation(location);
            }
        }
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//            if (mLastLocation != null) {
//                currLat = String.valueOf(mLastLocation.getLatitude());
//                currLong = String.valueOf(mLastLocation.getLongitude());
//            } else {
//                Log.d("T", "so mlastlocation is null");
//            }
//        }

    }
    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        currLat = String.valueOf(mLastLocation.getLatitude());
        currLong = String.valueOf(mLastLocation.getLongitude());

        Log.d("T", location.toString());

    } // handleNewLocation()

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("T", "Location services suspended. Please reconnect.");
    } // onConnectionSuspended()

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to solve the error
                connectionResult.startResolutionForResult(this,
                        9000);
            } catch (IntentSender.SendIntentException e) {
                Log.e("T", "SendIntentException", e);
                e.printStackTrace();
            } // catch
        } else {
            Log.i("T", "Location services connection failed with code "
                    + connectionResult.getErrorCode());
        } // else
    } // onConnectionFailed()

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    } // onLocationChanged()

}
