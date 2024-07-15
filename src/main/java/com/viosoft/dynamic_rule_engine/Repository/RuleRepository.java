package com.viosoft.dynamic_rule_engine.Repository;

import com.viosoft.dynamic_rule_engine.model.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<RuleEntity, Long> {
}
