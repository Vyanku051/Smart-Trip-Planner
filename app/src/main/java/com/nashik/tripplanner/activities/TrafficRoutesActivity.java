package com.nashik.tripplanner.activities;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.data.NashikData;
import com.nashik.tripplanner.models.Route;
import java.util.List;
public class TrafficRoutesActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);setContentView(R.layout.activity_traffic_routes);
        View back=findViewById(R.id.iv_back);if(back!=null)back.setOnClickListener(v->onBackPressed());
        RecyclerView rv=findViewById(R.id.rv_routes);
        if(rv!=null){rv.setLayoutManager(new LinearLayoutManager(this));rv.setAdapter(new RA(NashikData.getRoutes(),this::openMaps));}
    }
    void openMaps(Route r){
        String url="https://www.google.com/maps/dir/?api=1&origin="+r.getFromLat()+","+r.getFromLng()+"&destination="+r.getToLat()+","+r.getToLng()+"&travelmode=driving";
        try{startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));}catch(Exception e){Toast.makeText(this,"Google Maps not available",Toast.LENGTH_SHORT).show();}
    }
    interface OnRoute{void on(Route r);}
    static class RA extends RecyclerView.Adapter<RA.VH>{
        private final List<Route> routes; private final OnRoute cb; private int sel=-1;
        RA(List<Route> r,OnRoute cb){routes=r;this.cb=cb;}
        @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int v){return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_route,p,false));}
        @Override public void onBindViewHolder(@NonNull VH h,int pos){
            Route r=routes.get(pos);boolean exp=sel==pos;
            if(h.tvFT!=null)h.tvFT.setText(r.getFrom()+" → "+r.getTo());
            if(h.tvDur!=null)h.tvDur.setText(r.getDuration());
            if(h.tvDist!=null)h.tvDist.setText(r.getDistance());
            if(h.tvVia!=null)h.tvVia.setText("Via: "+r.getVia());
            if(h.tvTraf!=null){h.tvTraf.setText(r.getTrafficLabel());h.tvTraf.setTextColor(r.getTrafficColor());}
            if(h.expLayout!=null)h.expLayout.setVisibility(exp?View.VISIBLE:View.GONE);
            if(exp){if(h.tvFare!=null)h.tvFare.setText("Fare: "+r.getFare());if(h.tvMode!=null)h.tvMode.setText("Mode: "+r.getMode());}
            h.itemView.setOnClickListener(v->{int prev=sel;sel=(sel==pos)?-1:pos;notifyItemChanged(prev);notifyItemChanged(pos);});
            if(h.btnSel!=null)h.btnSel.setOnClickListener(v->{cb.on(r);Toast.makeText(h.itemView.getContext(),"Opening Google Maps…",Toast.LENGTH_SHORT).show();});
        }
        @Override public int getItemCount(){return routes.size();}
        static class VH extends RecyclerView.ViewHolder{TextView tvFT,tvDur,tvDist,tvVia,tvTraf,tvFare,tvMode;View expLayout;com.google.android.material.button.MaterialButton btnSel;VH(@NonNull View v){super(v);tvFT=v.findViewById(R.id.tv_route_from_to);tvDur=v.findViewById(R.id.tv_route_duration);tvDist=v.findViewById(R.id.tv_route_distance);tvVia=v.findViewById(R.id.tv_route_via);tvTraf=v.findViewById(R.id.tv_traffic_label);expLayout=v.findViewById(R.id.layout_route_details);tvFare=v.findViewById(R.id.tv_route_fare);tvMode=v.findViewById(R.id.tv_route_mode);btnSel=v.findViewById(R.id.btn_select_route);}}
    }
}
