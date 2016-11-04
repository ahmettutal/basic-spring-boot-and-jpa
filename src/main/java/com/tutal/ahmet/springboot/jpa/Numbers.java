package com.tutal.ahmet.springboot.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tutal on 04.11.2016.
 */

@Entity
@Table(name = "NUMBERS")
public class Numbers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NUMBER", nullable = false)
    private int number;

    @Column(name = "INSERT_DATE", nullable = false)
    private String insertDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }
}
