package com.nashik.tripplanner.models;import java.io.Serializable;
public class Attraction implements Serializable {
    private String id,name,category,description,address,timing,entryFee,duration,emoji;
    private float rating;private double latitude,longitude;
    public Attraction(){}
    public Attraction(String id,String name,String cat,String desc,String addr,String timing,String fee,float rating,String dur,String emoji,double lat,double lng){this.id=id;this.name=name;this.category=cat;this.description=desc;this.address=addr;this.timing=timing;this.entryFee=fee;this.rating=rating;this.duration=dur;this.emoji=emoji;this.latitude=lat;this.longitude=lng;}
    public String getId(){return id;}public void setId(String v){id=v;}
    public String getName(){return name;}public void setName(String v){name=v;}
    public String getCategory(){return category;}public void setCategory(String v){category=v;}
    public String getDescription(){return description;}public void setDescription(String v){description=v;}
    public String getAddress(){return address;}public void setAddress(String v){address=v;}
    public String getTiming(){return timing;}public void setTiming(String v){timing=v;}
    public String getEntryFee(){return entryFee;}public void setEntryFee(String v){entryFee=v;}
    public String getDuration(){return duration;}public void setDuration(String v){duration=v;}
    public String getEmoji(){return emoji;}public void setEmoji(String v){emoji=v;}
    public float getRating(){return rating;}public void setRating(float v){rating=v;}
    public double getLatitude(){return latitude;}public void setLatitude(double v){latitude=v;}
    public double getLongitude(){return longitude;}public void setLongitude(double v){longitude=v;}
}
