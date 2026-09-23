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
import com.nashik.tripplanner.activities.*;
import com.nashik.tripplanner.data.NashikData;
import com.nashik.tripplanner.models.*;
import com.nashik.tripplanner.utils.*;
import java.util.*;
public class HomeFragment extends Fragment {
    private SessionManager sm; 
    private TripManager tm;

    @Nullable 
    @Override 
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle s) {
        // Inflate Home Fragment UI
        return i.inflate(R.layout.fragment_home, c, false);
    }

    @Override 
    public void onViewCreated(@NonNull View v, @Nullable Bundle s) {
        super.onViewCreated(v, s);
        sm = new SessionManager(requireContext());
        tm = new TripManager(requireContext());
        setupAll(v);
    }

    @Override 
    public void onResume() {
        super.onResume();
        if (getView() != null) {
            setupTrips(getView());
            updateBadge(getView());
        }
    }

    /**
     * Initializes all components of the Home screen
     */
    void setupAll(View v) {
        setupHeader(v);
        setupQuickActions(v);
        setupTrips(v);
        setupAttractions(v);
    }

    /**
     * Sets up user greetings and notification icon in the header
     */
    void setupHeader(View v) {
        TextView tvName = v.findViewById(R.id.tv_hello_name);
        TextView tvGreet = v.findViewById(R.id.tv_greeting);
        TextView tvAv = v.findViewById(R.id.tv_avatar);
        
        if (tvName != null) tvName.setText("Hello, " + sm.getFirstName() + "!");
        
        if (tvGreet != null) {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            tvGreet.setText(hour < 12 ? "Good Morning 🌿" : hour < 17 ? "Good Afternoon ☀️" : "Good Evening 🌙");
        }
        
        if (tvAv != null) tvAv.setText(sm.getUserInitials());
        
        View btnNotif = v.findViewById(R.id.btn_notifications);
        if (btnNotif != null) {
            btnNotif.setOnClickListener(b -> startActivity(new Intent(requireContext(), NotificationsActivity.class)));
        }
        
        updateBadge(v);
    }

    /**
     * Updates the red dot badge on the notification icon
     */
    void updateBadge(View v) {
        View badge = v.findViewById(R.id.notif_badge);
        if (badge != null) {
            badge.setVisibility(new NotificationStore(requireContext()).getUnreadCount() > 0 ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Sets up click listeners for the quick action buttons (New Trip, Chat, etc.)
     */
    void setupQuickActions(View v) {
        // New Trip Button
        View btnNew = v.findViewById(R.id.btn_new_trip);
        if (btnNew != null) btnNew.setOnClickListener(b -> startActivity(new Intent(requireContext(), TripCreationActivity.class)));
        
        // AI Chat Button
        View btnChat = v.findViewById(R.id.btn_ai_chat);
        if (btnChat != null) btnChat.setOnClickListener(b -> ((MainActivity) requireActivity()).switchTo(MainActivity.TAB_CHAT));
        
        // Map Quick Access
        View btnMap = v.findViewById(R.id.btn_map_quick);
        if (btnMap != null) btnMap.setOnClickListener(b -> ((MainActivity) requireActivity()).switchTo(MainActivity.TAB_MAP));
        
        // External Google Maps navigation to Nashik
        View btnRoutes = v.findViewById(R.id.btn_routes_quick);
        if (btnRoutes != null) {
            btnRoutes.setOnClickListener(b -> {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=Nashik,Maharashtra");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                try {
                    startActivity(mapIntent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination=Nashik")));
                }
            });
        }
    }

    /**
     * Populates the "Your Trips" horizontal list
     */
    void setupTrips(View v) {
        RecyclerView rv = v.findViewById(R.id.rv_trips);
        if (rv == null) return;
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(new TripAdapter(tm.getAll(), t -> {
            Intent i = new Intent(requireContext(), TripOverviewActivity.class);
            i.putExtra("trip_id", t.getId());
            startActivity(i);
        }));
    }

    /**
     * Populates the "Top Attractions" horizontal list
     */
    void setupAttractions(View v) {
        RecyclerView rv = v.findViewById(R.id.rv_attractions);
        if (rv == null) return;
        rv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(new AttrAdapter(NashikData.getAttractions()));
    }

    /**
     * Adapter for showing Trip cards
     */
    static class TripAdapter extends RecyclerView.Adapter<TripAdapter.VH> {
        private final List<Trip> trips;
        private final CB cb;
        interface CB { void on(Trip t); }
        TripAdapter(List<Trip> t, CB c) { trips = t; cb = c; }
        @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
            return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_trip_card, p, false));
        }
        @Override public void onBindViewHolder(@NonNull VH h, int pos) {
            Trip t = trips.get(pos);
            if (h.ivIcon != null) h.ivIcon.setImageResource(NashikData.getMoodIcon(t.getMood()));
            if (h.nm != null) h.nm.setText(t.getName());
            if (h.dt != null) h.dt.setText("📅 " + t.getTravelDuration());
            if (h.tg != null) {
                h.tg.setText(t.getMoodDisplayName());
                try { h.tg.setTextColor(Color.parseColor(t.getMoodColor())); } catch (Exception ignored) {}
            }
            if (h.bg != null) {
                try { h.bg.setBackgroundColor(Color.parseColor(t.getColorHex())); } catch (Exception ignored) {}
            }
            if (h.nb != null) h.nb.setText(t.getDayCount() + " days");
            h.itemView.setOnClickListener(v -> cb.on(t));
        }
        @Override public int getItemCount() { return trips.size(); }
        static class VH extends RecyclerView.ViewHolder {
            TextView nm, dt, tg, nb; ImageView ivIcon; View bg;
            VH(@NonNull View v) { super(v); ivIcon = v.findViewById(R.id.iv_trip_emoji); nm = v.findViewById(R.id.tv_trip_name); dt = v.findViewById(R.id.tv_trip_dates); tg = v.findViewById(R.id.tv_trip_tag); nb = v.findViewById(R.id.tv_nights_badge); bg = v.findViewById(R.id.trip_card_bg); }
        }
    }

    /**
     * Adapter for showing Attraction cards
     */
    static class AttrAdapter extends RecyclerView.Adapter<AttrAdapter.VH> {
        private final List<Attraction> list;
        AttrAdapter(List<Attraction> l) { list = l; }
        @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
            return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_attraction_card, p, false));
        }
        @Override public void onBindViewHolder(@NonNull VH h, int pos) {
            Attraction a = list.get(pos);
            if (h.em != null) h.em.setText(a.getEmoji());
            if (h.nm != null) h.nm.setText(a.getName());
            if (h.rt != null) h.rt.setText("★ " + a.getRating());
            if (h.fe != null) h.fe.setText(a.getEntryFee());
        }
        @Override public int getItemCount() { return Math.min(list.size(), 8); }
        static class VH extends RecyclerView.ViewHolder {
            TextView em, nm, rt, fe;
            VH(@NonNull View v) { super(v); em = v.findViewById(R.id.tv_attr_emoji); nm = v.findViewById(R.id.tv_attr_name); rt = v.findViewById(R.id.tv_attr_rating); fe = v.findViewById(R.id.tv_attr_fee); }
        }
    }
}
