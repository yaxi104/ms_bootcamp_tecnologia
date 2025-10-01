package com.reactive.ms_technology.domain.model;

public class TechnologyCapacity {

    private Long id;
    private String name;

    public TechnologyCapacity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TechnologyCapacity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}