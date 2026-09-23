package com.nashik.tripplanner.models;import java.io.Serializable;
public class Route implements Serializable {
    private String id,from,to,distance,duration,traffic,fare,mode,via;
    private double fromLat,fromLng,toLat,toLng;
    public Route(){}
    public Route(String id,String from,String to,String dist,String dur,String tr,String fare,String mode,String via){this.id=id;this.from=from;this.to=to;this.distance=dist;this.duration=dur;this.traffic=tr;this.fare=fare;this.mode=mode;this.via=via;}
    public String getId(){return id;}public void setId(String v){id=v;}
    public String getFrom(){return from;}public void setFrom(String v){from=v;}
    public String getTo(){return to;}public void setTo(String v){to=v;}
    public String getDistance(){return distance;}public void setDistance(String v){distance=v;}
    public String getDuration(){return duration;}public void setDuration(String v){duration=v;}
    public String getTraffic(){return traffic;}public void setTraffic(String v){traffic=v;}
    public String getFare(){return fare;}public void setFare(String v){fare=v;}
    public String getMode(){return mode;}public void setMode(String v){mode=v;}
    public String getVia(){return via;}public void setVia(String v){via=v;}
    public double getFromLat(){return fromLat;}public void setFromLat(double v){fromLat=v;}
    public double getFromLng(){return fromLng;}public void setFromLng(double v){fromLng=v;}
    public double getToLat(){return toLat;}public void setToLat(double v){toLat=v;}
    public double getToLng(){return toLng;}public void setToLng(double v){toLng=v;}
    public String getTrafficLabel(){if("low".equals(traffic))return"Light Traffic";if("heavy".equals(traffic))return"Heavy Traffic";return"Moderate";}
    public int getTrafficColor(){if("low".equals(traffic))return 0xFF27AE60;if("heavy".equals(traffic))return 0xFFE74C3C;return 0xFFE8A020;}
}
