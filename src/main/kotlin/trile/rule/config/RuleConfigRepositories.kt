package trile.rule.config

import org.springframework.data.jpa.repository.JpaRepository

interface RuleSetDefinitionRepository : JpaRepository<RuleSetDefinition, Long>
interface RuleDefinitionRepository : JpaRepository<RuleDefinition, Long>
interface ConditionDefinitionRepository : JpaRepository<ConditionDefinition, Long>
interface ActionDefinitionRepository : JpaRepository<ActionDefinition, Long>
