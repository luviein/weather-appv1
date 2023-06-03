package com.example.weatherappv1.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.weatherappv1.model.Weather;

@Service
public class WeatherService {
    @Value("${workshop17.open.weather.url}")
    private String openWeatherAPIUrl;

    @Value("${workshop17.open.weather.key}")
    private String openWeatherAPIKey;

    //construct URL
    public Optional<Weather> getWeather(String city, String unitMeasurement) throws IOException {
        String weatherURL = UriComponentsBuilder.fromUriString(openWeatherAPIUrl)
                    .queryParam("q", city.replaceAll(" ", "+"))
                    .queryParam("units", unitMeasurement)
                    .queryParam("appId", openWeatherAPIKey)
                    .toUriString();
        
        System.out.println("Weather URL: " + weatherURL);

        //Rest Template makes HTTP requests to external APIs
        RestTemplate template = new RestTemplate();

        //HTTP response received from server when making HTTP request using RestTemplate
        //ResponseEntity is expected to be a String
        //getForEntity uses GET request to specified URL and retrieve response as Response Entity object
        //String.class is expected response type
        ResponseEntity<String> res = template.getForEntity(weatherURL, String.class);

        //getBody retrieves JSON data from API > creates JSON object and set to Model based on values stated
        Weather w = Weather.createJSON(res.getBody());
        if(w != null){
            return Optional.of(w);
        }
        return Optional.empty();
    }
}
