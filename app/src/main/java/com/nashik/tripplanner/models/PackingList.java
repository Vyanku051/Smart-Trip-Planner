package com.nashik.tripplanner.models;import java.io.Serializable;import java.util.*;
public class PackingList implements Serializable {
    private List<PackingItem> items=new ArrayList<>();
    public List<PackingItem> getItems(){return items;}public void setItems(List<PackingItem> v){items=v;}
    public void addItem(PackingItem i){items.add(i);}
    public int getPackedCount(){int c=0;for(PackingItem i:items)if(i.isPacked())c++;return c;}
    public int getTotalCount(){return items.size();}
}
