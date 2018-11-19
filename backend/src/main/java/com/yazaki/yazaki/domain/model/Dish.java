package com.yazaki.yazaki.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "DISHES")
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", unique = true)
    @NotNull(message = "Ястието трябва да има име.")
    @Size(min = 3, max = 30, message = "Името на ястието трябва да е между 3 и 30 символа.")
    @Audited
    private String name;

    @Column(name = "DESCRIPTION")
    @NotNull(message = "Описанието на ястието не може да е празно.")
    @Size(min = 3, max = 200, message = "Описанието на ястието трябва да е между 3 и 200 символа.")
    @Audited
    private String description;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "dish")
    @NotAudited
    private List<DishCounter> dishCounters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public List<DishCounter> getDishCounters() {
        return dishCounters;
    }

    public void setDishCounters(List<DishCounter> dishCounters) {
        this.dishCounters = dishCounters;
    }

}
