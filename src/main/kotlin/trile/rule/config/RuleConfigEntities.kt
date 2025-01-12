package trile.rule.config


import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapKeyColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import trile.rule.action.ActionType
import trile.rule.condition.ConditionType

@Entity
data class RuleSetDefinition(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,

  val name: String,

  @OneToMany(mappedBy = "ruleSet", cascade = [CascadeType.ALL], orphanRemoval = true)
  val rules: List<RuleDefinition> = emptyList()
)

@Entity
data class RuleDefinition(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,

  val name: String?,

  @ManyToOne
  @JoinColumn(name = "rule_set_id")
  val ruleSet: RuleSetDefinition,

  @OneToMany(mappedBy = "rule", cascade = [CascadeType.ALL], orphanRemoval = true)
  val conditions: List<ConditionDefinition> = emptyList(),

  @OneToMany(mappedBy = "rule", cascade = [CascadeType.ALL], orphanRemoval = true)
  val actions: List<ActionDefinition> = emptyList()
)

@Entity
@Table(name = "condition_definitions")
data class ConditionDefinition(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,

  var name: String?,

  @Enumerated(EnumType.STRING)
  var type: ConditionType,

  @ElementCollection
  @CollectionTable(
    name = "condition_parameters",
    joinColumns = [JoinColumn(name = "condition_id")]
  )
  @MapKeyColumn(name = "parameter_key")
  @Column(name = "parameter_value")
  var parameters: Map<String, String> = emptyMap(),

  @ManyToOne
  @JoinColumn(name = "rule_id")
  var rule: RuleDefinition, // **Thêm mối quan hệ ngược ở đây để fix lỗi!**

  @OneToMany(cascade = [CascadeType.ALL])
  @JoinTable(
    name = "condition_or_conditions",
    joinColumns = [JoinColumn(name = "condition_id")],
    inverseJoinColumns = [JoinColumn(name = "or_condition_id")]
  )
  var or: List<ConditionDefinition> = emptyList(),

  @OneToMany(cascade = [CascadeType.ALL])
  @JoinTable(
    name = "condition_and_conditions",
    joinColumns = [JoinColumn(name = "condition_id")],
    inverseJoinColumns = [JoinColumn(name = "and_condition_id")]
  )
  var and: List<ConditionDefinition> = emptyList(),

  @OneToMany(cascade = [CascadeType.ALL])
  @JoinTable(
    name = "condition_not_conditions",
    joinColumns = [JoinColumn(name = "condition_id")],
    inverseJoinColumns = [JoinColumn(name = "not_condition_id")]
  )
  var not: List<ConditionDefinition> = emptyList()
)

@Entity
data class ActionDefinition(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,

  val name: String?,

  @Enumerated(EnumType.STRING)
  val type: ActionType,

  @ElementCollection
  @CollectionTable(
    name = "action_parameters",
    joinColumns = [JoinColumn(name = "action_id")]
  )
  @MapKeyColumn(name = "parameter_key")
  @Column(name = "parameter_value")
  val parameters: Map<String, String> = emptyMap(),

  @ManyToOne
  @JoinColumn(name = "rule_id")
  val rule: RuleDefinition
)
