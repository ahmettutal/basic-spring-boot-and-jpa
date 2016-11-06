package com.tutal.ahmet.springboot.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tutal on 04.11.2016.
 */

@Entity
@Table(name = "NUMBERS")
public class Number implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NUMBER", nullable = false)
    private Integer number;

    @Column(name = "INSERT_DATE", nullable = false)
    private String insertDate;

    protected Number() {
    }

    public Number(Integer number, String insertDate) {
        this.number = number;
        this.insertDate = insertDate;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }
}
