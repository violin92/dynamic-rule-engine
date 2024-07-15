package com.viosoft.dynamic_rule_engine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RuleDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, message = "Name must be at least 3 characters")
    private String name;

    @NotBlank(message = "Condition cannot be blank")
    @Size(min = 5, message = "Condition must be at least 5 characters")
    private String condition;

    @NotBlank(message = "Action cannot be blank")
    @Size(min = 5, message = "Action must be at least 5 characters")
    private String action;

    public RuleDto() {
    }

    public RuleDto(Long id, String name, String condition, String action) {
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.action = action;
    }

    public RuleDto(String name, String condition, String action) {
        this.name = name;
        this.condition = condition;
        this.action = action;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
