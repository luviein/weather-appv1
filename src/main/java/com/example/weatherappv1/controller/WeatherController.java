package com.example.weatherappv1.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.weatherappv1.model.Weather;
import com.example.weatherappv1.service.WeatherService;

@Controller
public class WeatherController {
    
    @Autowired
    private WeatherService svc;

    @GetMapping(path="weather")
    public String getWeather(@RequestParam(required=true) String city, 
            @RequestParam(defaultValue = "metric", required=false) String units, 
            Model m) throws IOException{
        
        Optional<Weather> weather = svc.getWeather(city, units);
        //.get() retrieves value from Optional
        m.addAttribute("weather", weather.get());
        
        return "weather";
    }


}
