package com.lambdaschool.javacountries.controllers;

import com.lambdaschool.javacountries.models.Country;
import com.lambdaschool.javacountries.repositories.CountryRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryRepository countryrepos;

    private List<Country> findCountries (List<Country> myList, CheckCountry tester){
        List<Country> tempList = new ArrayList<>();
        for (Country c : myList){
            if (tester.test(c)){
                tempList.add(c);
            }
        }
        return tempList;
    }

    //    http://localhost:2019/names/all
    @GetMapping(value= "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries(){
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    //    http://localhost:2019/names/start/u
    @GetMapping(value = "/names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> listALlByName(@PathVariable char letter){
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        List<Country> rtnList = findCountries(myList, e -> e.getName().charAt(0) == letter);

        for ( Country c : rtnList){
            System.out.println(c);
        }
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    //    http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = "application/json")
    public ResponseEntity<?> totalPopulation() {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining((myList::add));

        long total = 0;
        for (Country c : myList) {
            total = total + c.getPopulation();
        }
        String words = "The total population is " + total;
        System.out.println("The total population is " + total);

        return new ResponseEntity<>(words, HttpStatus.OK);
    }

    //    http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = "application/json")
    public ResponseEntity<?> minPopulation() {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        int minIndex = 0;
        for( int index = 0; index < myList.size(); index++) {
            if(myList.get(index).getPopulation() < myList.get(minIndex).getPopulation()) {
                minIndex = index;
            }
        }
        return new ResponseEntity<>(myList.get(minIndex), HttpStatus.OK);
    }


    //    http://localhost:2019/population/max
    @GetMapping(value = "/population/max", produces = "application/json")
    public ResponseEntity<?> maxPopulation() {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        int maxIndex = 0;

        for( int index = 0; index < myList.size(); index++) {
            if(myList.get(index).getPopulation() > myList.get(maxIndex).getPopulation()) {
                maxIndex = index;
            }
        }
        return new ResponseEntity<>(myList.get(maxIndex), HttpStatus.OK);
    }

}
