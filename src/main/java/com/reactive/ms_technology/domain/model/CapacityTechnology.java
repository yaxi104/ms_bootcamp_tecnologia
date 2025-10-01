package com.reactive.ms_technology.domain.model;

public class CapacityTechnology {

    private Long id;
    private Long technologyId;
    private Long capacityId;

    public CapacityTechnology(Long id, Long technologyId, Long capacityId) {
        this.id = id;
        this.technologyId = technologyId;
        this.capacityId = capacityId;
    }

    public CapacityTechnology() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Long technologyId) {
        this.technologyId = technologyId;
    }

    public Long getCapacityId() {
        return capacityId;
    }

    public void setCapacityId(Long capacityId) {
        this.capacityId = capacityId;
    }
}
