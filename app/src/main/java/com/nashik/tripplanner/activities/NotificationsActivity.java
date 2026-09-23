package com.nashik.tripplanner.activities;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.models.AppNotification;
import com.nashik.tripplanner.utils.NotificationStore;
import java.util.*;
public class NotificationsActivity extends AppCompatActivity {
    private RecyclerView rvToday,rvEarlier; private TextView tvUnread,tvToday,tvEarlier;
    private View layoutEmpty,divider; private NotificationStore store;
    private List<AppNotification> todayList=new ArrayList<>(),earlierList=new ArrayList<>();
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);setContentView(R.layout.activity_notifications);
        store=new NotificationStore(this);
        View back=findViewById(R.id.iv_back);if(back!=null)back.setOnClickListener(v->onBackPressed());
        rvToday=findViewById(R.id.rv_today);rvEarlier=findViewById(R.id.rv_earlier);
        tvUnread=findViewById(R.id.tv_unread_count);tvToday=findViewById(R.id.tv_today_header);tvEarlier=findViewById(R.id.tv_earlier_header);
        layoutEmpty=findViewById(R.id.layout_empty);divider=findViewById(R.id.notif_divider);
        if(rvToday!=null)rvToday.setLayoutManager(new LinearLayoutManager(this));
        if(rvEarlier!=null)rvEarlier.setLayoutManager(new LinearLayoutManager(this));
        // CLEAR ALL - fully working
        View btnClear=findViewById(R.id.btn_clear_all);
        if(btnClear!=null)btnClear.setOnClickListener(v->new AlertDialog.Builder(this).setTitle("Clear All Notifications").setMessage("Remove all notifications? This cannot be undone.").setPositiveButton("Clear All",(d,w)->{store.clearAll();loadData();Toast.makeText(this,"All notifications cleared",Toast.LENGTH_SHORT).show();}).setNegativeButton("Cancel",null).show());
        // Mark all read
        View btnMark=findViewById(R.id.btn_mark_all_read);if(btnMark!=null)btnMark.setOnClickListener(v->{store.markAllRead();loadData();Toast.makeText(this,"All marked as read",Toast.LENGTH_SHORT).show();});
        loadData();
    }
    void loadData(){
        List<AppNotification> all=store.getAll();
        todayList.clear();earlierList.clear();
        for(AppNotification n:all){if(n.isToday())todayList.add(n);else earlierList.add(n);}
        if(rvToday!=null)rvToday.setAdapter(new NA(todayList,n->{store.markRead(n.getId());loadData();},n->{store.deleteById(n.getId());loadData();}));
        if(rvEarlier!=null)rvEarlier.setAdapter(new NA(earlierList,n->{store.markRead(n.getId());loadData();},n->{store.deleteById(n.getId());loadData();}));
        boolean ht=!todayList.isEmpty(),he=!earlierList.isEmpty(),empty=!ht&&!he;
        if(tvToday!=null)tvToday.setVisibility(ht?View.VISIBLE:View.GONE);if(rvToday!=null)rvToday.setVisibility(ht?View.VISIBLE:View.GONE);
        if(divider!=null)divider.setVisibility(ht&&he?View.VISIBLE:View.GONE);
        if(tvEarlier!=null)tvEarlier.setVisibility(he?View.VISIBLE:View.GONE);if(rvEarlier!=null)rvEarlier.setVisibility(he?View.VISIBLE:View.GONE);
        if(layoutEmpty!=null)layoutEmpty.setVisibility(empty?View.VISIBLE:View.GONE);
        int unread=store.getUnreadCount();if(tvUnread!=null)tvUnread.setText(unread>0?unread+" unread":"All read");
    }
    @Override protected void onResume(){super.onResume();loadData();}
    interface Act{void run(AppNotification n);}
    static class NA extends RecyclerView.Adapter<NA.VH>{
        private final List<AppNotification> list; private final Act onTap,onDel;
        NA(List<AppNotification> l,Act tap,Act del){list=l;onTap=tap;onDel=del;}
        @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int v){return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_notification,p,false));}
        @Override public void onBindViewHolder(@NonNull VH h,int pos){
            AppNotification n=list.get(pos);
            if(h.tvEmoji!=null)h.tvEmoji.setText(n.getIconEmoji());
            if(h.tvTitle!=null)h.tvTitle.setText(n.getTitle());
            if(h.tvTime!=null)h.tvTime.setText(n.getTime());
            try{if(h.iconBg!=null)h.iconBg.setBackgroundColor(Color.parseColor(n.getColorHex()));}catch(Exception ignored){}
            if(h.dot!=null)h.dot.setVisibility(n.isRead()?View.GONE:View.VISIBLE);
            h.itemView.setBackgroundColor(n.isRead()?Color.WHITE:Color.parseColor("#F0F7FF"));
            h.itemView.setOnClickListener(v->onTap.run(n));
            if(h.btnDel!=null)h.btnDel.setOnClickListener(v->onDel.run(n));
        }
        @Override public int getItemCount(){return list.size();}
        static class VH extends RecyclerView.ViewHolder{TextView tvEmoji,tvTitle,tvTime;View iconBg,dot,btnDel;VH(@NonNull View v){super(v);tvEmoji=v.findViewById(R.id.tv_notif_emoji);tvTitle=v.findViewById(R.id.tv_notif_title);tvTime=v.findViewById(R.id.tv_notif_time);iconBg=v.findViewById(R.id.notif_icon_bg);dot=v.findViewById(R.id.unread_dot);btnDel=v.findViewById(R.id.btn_delete_notif);}}
    }
}
