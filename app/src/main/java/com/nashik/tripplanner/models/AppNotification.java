package com.nashik.tripplanner.models;import java.io.Serializable;
public class AppNotification implements Serializable {
    private String id,type,title,time,colorHex,iconEmoji;private boolean isToday,isRead;
    public AppNotification(){}
    public AppNotification(String id,String type,String title,String time,boolean today,boolean read,String color,String icon){this.id=id;this.type=type;this.title=title;this.time=time;isToday=today;isRead=read;colorHex=color;iconEmoji=icon;}
    public String getId(){return id;}public void setId(String v){id=v;}
    public String getType(){return type;}public void setType(String v){type=v;}
    public String getTitle(){return title;}public void setTitle(String v){title=v;}
    public String getTime(){return time;}public void setTime(String v){time=v;}
    public String getColorHex(){return colorHex;}public void setColorHex(String v){colorHex=v;}
    public String getIconEmoji(){return iconEmoji;}public void setIconEmoji(String v){iconEmoji=v;}
    public boolean isToday(){return isToday;}public void setToday(boolean v){isToday=v;}
    public boolean isRead(){return isRead;}public void setRead(boolean v){isRead=v;}
}
