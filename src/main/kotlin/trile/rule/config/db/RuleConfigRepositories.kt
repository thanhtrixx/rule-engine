package trile.rule.config.db

import org.springframework.data.jpa.repository.JpaRepository

interface RuleSetDefinitionRepository : JpaRepository<RuleSetDefinitionEntity, Long>
