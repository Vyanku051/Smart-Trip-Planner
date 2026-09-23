package com.nashik.tripplanner.models;import java.io.Serializable;
public class PackingItem implements Serializable {
    private String id,name;private boolean packed;
    public PackingItem(){}public PackingItem(String i,String n,boolean p){id=i;name=n;packed=p;}
    public String getId(){return id;}public void setId(String v){id=v;}
    public String getName(){return name;}public void setName(String v){name=v;}
    public boolean isPacked(){return packed;}public void setPacked(boolean v){packed=v;}
}
