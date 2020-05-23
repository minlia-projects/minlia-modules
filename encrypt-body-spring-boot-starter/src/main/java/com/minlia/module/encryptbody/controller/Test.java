package com.minlia.module.encryptbody.controller;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class Test {

    @Size(max = 1)
    private String name;

    private String value;

}