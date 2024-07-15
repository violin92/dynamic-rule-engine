package com.viosoft.dynamic_rule_engine.service;

import com.viosoft.dynamic_rule_engine.Repository.RuleRepository;
import com.viosoft.dynamic_rule_engine.dto.RuleExecutionResultDto;
import com.viosoft.dynamic_rule_engine.exception.RuleExecutionException;
import com.viosoft.dynamic_rule_engine.model.RuleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class RuleServiceTest {

    @Mock
    private RuleRepository ruleRepository;

    @InjectMocks
    private RuleService ruleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create some sample rules
        RuleEntity rule1 = new RuleEntity("1", "#inputNumber > 5 and #inputText.startsWith('Test')", "'Input number is greater than 5 and input text starts with Test'");

        // Mock the repository to return the sample rules
        when(ruleRepository.findAll()).thenReturn(List.of(rule1));
    }

    @Test
    void testRuleWithValidParameters() throws RuleExecutionException {
        // Define input parameters
        Map<String, String> parameters = new HashMap<>();
        parameters.put("inputNumber", "10");
        parameters.put("inputText", "TestInput");

        // Execute rule and get result
        RuleExecutionResultDto result = ruleService.executeRule(parameters);

        // Assert the result
        assertEquals("Input number is greater than 5 and input text starts with Test", result.getResult());
    }

    @Test
    void testRuleWithInvalidParameters() {
        // Define input parameters
        Map<String, String> parameters = new HashMap<>();
        parameters.put("inputNumber", "3");
        parameters.put("inputText", "TestInput");

        // Execute rule and assert exception
        assertThrows(RuleExecutionException.class, () -> {
            ruleService.executeRule(parameters);
        });
    }

    @Test
    void testRuleWithMissingParameters() {
        // Define input parameters
        Map<String, String> parameters = new HashMap<>();
        parameters.put("inputNumber", "10");

        // Execute rule and assert exception
        assertThrows(RuleExecutionException.class, () -> {
            ruleService.executeRule(parameters);
        });
    }
}
