package com.yazaki.yazaki.domain.model;

import com.fasterxml.jackson.annotation.*;
import com.yazaki.yazaki.ui.converter.LocalDatePersistanceConverter;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE", unique = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Convert(converter = LocalDatePersistanceConverter.class)
    @NotNull(message = "daily.menu.date.not.null")
    private LocalDate date;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
    private List<DishCounter> dishCounters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull LocalDate date) {
        this.date = date;
    }

    public List<DishCounter> getDishCounters() {
        return dishCounters;
    }

    public void setDishCounters(List<DishCounter> dishCounters) {
        this.dishCounters = dishCounters;
    }
}