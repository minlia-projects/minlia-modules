package com.microsoft.crm.v1.domain;

import javax.persistence.*;

/**
 * @author Jarvis Song
 */
@Entity
@Table(name = "ds_booking")
public class Booking  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String serialNumber;
    private Integer amount;

    public Booking() {
    }

//    @PersistenceConstructor
//    public Booking(Long userId) {
//        this.userId = userId;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
