package com.nashik.tripplanner.activities;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.data.NashikData;
import com.nashik.tripplanner.models.Attraction;
import java.util.List;
public class MapFullActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final LatLng NASHIK=new LatLng(19.9975,73.7898);
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);setContentView(R.layout.activity_map_full);
        View back=findViewById(R.id.iv_back);if(back!=null)back.setOnClickListener(v->onBackPressed());
        SupportMapFragment mf=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment_view);
        if(mf!=null)mf.getMapAsync(this);
    }
    @Override public void onMapReady(GoogleMap map){
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setTrafficEnabled(true); // Enable live traffic
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(NASHIK,12f));
        List<Attraction> attrs=NashikData.getAttractions();
        for(Attraction a:attrs){
            float hue;switch(a.getCategory()){case"Pilgrimage":hue=BitmapDescriptorFactory.HUE_ORANGE;break;case"Wine Tour":hue=BitmapDescriptorFactory.HUE_VIOLET;break;case"Trekking":hue=BitmapDescriptorFactory.HUE_GREEN;break;case"Heritage":hue=BitmapDescriptorFactory.HUE_YELLOW;break;case"Nature":hue=BitmapDescriptorFactory.HUE_CYAN;break;default:hue=BitmapDescriptorFactory.HUE_BLUE;}
            map.addMarker(new MarkerOptions().position(new LatLng(a.getLatitude(),a.getLongitude())).title(a.getEmoji()+" "+a.getName()).snippet(a.getCategory()+" • "+a.getEntryFee()+" • "+a.getTiming()).icon(BitmapDescriptorFactory.defaultMarker(hue)));
        }
        map.setOnMarkerClickListener(marker->{Toast.makeText(this,marker.getTitle(),Toast.LENGTH_SHORT).show();marker.showInfoWindow();return false;});
    }
}
