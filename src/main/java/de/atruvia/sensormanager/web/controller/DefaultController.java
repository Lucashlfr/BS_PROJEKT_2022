package de.atruvia.sensormanager.web.controller;

import de.atruvia.sensormanager.app.entities.ValueEntity;
import de.atruvia.sensormanager.cache.Cache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String getIndex(Model model) {

        ArrayList<ValueEntity> valueEntities = Cache.ENTRY_SERVICE.getEntities();

        String[] xIndex = new String[valueEntities.size()];
        for (int i = 0; i < xIndex.length; i++) {
            xIndex[i] = valueEntities.get(i).getDate() + "";
        }
        model.addAttribute("xIndex", Arrays.toString(xIndex)
                .replace("[", "")
                .replace("]", ""));

        String[] yTemp = new String[valueEntities.size()];
        for (int i = 0; i < yTemp.length; i++) {
            yTemp[i] = valueEntities.get(i).getTemperature() + "";
        }
        model.addAttribute("yTemp", Arrays.toString(yTemp)
                .replace("[", "")
                .replace("]", ""));

        String[] yPressure = new String[valueEntities.size()];
        for (int i = 0; i < yPressure.length; i++) {
            yPressure[i] = valueEntities.get(i).getPressure() + "";
        }
        model.addAttribute("yPressure", Arrays.toString(yPressure)
                .replace("[", "")
                .replace("]", ""));

        model.addAttribute("valueList", valueEntities);



        return "index";
    }

    @GetMapping("/tmp")
    public String getTMP() {
        return "tmp/load";
    }

}
