package com.nashik.tripplanner.utils;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nashik.tripplanner.data.NashikData;
import com.nashik.tripplanner.models.AppNotification;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class NotificationStore {
    private static final String P="NashikNotifs",K="notifs";
    private final SharedPreferences prefs;
    private final Gson gson=new Gson();
    public NotificationStore(Context c){prefs=c.getSharedPreferences(P,Context.MODE_PRIVATE);}
    public List<AppNotification> getAll(){
        String json=prefs.getString(K,null);
        if(json==null){List<AppNotification> d=NashikData.getDefaultNotifications();save(d);return d;}
        Type type=new TypeToken<List<AppNotification>>(){}.getType();
        List<AppNotification> list=gson.fromJson(json,type);
        return list!=null?list:NashikData.getDefaultNotifications();
    }
    public void save(List<AppNotification> list){prefs.edit().putString(K,gson.toJson(list)).apply();}
    public void markRead(String id){List<AppNotification> l=getAll();for(AppNotification n:l)if(n.getId().equals(id))n.setRead(true);save(l);}
    public void markAllRead(){List<AppNotification> l=getAll();for(AppNotification n:l)n.setRead(true);save(l);}
    public void clearAll(){save(new ArrayList<>());}
    public void deleteById(String id){List<AppNotification> l=getAll();l.removeIf(n->n.getId().equals(id));save(l);}
    public int getUnreadCount(){int c=0;for(AppNotification n:getAll())if(!n.isRead())c++;return c;}
    public void add(AppNotification n){List<AppNotification> l=getAll();l.add(0,n);save(l);}
}
