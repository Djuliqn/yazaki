package com.yazaki.yazaki.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Data
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonManagedReference
    private List<DishCounter> dishCounters;
}