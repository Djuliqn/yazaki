package com.yazaki.yazaki.domain.scheduled.report;

public class ReportStatisticMapper {

    private String name;
    private Long counter;

    public ReportStatisticMapper(String name, Long counter) {
        this.name = name;
        this.counter = counter;
    }

    public String getName() {
        return name;
    }

    public Long getCounter() {
        return counter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }
}