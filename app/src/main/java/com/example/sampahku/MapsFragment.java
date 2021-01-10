package com.example.sampahku;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        @Override
        public void onMapReady(GoogleMap googleMap) {

            LatLng yogyakarta = new LatLng(-7.797068, 110.370529);


            LatLng tuhutentrem = new LatLng(-7.820550401823985, 110.37878156267553);
            googleMap.addMarker(new MarkerOptions().position(tuhutentrem).title("Bank Sampah Tresno Tuhutentrem"));

            LatLng gemahripah = new LatLng(-7.894455259841929, 110.32950381202032);
            googleMap.addMarker(new MarkerOptions().position(gemahripah).title("Bank Sampah Gemah Ripah Bantul"));

            LatLng prenggan = new LatLng(-7.831391004101809, 110.40007471399855);
            googleMap.addMarker(new MarkerOptions().position(prenggan).title("Bank Sampah Barokah Daleman Prenggan"));

            LatLng mondoroko = new LatLng(-7.8272521052669415, 110.3963539153772);
            googleMap.addMarker(new MarkerOptions().position(mondoroko).title("Bank Sampah Mondoroko RW 7"));

            LatLng sunten = new LatLng(-7.808374294383879, 110.40849762087997);
            googleMap.addMarker(new MarkerOptions().position(sunten).title("Bank Sampah Sunten"));

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yogyakarta, 11));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}