package com.nashik.tripplanner.fragments;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.data.NashikData;
import com.nashik.tripplanner.models.Attraction;
import java.util.*;

public class MapFragment extends Fragment {
    private List<Attraction> all, filtered;
    private AttrListAdapter listAdapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle s) {
        return i.inflate(R.layout.fragment_map, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle s) {
        super.onViewCreated(v, s);
        all = NashikData.getAttractions();
        filtered = new ArrayList<>(all);

        RecyclerView rv = v.findViewById(R.id.rv_attractions_list);
        if (rv != null) {
            rv.setLayoutManager(new LinearLayoutManager(requireContext()));
            listAdapter = new AttrListAdapter(filtered);
            rv.setAdapter(listAdapter);
        }

        RecyclerView rvCats = v.findViewById(R.id.rv_categories);
        if (rvCats != null) {
            rvCats.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            String[] cats = {"All", "Pilgrimage", "Wine Tour", "Trekking", "Nature", "Heritage", "Culture"};
            rvCats.setAdapter(new CatAdapter(cats, this::filterBy));
        }

        // Action to open Google Maps directly
        View.OnClickListener openMapsListener = b -> {
            Uri gmmIntentUri = Uri.parse("geo:19.9975,73.7898?q=attractions+in+Nashik");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            try {
                startActivity(mapIntent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=Nashik+attractions")));
            }
        };

        View btnFull = v.findViewById(R.id.btn_full_map);
        if (btnFull != null) btnFull.setOnClickListener(openMapsListener);

        View tvTitle = v.findViewById(R.id.tv_title_map);
        if (tvTitle != null) tvTitle.setOnClickListener(openMapsListener);
    }

    void filterBy(String cat) {
        filtered.clear();
        for (Attraction a : all) if ("All".equals(cat) || a.getCategory().equals(cat)) filtered.add(a);
        if (listAdapter != null) listAdapter.notifyDataSetChanged();
    }

    static class AttrListAdapter extends RecyclerView.Adapter<AttrListAdapter.VH> {
        private final List<Attraction> list;
        AttrListAdapter(List<Attraction> l) { list = l; }

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
            return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_attraction_list, p, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int pos) {
            Attraction a = list.get(pos);
            if (h.em != null) h.em.setText(a.getEmoji());
            if (h.nm != null) h.nm.setText(a.getName());
            if (h.tm != null) h.tm.setText(a.getTiming());
            if (h.rt != null) h.rt.setText("★ " + a.getRating());
            if (h.fe != null) h.fe.setText(a.getEntryFee());
            if (h.dur != null) h.dur.setText("⏱️ " + a.getDuration());
            
            // Clicking an item can also offer to open in Maps
            h.itemView.setOnClickListener(v -> {
                Uri gmmIntentUri = Uri.parse("geo:" + a.getLatitude() + "," + a.getLongitude() + "?q=" + Uri.encode(a.getName()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                try {
                    h.itemView.getContext().startActivity(mapIntent);
                } catch (Exception e) {
                    h.itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(a.getName()))));
                }
            });
        }

        @Override public int getItemCount() { return list.size(); }

        static class VH extends RecyclerView.ViewHolder {
            TextView em, nm, tm, rt, fe, dur;
            VH(@NonNull View v) {
                super(v);
                em = v.findViewById(R.id.tv_attr_emoji);
                nm = v.findViewById(R.id.tv_attr_name);
                tm = v.findViewById(R.id.tv_attr_timing);
                rt = v.findViewById(R.id.tv_attr_rating);
                fe = v.findViewById(R.id.tv_attr_fee);
                dur = v.findViewById(R.id.tv_attr_duration);
            }
        }
    }

    static class CatAdapter extends RecyclerView.Adapter<CatAdapter.VH> {
        private final String[] cats;
        private int sel = 0;
        interface CB { void on(String c); }
        private final CB cb;

        CatAdapter(String[] c, CB cb) { cats = c; this.cb = cb; }

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
            return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_category_chip, p, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int pos) {
            h.tv.setText(cats[pos]);
            h.tv.setTextColor(pos == sel ? Color.parseColor("#1E5C9B") : Color.parseColor("#9E9E9E"));
            h.itemView.setOnClickListener(v -> {
                int prev = sel;
                sel = h.getAdapterPosition();
                notifyItemChanged(prev);
                notifyItemChanged(sel);
                cb.on(cats[sel]);
            });
        }

        @Override public int getItemCount() { return cats.length; }
        static class VH extends RecyclerView.ViewHolder {
            TextView tv;
            VH(@NonNull View v) { super(v); tv = v.findViewById(R.id.tv_category_name); }
        }
    }
}
