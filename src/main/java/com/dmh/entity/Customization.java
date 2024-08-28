package com.dmh.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "customization")
public class Customization {
    @Id
    @GeneratedValue
    @Column
    private Integer id;
    private Integer productId;
    private String name;


}
