package com.nashik.tripplanner.models;
import java.io.Serializable;import java.util.*;
public class ItineraryDay implements Serializable {
    private int dayNumber;private String date,title,accommodation;private int accommodationCost;
    private List<ItineraryItem> activities=new ArrayList<>();
    public ItineraryDay(){}
    public ItineraryDay(int n,String d,String t){dayNumber=n;date=d;title=t;}
    public int getDayNumber(){return dayNumber;}public void setDayNumber(int v){dayNumber=v;}
    public String getDate(){return date;}public void setDate(String v){date=v;}
    public String getTitle(){return title;}public void setTitle(String v){title=v;}
    public String getAccommodation(){return accommodation;}public void setAccommodation(String v){accommodation=v;}
    public int getAccommodationCost(){return accommodationCost;}public void setAccommodationCost(int v){accommodationCost=v;}
    public List<ItineraryItem> getActivities(){return activities;}public void setActivities(List<ItineraryItem> v){activities=v;}
    public void addActivity(ItineraryItem i){activities.add(i);}
}
