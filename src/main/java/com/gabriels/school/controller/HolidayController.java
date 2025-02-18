package com.gabriels.school.controller;

import com.gabriels.school.model.Holiday;
import com.gabriels.school.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Controller
public class HolidayController {

    /*@RequestMapping(value = {"/holidays"}, method= RequestMethod.GET)
    public String displayHolidays(@RequestParam(required=false) boolean festival, @RequestParam(required=false) boolean federal, Model model){
        model.addAttribute("festival",festival);
        model.addAttribute("federal",federal);
        List<Holiday> holidaysList= Arrays.asList(
                new Holiday("January 1", "New Year's Day",Holiday.Type.FESTIVAL),
                new Holiday("January 26", "Republic Day",Holiday.Type.FEDERAL),
                new Holiday("March 29", "Holi",Holiday.Type.FESTIVAL),
                new Holiday("April 2", "Good Friday",Holiday.Type.FESTIVAL),
                new Holiday("May 1", "Labour Day",Holiday.Type.FEDERAL),
                new Holiday("August 15", "Independence Day",Holiday.Type.FEDERAL),
                new Holiday("October 2", "Gandhi Jayanti",Holiday.Type.FEDERAL),
                new Holiday("November 4", "Diwali",Holiday.Type.FESTIVAL),
                new Holiday("December 25", "Christmas",Holiday.Type.FESTIVAL));
        Holiday.Type[] holidayTypes=Holiday.Type.values();
        for(Holiday.Type type : holidayTypes){
            model.addAttribute(type.toString(),holidaysList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList()));
        }
        return "holidays.html";
    }*/

    private final HolidaysRepository holidaysRepository;

    @Autowired
    public HolidayController(HolidaysRepository holidaysRepository){
        this.holidaysRepository=holidaysRepository;
    }

    @RequestMapping(value = {"/holidays/{display}"}, method= RequestMethod.GET)
    public String displayHolidays(@PathVariable String display, Model model){
        if(null!=display && display.equals("all")) {
            model.addAttribute("festival", true);
            model.addAttribute("federal", true);
        }else if(null!=display && display.equals("federal")){
            model.addAttribute("festival",false);
            model.addAttribute("federal",true);
        }else if(null!=display && display.equals("festival")){
            model.addAttribute("festival",true);
            model.addAttribute("federal",false);
        }
        Iterable<Holiday> holidays= holidaysRepository.findAll();
        List<Holiday> holidaysList= StreamSupport.stream(holidays.spliterator(),false).toList();
        Holiday.Type[] holidayTypes=Holiday.Type.values();
        for(Holiday.Type type : holidayTypes){
            model.addAttribute(type.toString(),holidaysList.stream().filter(holiday -> holiday.getType().equals(type)).collect(toList()));
        }
        return "holidays.html";
    }
}
