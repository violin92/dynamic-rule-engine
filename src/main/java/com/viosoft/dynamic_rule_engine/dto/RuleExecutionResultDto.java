package com.viosoft.dynamic_rule_engine.dto;

public class RuleExecutionResultDto {
    private String result;

    public RuleExecutionResultDto(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
