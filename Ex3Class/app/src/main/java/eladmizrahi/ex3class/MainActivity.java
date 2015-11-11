package eladmizrahi.ex3class;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    private TextView txtLocation, txtLocation2;
    private LocationManager locationManager;
    private PermissionManager permissionManager;
    private Location location1;
    private LocationListener listener;
    private boolean once = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLocation = (TextView) findViewById(R.id.txtLocation1);
        txtLocation2 = (TextView) findViewById(R.id.txtLocation2);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        final Location cinemaCity = new Location("Cinema City");
        cinemaCity.setLatitude(31.782780);
        cinemaCity.setLongitude(35.203695);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location)
            {
                float distance = location.distanceTo(cinemaCity);
                txtLocation.setText("" + distance);
                if (distance <= 10)
                {
                    txtLocation2.setText("You're There!");
                }
                else if (location1.distanceTo(cinemaCity) > distance)
                {
                    txtLocation2.setText("Warmer");
                }
                else
                {
                    txtLocation2.setText("Colder");
                }

                location1 = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        final String provider = locationManager.getBestProvider(criteria, true);

        permissionManager = new PermissionManager(this, new PermissionManager.OnPermissionListener() {
            @Override
            public void OnPermissionChanged(boolean permissionGranted) {
                if (permissionGranted)
                {
                    try
                    {
                        locationManager.requestLocationUpdates(provider, 2000, 3, listener);
                        location1 = locationManager.getLastKnownLocation(provider);
                    }
                    catch (SecurityException e)
                    {

                    }


                } else {

                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
