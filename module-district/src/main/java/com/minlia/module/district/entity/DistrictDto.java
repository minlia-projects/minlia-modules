package com.minlia.module.district.entity;

import lombok.Getter;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 区域
 * </p>
 *
 * @author garen
 * @since 2021-04-06
 */
@Getter
@XmlRootElement(name = "Location")
public class DistrictDto {

    @XmlElement(name = "CountryRegion")
    private List<Country> countries;

    @Getter
    @XmlRootElement(name = "bbbb")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Country {
        @XmlAttribute(name = "Name")
        private String name;
        @XmlAttribute(name = "Code")
        private String code;
        @XmlElement(name = "State")
        private List<State> states;

        @Getter
        @XmlRootElement(name = "ccc")
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class State {
            @XmlAttribute(name = "Name")
            private String name;
            @XmlAttribute(name = "Code")
            private String code;
            @XmlElement(name = "City")
            private List<City> cities;

            @Getter
            @XmlRootElement(name = "ddd")
            @XmlAccessorType(XmlAccessType.FIELD)
            public static class City {
                @XmlAttribute(name = "Name")
                private String name;
                @XmlAttribute(name = "Code")
                private String code;
            }
        }
    }

}