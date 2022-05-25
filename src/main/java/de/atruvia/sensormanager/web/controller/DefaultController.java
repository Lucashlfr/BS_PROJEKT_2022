package de.atruvia.sensormanager.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/tmp")
    public String getTMP(){
        return "tmp/load";
    }

}
