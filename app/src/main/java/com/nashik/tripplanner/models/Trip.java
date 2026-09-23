package com.nashik.tripplanner.models;
import java.io.Serializable;
import java.util.List;
public class Trip implements Serializable {
    private String id,name,destination,startDate,endDate,travelType,mood,pace,status,emoji,colorHex,notes;
    private int budget,travelers;
    private List<ItineraryDay> itineraryDays;
    private PackingList packingList;
    private BudgetBreakdown budgetBreakdown;
    public Trip(){}
    public Trip(String id,String name,String dest,String sd,String ed,String tt,String mood,int budget,int travelers,String emoji,String color){
        this.id=id;this.name=name;this.destination=dest;this.startDate=sd;this.endDate=ed;
        this.travelType=tt;this.mood=mood;this.budget=budget;this.travelers=travelers;
        this.emoji=emoji;this.colorHex=color;this.status="upcoming";
    }
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getDestination(){return destination;} public void setDestination(String v){destination=v;}
    public String getStartDate(){return startDate;} public void setStartDate(String v){startDate=v;}
    public String getEndDate(){return endDate;} public void setEndDate(String v){endDate=v;}
    public String getTravelType(){return travelType;} public void setTravelType(String v){travelType=v;}
    public String getMood(){return mood;} public void setMood(String v){mood=v;}
    public String getPace(){return pace;} public void setPace(String v){pace=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public String getEmoji(){return emoji;} public void setEmoji(String v){emoji=v;}
    public String getColorHex(){return colorHex;} public void setColorHex(String v){colorHex=v;}
    public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
    public int getBudget(){return budget;} public void setBudget(int v){budget=v;}
    public int getTravelers(){return travelers;} public void setTravelers(int v){travelers=v;}
    public List<ItineraryDay> getItineraryDays(){return itineraryDays;} public void setItineraryDays(List<ItineraryDay> v){itineraryDays=v;}
    public PackingList getPackingList(){return packingList;} public void setPackingList(PackingList v){packingList=v;}
    public BudgetBreakdown getBudgetBreakdown(){return budgetBreakdown;} public void setBudgetBreakdown(BudgetBreakdown v){budgetBreakdown=v;}
    public String getMoodDisplayName(){if(mood==null)return"General";switch(mood){case"pilgrimage":return"Pilgrimage & Spiritual";case"wine_tour":return"Wine Tour & Leisure";case"adventure":return"Adventure & Trekking";case"heritage":return"Heritage & Culture";case"nature":return"Nature & Waterfalls";case"family":return"Family Fun";default:return"Nashik Exploration";}}
    public String getMoodEmoji(){if(mood==null)return"🌿";switch(mood){case"pilgrimage":return"🙏";case"wine_tour":return"🍷";case"adventure":return"🏔️";case"heritage":return"🏛️";case"nature":return"🌊";case"family":return"👨‍👩‍👧‍👦";default:return"🌿";}}
    public String getMoodColor(){if(mood==null)return"#1E5C9B";switch(mood){case"pilgrimage":return"#FF6B35";case"wine_tour":return"#8E44AD";case"adventure":return"#27AE60";case"heritage":return"#E8A020";case"nature":return"#2980B9";case"family":return"#E74C3C";default:return"#1E5C9B";}}
    public String getTravelDuration(){return startDate+" – "+endDate;}
    public int getDayCount(){return itineraryDays!=null?itineraryDays.size():3;}
}
