package ro.ubb.monitorweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.monitorweb.dto.CountyDto;

import java.util.List;

@RestController
public class MonitorController {
    public static final Logger log= LoggerFactory.getLogger(MonitorController.class);

    @RequestMapping(value = "/voting", method = RequestMethod.POST)
    public void addCounty(@RequestBody CountyDto countyDto) {
        log.trace("addCounty --- method entered: countyDto={}", countyDto);

        System.out.println("POST: add county");

        log.trace("addCounty --- method finished");
    }
}
