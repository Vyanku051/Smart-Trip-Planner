package com.nashik.tripplanner.fragments;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import android.text.*;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.activities.NotificationsActivity;
import com.nashik.tripplanner.data.NashikData;
import com.nashik.tripplanner.models.Hotel;
import java.util.*;
public class HotelsFragment extends Fragment {
    private List<Hotel> all,filtered;private HotelAdapter adapter;private String sortKey="rating";
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater i,@Nullable ViewGroup c,@Nullable Bundle s){return i.inflate(R.layout.fragment_hotels,c,false);}
    @Override public void onViewCreated(@NonNull View v,@Nullable Bundle s){
        super.onViewCreated(v,s);all=NashikData.getHotels();filtered=new ArrayList<>(all);
        RecyclerView rv=v.findViewById(R.id.rv_hotels);if(rv!=null){rv.setLayoutManager(new LinearLayoutManager(requireContext()));adapter=new HotelAdapter(filtered);rv.setAdapter(adapter);}
        EditText search=v.findViewById(R.id.et_hotel_search);if(search!=null)search.addTextChangedListener(new TextWatcher(){@Override public void beforeTextChanged(CharSequence s2,int st,int c2,int a){}@Override public void onTextChanged(CharSequence s2,int st,int b,int c2){filter(s2.toString());}@Override public void afterTextChanged(Editable s2){}});
        int[]cids={R.id.chip_sort_rating,R.id.chip_sort_price,R.id.chip_sort_discount};String[]keys={"rating","price","discount"};
        for(int k=0;k<cids.length;k++){final String key=keys[k];TextView chip=v.findViewById(cids[k]);if(chip!=null)chip.setOnClickListener(c2->{sortKey=key;updateChips(v);sort();});}
        updateChips(v);
        View nb=v.findViewById(R.id.btn_notif_hotels);if(nb!=null)nb.setOnClickListener(c2->startActivity(new android.content.Intent(requireContext(),NotificationsActivity.class)));
    }
    void updateChips(View v){
        int[]cids={R.id.chip_sort_rating,R.id.chip_sort_price,R.id.chip_sort_discount};String[]keys={"rating","price","discount"};
        for(int k=0;k<cids.length;k++){TextView chip=v.findViewById(cids[k]);if(chip!=null){boolean active=keys[k].equals(sortKey);chip.setBackgroundResource(active?R.drawable.bg_chip_active:R.drawable.bg_chip_outline);chip.setTextColor(active?getResources().getColor(R.color.primary,null):getResources().getColor(R.color.text_hint,null));chip.setTypeface(null,active?android.graphics.Typeface.BOLD:android.graphics.Typeface.NORMAL);}}
    }
    void filter(String q){filtered.clear();String low=q.toLowerCase();for(Hotel h:all)if(h.getName().toLowerCase().contains(low)||h.getAddress().toLowerCase().contains(low))filtered.add(h);sort();}
    void sort(){switch(sortKey){case"price":filtered.sort((a,b)->a.getPricePerNight()-b.getPricePerNight());break;case"discount":filtered.sort((a,b)->b.getDiscountPercent()-a.getDiscountPercent());break;default:filtered.sort((a,b)->Float.compare(b.getRating(),a.getRating()));}if(adapter!=null)adapter.notifyDataSetChanged();}
    static class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.VH>{
        private final List<Hotel> hotels;
        HotelAdapter(List<Hotel> h){hotels=h;}
        @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int v){return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_hotel_card,p,false));}
        @Override public void onBindViewHolder(@NonNull VH h,int pos){
            Hotel hotel=hotels.get(pos);
            if(h.nm!=null)h.nm.setText(hotel.getName());if(h.rt!=null)h.rt.setText("★ "+hotel.getRating());if(h.addr!=null)h.addr.setText("📍 "+hotel.getAddress());if(h.desc!=null)h.desc.setText(hotel.getDescription());if(h.price!=null)h.price.setText(hotel.getFormattedPrice());if(h.amenities!=null)h.amenities.setText(hotel.getAmenitiesString());
            if(h.stars!=null){StringBuilder sb=new StringBuilder();for(int i=0;i<hotel.getStars();i++)sb.append("★");for(int i=hotel.getStars();i<5;i++)sb.append("☆");h.stars.setText(sb.toString());}
            if(h.disc!=null)h.disc.setVisibility(hotel.hasDiscount()?View.VISIBLE:View.GONE);if(hotel.hasDiscount()&&h.disc!=null)h.disc.setText(hotel.getDiscountPercent()+"% OFF");
            
            // Set hotel image
            if (h.img != null) {
                if (hotel.getImageUrl() != null) {
                    Glide.with(h.itemView.getContext())
                        .load(hotel.getImageUrl())
                        .transform(new CenterCrop(), new RoundedCorners(32))
                        .placeholder(R.drawable.ic_hotel)
                        .error(R.drawable.ic_hotel)
                        .into(h.img);
                    if (h.emojiOverlay != null) h.emojiOverlay.setVisibility(View.GONE);
                } else if (hotel.getImageName() != null) {
                    int resId = h.itemView.getContext().getResources().getIdentifier(hotel.getImageName(), "drawable", h.itemView.getContext().getPackageName());
                    if (resId != 0) {
                        h.img.setImageResource(resId);
                        if (h.emojiOverlay != null) h.emojiOverlay.setVisibility(View.GONE);
                    } else {
                        h.img.setImageResource(R.drawable.ic_hotel);
                        if (h.emojiOverlay != null) {
                            h.emojiOverlay.setVisibility(View.VISIBLE);
                            h.emojiOverlay.setText(hotel.getEmoji());
                        }
                    }
                }
            }

            if(h.btnBook!=null){
                h.btnBook.setText("Book Now");
                h.btnBook.setOnClickListener(v->{
                    String query = "https://www.google.com/search?q=" + hotel.getName() + "+hotel+Nashik+booking";
                    try {
                        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(query));
                        h.itemView.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(h.itemView.getContext(), "Browser not available", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        @Override public int getItemCount(){return hotels.size();}
        static class VH extends RecyclerView.ViewHolder{TextView nm,rt,addr,desc,price,amenities,stars,disc,emojiOverlay;ImageView img;com.google.android.material.button.MaterialButton btnBook;VH(@NonNull View v){super(v);nm=v.findViewById(R.id.tv_hotel_name);rt=v.findViewById(R.id.tv_hotel_rating);addr=v.findViewById(R.id.tv_hotel_address);desc=v.findViewById(R.id.tv_hotel_desc);price=v.findViewById(R.id.tv_hotel_price);amenities=v.findViewById(R.id.tv_hotel_amenities);stars=v.findViewById(R.id.tv_hotel_stars);disc=v.findViewById(R.id.tv_hotel_discount);img=v.findViewById(R.id.iv_hotel_image);btnBook=v.findViewById(R.id.btn_book_hotel);emojiOverlay=v.findViewById(R.id.tv_hotel_emoji_overlay);}}
    }
}
