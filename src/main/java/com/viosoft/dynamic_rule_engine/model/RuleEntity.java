package com.viosoft.dynamic_rule_engine.model;

import jakarta.persistence.*;

@Entity(name = "rule")
public class RuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rule_seq_gen")
    @SequenceGenerator(name = "rule_seq_gen", sequenceName = "rule_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String condition;

    private String action;

    public RuleEntity() {
    }

    public RuleEntity(String name, String condition, String action) {
        this.name = name;
        this.condition = condition;
        this.action = action;
    }

    public Long getId() {
        return id;
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
