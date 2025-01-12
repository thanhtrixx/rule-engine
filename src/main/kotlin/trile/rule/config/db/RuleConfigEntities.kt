package trile.rule.config.db


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import trile.rule.model.RuleDefinition

@Entity
data class RuleSetDefinitionEntity(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0,

  var name: String,

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "parameters", columnDefinition = "json")
  var rules: List<RuleDefinition>
)
