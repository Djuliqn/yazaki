package com.yazaki.yazaki.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "DISHES")
@Data
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", unique = true)
    @NotNull(message = "Ястието трябва да има име.")
    @Size(min = 3, max = 30, message = "Името на ястието трябва да е между 3 и 30 символа.")
    private String name;

    @Column(name = "DESCRIPTION")
    @NotNull(message = "Описанието на ястието не може да е празно.")
    @Size(min = 3, max = 300, message = "Описанието на ястието трябва да е между 3 и 30 символа.")
    private String description;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "dish")
    //@JsonBackReference
    private List<DishCounter> dishCounters;

}
