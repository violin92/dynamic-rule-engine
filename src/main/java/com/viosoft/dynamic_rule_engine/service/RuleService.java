package com.viosoft.dynamic_rule_engine.service;

import com.viosoft.dynamic_rule_engine.Repository.RuleRepository;
import com.viosoft.dynamic_rule_engine.dto.RuleDto;
import com.viosoft.dynamic_rule_engine.dto.RuleExecutionResultDto;
import com.viosoft.dynamic_rule_engine.exception.RuleExecutionException;
import com.viosoft.dynamic_rule_engine.exception.RuleNotFoundException;
import com.viosoft.dynamic_rule_engine.mapper.RuleMapper;
import com.viosoft.dynamic_rule_engine.model.RuleEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RuleService {

    @Autowired
    RuleMapper ruleMapper;

    @Autowired
    RuleRepository ruleRepository;

    @Autowired
    Validator validator;

    public List<RuleDto> getRules() {
        List<RuleEntity> ruleEntities = ruleRepository.findAll();
        return ruleMapper.toDtoList(ruleEntities);
    }

    public RuleDto createRule(@Valid RuleDto ruleDto) {
        validateRuleDto(ruleDto);
        var ruleEntity = ruleMapper.toEntity(ruleDto);
        var result = ruleRepository.save(ruleEntity);
        return ruleMapper.toDto(result);
    }

    public RuleDto getRule(Long id) {
        var ruleEntity = ruleRepository.findById(id)
                .orElseThrow(() -> new RuleNotFoundException(id.toString()));
        return ruleMapper.toDto(ruleEntity);
    }

    public void updateRule(@Valid RuleDto ruleDto, Long id) {
        var ruleEntity = ruleRepository.findById(id)
                .orElseThrow(() -> new RuleNotFoundException(ruleDto.getId().toString()));

        validateRuleDto(ruleDto);

        ruleEntity.setName(ruleDto.getName());
        ruleEntity.setCondition(ruleDto.getCondition());
        ruleEntity.setAction(ruleDto.getAction());

        ruleRepository.save(ruleEntity);
    }

    public RuleExecutionResultDto executeRule(Map<String, String> parameters) {
        var parsedParameters = getParsedParameters(parameters);

        ExpressionParser parser = new SpelExpressionParser();
        for (RuleEntity rule : ruleRepository.findAll()) {
            try {
                StandardEvaluationContext context = new StandardEvaluationContext();
                context.setVariables(parsedParameters);

                Boolean conditionMet = parser.parseExpression(rule.getCondition()).getValue(context, Boolean.class);

                if (conditionMet != null && conditionMet) {
                    return new RuleExecutionResultDto(parser.parseExpression(rule.getAction()).getValue(context, String.class));
                }
            } catch (SpelEvaluationException e) {
                //The required and passed parameters do not match; skip to next rule
                //throw new RuleExecutionException("Error occurred during the execution: " + e.getMessage());
            }
        }

        throw new RuleExecutionException("No rule was found for the given parameters, which meets the condition.");
    }

    public void deleteRule(Long id) {
        if (ruleRepository.existsById(id)) {
            ruleRepository.deleteById(id);
        } else {
            throw new RuleNotFoundException(id.toString());
        }
    }

    private HashMap<String, Object> getParsedParameters(Map<String, String> parameters) {
        var parsedParameters = new HashMap<String, Object>();

        for (var entry : parameters.entrySet()) {
            parsedParameters.put(entry.getKey(), parseValue(entry.getValue()));
        }
        return parsedParameters;
    }

    private void validateRuleDto(RuleDto ruleDto) {
        Set<ConstraintViolation<RuleDto>> violations = validator.validate(ruleDto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    private Object parseValue(String value) {
        // Trim the value to remove any leading or trailing whitespace
        value = value.trim();

        // Check if it's a boolean value
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.parseBoolean(value);
        }

        // Check if it's an integer value
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // Not an integer, continue to check other types
        }

        // Check if it's a long value
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            // Not a long, continue to check other types
        }

        // Check if it's a double value
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            // Not a double, return as string
            return value;
        }
    }
}
