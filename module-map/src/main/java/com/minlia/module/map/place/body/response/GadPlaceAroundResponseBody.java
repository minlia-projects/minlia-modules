package com.minlia.module.map.place.body.response;

import java.util.List;

/**
 * Created by garen on 2018/4/25.
 */
public class GadPlaceAroundResponseBody {

    private Integer status;

    private Long count;

    private String info;

    private Integer infocode;

    private SuggestionBody suggestion;

    private List<PoiBody> pois;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getInfocode() {
        return infocode;
    }

    public void setInfocode(Integer infocode) {
        this.infocode = infocode;
    }

    public SuggestionBody getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(SuggestionBody suggestion) {
        this.suggestion = suggestion;
    }

    public List<PoiBody> getPois() {
        return pois;
    }

    public void setPois(List<PoiBody> pois) {
        this.pois = pois;
    }
}
