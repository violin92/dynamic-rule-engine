package com.viosoft.dynamic_rule_engine.controller;

import com.viosoft.dynamic_rule_engine.dto.RuleDto;
import com.viosoft.dynamic_rule_engine.dto.RuleExecutionResultDto;
import com.viosoft.dynamic_rule_engine.service.RuleService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/rules")
@CrossOrigin(origins = "${cors.allowed.origins}")
public class RuleController {

    @Autowired
    RuleService ruleService;

    @GetMapping
    public List<RuleDto> getRules() {
        return ruleService.getRules();
    }

    @PostMapping
    public ResponseEntity<RuleDto> createRule(@RequestBody RuleDto ruleDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ruleService.createRule(ruleDto));
    }

    @GetMapping("/{id}")
    public RuleDto getRule(@PathVariable Long id){
        return ruleService.getRule(id);
    }

    @PutMapping("/{id}")
    public void updateRule(@PathVariable Long id, @RequestBody RuleDto ruleDto){
        ruleService.updateRule(ruleDto, id);
    }

    @PostMapping("/execute")
    public RuleExecutionResultDto executeRule(@RequestBody Map<String, String> parameters) {
        return ruleService.executeRule(parameters);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id){
        ruleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}
