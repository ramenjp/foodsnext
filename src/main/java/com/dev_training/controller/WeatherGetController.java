package com.dev_training.controller;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherGetController {

@RequestMapping( value="/weather" , produces=MediaType.APPLICATION_JSON_VALUE , method=RequestMethod.GET)
        private String call() {
            RestTemplate rest = new RestTemplate();
            final String cityCode = "130010"; // 東京のCityCode
            final String endpoint = "http://weather.livedoor.com/forecast/webservice/json/v1";
            final String url = endpoint + "?city=" + cityCode;

            ResponseEntity<String> response = rest.getForEntity(url, String.class);
            String json = response.getBody();
            json = json.replace("\\n","");
            return(decode(json));
        }
            //日本語 2Byte対応
            private static String decode(String string) {
                return StringEscapeUtils.unescapeJava(string);
            }

        }
