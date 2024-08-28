package com.dmh.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "score")
@Data
public class Score implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private Integer id;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "user_id")
    private Integer userId;

    private Double score;

    private String content;

    private Date time;
}
