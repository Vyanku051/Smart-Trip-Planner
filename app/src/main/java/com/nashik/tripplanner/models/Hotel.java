package com.nashik.tripplanner.models;import java.io.Serializable;import java.util.*;
public class Hotel implements Serializable {
    private String id,name,address,description,emoji,phone,imageName,imageUrl;
    private int stars,pricePerNight,discountPercent;private float rating;private double latitude,longitude;
    private List<String> amenities;
    public Hotel(){}
    public Hotel(String id,String name,int stars,float rating,int price,String addr,int disc,String desc,String emoji,List<String> am){this.id=id;this.name=name;this.stars=stars;this.rating=rating;this.pricePerNight=price;this.address=addr;this.discountPercent=disc;this.description=desc;this.emoji=emoji;this.amenities=am;}
    public String getImageName(){return imageName;}public void setImageName(String v){imageName=v;}
    public String getImageUrl(){return imageUrl;}public void setImageUrl(String v){imageUrl=v;}
    public String getId(){return id;}public void setId(String v){id=v;}
    public String getName(){return name;}public void setName(String v){name=v;}
    public String getAddress(){return address;}public void setAddress(String v){address=v;}
    public String getDescription(){return description;}public void setDescription(String v){description=v;}
    public String getEmoji(){return emoji;}public void setEmoji(String v){emoji=v;}
    public String getPhone(){return phone;}public void setPhone(String v){phone=v;}
    public int getStars(){return stars;}public void setStars(int v){stars=v;}
    public int getPricePerNight(){return pricePerNight;}public void setPricePerNight(int v){pricePerNight=v;}
    public int getDiscountPercent(){return discountPercent;}public void setDiscountPercent(int v){discountPercent=v;}
    public float getRating(){return rating;}public void setRating(float v){rating=v;}
    public double getLatitude(){return latitude;}public void setLatitude(double v){latitude=v;}
    public double getLongitude(){return longitude;}public void setLongitude(double v){longitude=v;}
    public List<String> getAmenities(){return amenities;}public void setAmenities(List<String> v){amenities=v;}
    public boolean hasDiscount(){return discountPercent>0;}
    public int getDiscountedPrice(){return hasDiscount()?(int)(pricePerNight*(1-discountPercent/100.0)):pricePerNight;}
    public String getStarsString(){StringBuilder sb=new StringBuilder();for(int i=0;i<stars;i++)sb.append("★");for(int i=stars;i<5;i++)sb.append("☆");return sb.toString();}
    public String getFormattedPrice(){return String.format(java.util.Locale.getDefault(),"₹%,d",pricePerNight);}
    public String getAmenitiesString(){if(amenities==null||amenities.isEmpty())return"";return String.join(" • ",amenities);}
}
