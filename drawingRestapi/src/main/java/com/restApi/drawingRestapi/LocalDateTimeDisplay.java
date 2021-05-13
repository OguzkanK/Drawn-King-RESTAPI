package com.restApi.drawingRestapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
public class LocalDateTimeDisplay {

    @RequestMapping(method = RequestMethod.GET, path = "/time")
    public String displayCurrentTime(){
        String returnVal = "The current time is: " + LocalDateTime.now().toString();
        return returnVal;
    }

}
