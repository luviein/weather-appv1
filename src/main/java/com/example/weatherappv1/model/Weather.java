package com.example.weatherappv1.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Weather implements Serializable {
    private double temp;
    private double tempFeelsLike;
    private double tempMin;
    private double tempMax;
    private Long date;
    private Long sunRise;
    private Long sunSet;
    private List<Condition> condition = new LinkedList<>();

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {this.temp = temp;}

    public double getTempFeelsLike() {return tempFeelsLike;}

    public void setTempFeelsLike(double tempFeelsLike) {this.tempFeelsLike = tempFeelsLike;}

    public double getTempMin() {return tempMin;}

    public void setTempMin(double tempMin) {this.tempMin = tempMin;}

    public double getTempMax() {return tempMax;}

    public void setTempMax(double tempMax) {this.tempMax = tempMax;}

    public Long getDate() {return date;}

    public void setDate(Long date) {this.date = date;}

    public Long getSunRise() {return sunRise;}

    public void setSunRise(Long sunRise) {this.sunRise = sunRise;}

    public Long getSunSet() {return sunSet;}

    public void setSunSet(Long sunSet) {this.sunSet = sunSet;}
    
    
    public List<Condition> getCondition() {return condition;}

    public void setCondition(List<Condition> condition) {this.condition = condition;}



    // Converts json string to Weather object
    public static Weather createJSON(String json) throws IOException {
        var w = new Weather();
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            // create a json reader to read bytes from json string
            JsonReader r = Json.createReader(is);
            // puts read data from JsonReader into JsonObject
            JsonObject o = r.readObject();
            //gets temp value under "main" key
            JsonObject mainObj = o.getJsonObject("main");
            w.setTemp(mainObj.getJsonNumber("temp").doubleValue());
            w.setTempFeelsLike(mainObj.getJsonNumber("feels_like").doubleValue());
            w.setTempMin(mainObj.getJsonNumber("temp_min").doubleValue());
            w.setTempMax(mainObj.getJsonNumber("temp_max").doubleValue());
            //gets date which only has one value
            JsonNumber dateObj = o.getJsonNumber("dt");
            w.setDate(dateObj.longValue());
            //gets sunrise & sunset value under "sys" key
            JsonObject sysObj = o.getJsonObject("sys");
            w.setSunRise(sysObj.getJsonNumber("sunrise").longValue());
            w.setSunSet(sysObj.getJsonNumber("sunset").longValue());

            w.condition = o.getJsonArray("weather")
                .stream()
                //changes item in json array to JsonObject
                .map(v -> (JsonObject) v)
                //maps each JsonObject to return a Condition object
                .map(v -> Condition.createJSON(v))
                //store all Condition objects in a List
                .toList();

        }
        return w;
    }

}
