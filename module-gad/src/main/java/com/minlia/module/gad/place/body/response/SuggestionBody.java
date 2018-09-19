package com.minlia.module.gad.place.body.response;

import java.util.List;

/**
 * Created by garen on 2018/4/25.
 */
public class SuggestionBody {

    private List<String> keywords;

    private List<String> cities;

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }
}

