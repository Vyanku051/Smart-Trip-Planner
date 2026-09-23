package com.nashik.tripplanner.models;import java.io.Serializable;
public class ChatMessage implements Serializable {
    private String id,message,time;private boolean isUser,isTyping;
    public ChatMessage(){}
    public ChatMessage(String id,String msg,boolean user,String time){this.id=id;message=msg;isUser=user;this.time=time;}
    public static ChatMessage typing(){ChatMessage m=new ChatMessage("typing","...",false,"");m.setTyping(true);return m;}
    public String getId(){return id;}public void setId(String v){id=v;}
    public String getMessage(){return message;}public void setMessage(String v){message=v;}
    public String getTime(){return time;}public void setTime(String v){time=v;}
    public boolean isUser(){return isUser;}public void setUser(boolean v){isUser=v;}
    public boolean isTyping(){return isTyping;}public void setTyping(boolean v){isTyping=v;}
}
