package com.viosoft.dynamic_rule_engine.exception;

public class RuleNotFoundException extends RuntimeException {
    public RuleNotFoundException(String ruleId) {
        super("Rule with id " + ruleId + " not found!");
    }
}
