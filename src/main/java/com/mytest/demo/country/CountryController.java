package com.mytest.demo.country;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@RestController
public class CountryController {

    //Luodaan pelissä tarvittava lista ja pari apumuuttujaa.
    ArrayList<Country> countries = new ArrayList<>();
    int score = 0;
    int current = 0;

    @PostConstruct
    public void runThisToInit() {

        //Luodaan maa-olioita (sis. maan nimen ja pääkaupungin) ja lisätään ne listaan.
        Country finland = new Country("Finland","Helsinki");
        Country sweden = new Country("Sweden","Stockholm");
        Country greece = new Country("Greece","Athens");
        Country iceland = new Country("Iceland","Reykjavik");
        Country poland = new Country("Poland","Warsaw");
        Country romania = new Country("Romania","Bucharest");
        Country slovakia = new Country("Slovakia","Bratislava");
        Country monaco = new Country("Monaco","Monaco");
        Country latvia = new Country("Latvia","Riga");
        Country ireland = new Country("Ireland","Dublin");

        countries.add(finland);
        countries.add(sweden);
        countries.add(greece);
        countries.add(iceland);
        countries.add(poland);
        countries.add(romania);
        countries.add(slovakia);
        countries.add(monaco);
        countries.add(latvia);
        countries.add(ireland);
    }

    //"Alkusivu" joka selittää miten peli toimii. Myös initialisoi pelissä tarvittavan arraylistan.
    @GetMapping
    public String getInfo() {
        runThisToInit();
        return "In this game you try to guess the capitals of European countries. You do this by sending the name of the capital as a POST request to the /answer endpoint. Please begin by sending what you think the capital of Finland is to this endpoint (as raw text).";
    }

    //Palauttaa kaikki pelissä käytössä olevat maat ja niiden pääkaupungit. Ei käytetä pelissä (pääkaupunki on tarkoitus arvata!)
    @GetMapping("/countries")
    public List<Country> getCountries() {
        return countries;
    }

    //Hakee maan indexin perusteella. Ei myöskään käytetä pelissä.
    @GetMapping("/countries/{index}")
    public Country getCountryByIndex(@PathVariable int index) {
        return countries.get(index);
}

    //Varsinainen peli pyörii käytännössä tässä postmappauksessa. Peli kertoo menikö vastaus oikein vai väärin, laskee pisteitä ja kysyy seuraavan kysymyksen. Kun kaikki kysymykset on kysytty, peli kertoo montako pistettä pelaaja sai.
    @PostMapping("/answer")
    public String postAnswer(@RequestBody String capital) {
        if (capital.equals(countries.get(current).getCapital())) {
            score ++;
            current++;
            if (current == 10) {
                return "Game finished! You scored " + score + " points.";
            } else {
                return "Correct! Next country: " + countries.get(current).getName();
            }
        } else {
            current++;
            if (current == 10) {
                return "Game finished! You scored " + score + " points.";
            } else {
                return "Wrong! Next country: " + countries.get(current).getName();
            }
        }
    }
    
    //Tällä endpointilla voisi lisätä maita listaan. Ei käytetä pelissä.
    @PostMapping("addcountry")
    public Country firstPost(@RequestBody Country country) {
        countries.add(country);
        return country;
    }
}
