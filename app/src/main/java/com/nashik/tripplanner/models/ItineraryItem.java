package com.nashik.tripplanner.models;import java.io.Serializable;
public class ItineraryItem implements Serializable {
    private String time,name,location,type,emoji;
    public ItineraryItem(){}
    public ItineraryItem(String t,String n,String l,String tp,String e){time=t;name=n;location=l;type=tp;emoji=e;}
    public String getTime(){return time;}public void setTime(String v){time=v;}
    public String getName(){return name;}public void setName(String v){name=v;}
    public String getLocation(){return location;}public void setLocation(String v){location=v;}
    public String getType(){return type;}public void setType(String v){type=v;}
    public String getEmoji(){return emoji;}public void setEmoji(String v){emoji=v;}
}
