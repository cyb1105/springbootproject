package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password","ssn"})
//@JsonFilter("Userinfo")
public class User {

    private Integer id;

    @NotNull
    private String name;
    private Date joinDate;

    //@JsonIgnore
    @NotNull
    @Size(min=6, max=16)
    private String password;
    //@JsonIgnore
    private String ssn;

}
