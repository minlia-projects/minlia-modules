package com.minlia.module.map.place.body.response;

import java.util.List;

/**
 * Created by garen on 2018/4/25.
 */
public class PoiBody {

//    id: "BV10000199",
//    name: "三元桥(地铁站)",
//    type: "交通设施服务;地铁站;地铁站",
//    typecode: "150500",
//    biz_type: [ ],
//    address: "10号线;机场线;(在建)12号线",
//    location: "116.456997,39.961508",
//    tel: [ ],
//    distance: "101",
//    biz_ext: {
//        rating: [ ],
//        cost: [ ]
//    },
//    pname: "北京市",
//    cityname: "北京市",
//    adname: "朝阳区",
//    importance: [ ],
//    shopid: [ ],
//    shopinfo: "2",
//    poiweight: [ ]

    private String id;

    private String name;

    private String type;

    private Integer typecode;

    private List<String> biz_type;

    private String address;

    private String location;

    private List<String> tel;

    private String distance;

    private String pname;

    private String cityname;

    private String adname;

    private String shopinfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTypecode() {
        return typecode;
    }

    public void setTypecode(Integer typecode) {
        this.typecode = typecode;
    }

    public List<String> getBiz_type() {
        return biz_type;
    }

    public void setBiz_type(List<String> biz_type) {
        this.biz_type = biz_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getTel() {
        return tel;
    }

    public void setTel(List<String> tel) {
        this.tel = tel;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public String getShopinfo() {
        return shopinfo;
    }

    public void setShopinfo(String shopinfo) {
        this.shopinfo = shopinfo;
    }
}
