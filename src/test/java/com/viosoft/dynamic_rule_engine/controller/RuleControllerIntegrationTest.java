package com.viosoft.dynamic_rule_engine.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RuleControllerIntegrationTest {
    public static final long RULE_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    @Order(1)
    void createRule() throws Exception {
        String ruleJson = "{"
                + "\"name\": \"Name\","
                + "\"condition\": \"#number > 5 and #text.startsWith('Test')\","
                + "\"action\": \"'Input number is greater than 5 and input text starts with Test'\""
                + "}";

        mockMvc.perform(post("/api/rules") // Adjust the endpoint as needed
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleJson))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    @Order(2)
    void testGetAllRules() throws Exception {
        mockMvc.perform(get("/api/rules"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(RULE_ID)) // Expecting the first rule to have the same ID as created
                .andExpect(jsonPath("$[0].name").value("Name")) // Expecting the first rule to have the correct name
                // Add more assertions as needed for other attributes
                .andExpect(jsonPath("$[0].condition").value("#number > 5 and #text.startsWith('Test')"))
                .andExpect(jsonPath("$[0].action").value("'Input number is greater than 5 and input text starts with Test'"));
    }

    @Test
    @WithMockUser
    @Order(3)
    void testGetRuleById() throws Exception {
        mockMvc.perform(get("/api/rules/{id}", RULE_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(RULE_ID)) // Ensure the correct ID is returned
                .andExpect(jsonPath("$.name").value("Name")) // Ensure the correct name is returned
                .andExpect(jsonPath("$.condition").value("#number > 5 and #text.startsWith('Test')"))
                .andExpect(jsonPath("$.action").value("'Input number is greater than 5 and input text starts with Test'"));
    }

    @Test
    @WithMockUser
    @Order(4)
    void testExecuteRule() throws Exception {
        String inputJson = "{\"number\": 10, \"text\": \"Test\"}";

        mockMvc.perform(post("/api/rules/execute")
                        .contentType("application/json")
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"result\":\"Input number is greater than 5 and input text starts with Test\"}"));
    }

    @Test
    @WithMockUser
    @Order(5)
    void testUpdateRule() throws Exception {
        // JSON representation of the updated rule
        String updatedRuleJson = "{"
                + "\"name\": \"Updated Name\","
                + "\"condition\": \"#number <= 10\","
                + "\"action\": \"'Input number is less than or equal to 10'\""
                + "}";

        mockMvc.perform(put("/api/rules/{id}", RULE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRuleJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/rules/{id}", RULE_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(RULE_ID))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.condition").value("#number <= 10"))
                .andExpect(jsonPath("$.action").value("'Input number is less than or equal to 10'"));
    }

    @Test
    @WithMockUser
    @Order(6)
    void testDeleteRuleById() throws Exception {
        mockMvc.perform(delete("/api/rules/{id}", RULE_ID))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/rules/{id}", RULE_ID))
                .andExpect(status().isNotFound());
    }
}
