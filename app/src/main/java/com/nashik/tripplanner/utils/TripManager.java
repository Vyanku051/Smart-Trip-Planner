package com.nashik.tripplanner.utils;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nashik.tripplanner.data.NashikData;
import com.nashik.tripplanner.models.Trip;
import java.lang.reflect.Type;
import java.util.List;
public class TripManager {
    private static final String P="NashikTrips",K="trips";
    private final SharedPreferences prefs;
    private final Gson gson=new Gson();
    public TripManager(Context c){prefs=c.getSharedPreferences(P,Context.MODE_PRIVATE);}
    public void saveTrip(Trip t){
        List<Trip> list=getAll();boolean found=false;
        for(int i=0;i<list.size();i++){if(list.get(i).getId().equals(t.getId())){list.set(i,t);found=true;break;}}
        if(!found)list.add(0,t);
        prefs.edit().putString(K,gson.toJson(list)).apply();
    }
    public List<Trip> getAll(){
        String json=prefs.getString(K,null);
        if(json==null)return NashikData.getDefaultTrips();
        Type type=new TypeToken<List<Trip>>(){}.getType();
        List<Trip> list=gson.fromJson(json,type);
        return list!=null?list:NashikData.getDefaultTrips();
    }
    public Trip getById(String id){for(Trip t:getAll())if(t.getId().equals(id))return t;return null;}
    public void delete(String id){List<Trip> list=getAll();list.removeIf(t->t.getId().equals(id));prefs.edit().putString(K,gson.toJson(list)).apply();}
    public int count(){return getAll().size();}
}
