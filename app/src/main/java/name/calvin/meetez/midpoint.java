package name.calvin.meetez;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class midpoint extends FragmentActivity implements OnMapReadyCallback {

    private double[] midpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midpoint);
        Bundle extras = getIntent().getExtras();
        midpoint = extras.getDoubleArray("midpoint");

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.activity_midpoint);
        mapFragment.getMapAsync(this);
    }

    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(midpoint[0], midpoint[1])).title("Center Location"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(midpoint[0], midpoint[1])).zoom(15).tilt(30).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
