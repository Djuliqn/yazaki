package com.yazaki.yazaki.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DISH_COUNTER")
@Data
public class DishCounter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COUNTER")
    private Long counter;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ORDER_ID")
    @JsonBackReference
    private Order order;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "DISH_ID")
    @JsonManagedReference
    private Dish dish;
}