package com.example.weatherappv1.model;

import java.io.Serializable;

import jakarta.json.JsonObject;

public class Condition implements Serializable {
    private String description;
    private String icon;


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static Condition createJSON(JsonObject o) {
        var c = new Condition();
        c.description = "%s - %s"
            .formatted(o.getString("main")
            , o.getString("description"));
        
        c.icon = "https://openweathermap.org/img/wn/%s@4x.png"
            .formatted(o.getString("icon"));

        return c;
            
    }
    
}
