package com.example.vclab.shipcrew.Model;

/**
 * Created by Aiden on 2018-08-31.
 */

public class PositionModel {

    public Double lat;
    public Double lon;
    public String name;
    public String ts;

    public PositionModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public PositionModel(Double lat, Double lon, String name, String ts) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.ts = ts;
    }

    public Double getLat(){
        return lat;
    }
    public Double getLon(){
        return lon;
    }

    public String getName(){ return name; }
    public String getTS() { return ts; }

}
