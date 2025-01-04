# Rule Engine

## Introduction

The Rule Engine project was developed to address the growing need for flexible and scalable promotion and loyalty configurations in our system. Currently, our promotion logic is hardcoded within the application, making it difficult to introduce new rules without significant development effort. This rigid structure leads to prolonged release cycles and limits the ability to respond quickly to market demands.

To solve these challenges, the Rule Engine provides a configurable and extensible framework where:
- **Flexible Configuration:** Rules can be defined using YAML or JSON without requiring code changes.
- **Extensible:** New conditions and actions can be easily added to the system.
- **User-Friendly:** Designed to be readable and understandable by both technical and non-technical users.
- **Scalable:** Capable of handling multiple use cases with minimal performance overhead.

## How It Works

The Rule Engine allows you to define rules using a declarative approach where conditions and actions are separated. A rule consists of:

- **Condition:** Specifies when the rule should be applied.
- **Action:** Defines the outcomes when the conditions are met.

### Example Use Case
**Scenario:**
- If a customer makes a credit card transaction above 10,000 VND, they will earn 2% reward points.

**YAML Configuration:**
```yaml
rules:
  payment-use-cases:
    regular-card:
      name: Regular Card Purchases
      rules:
        - name: Earn points for regular card purchases over 10k
          conditions:
            - type: EQUALS
              parameters:
                amount: 10000
          actions:
            - type: EARN_POINT_BY_RATE
              parameters:
                rate: 0.02
```

## Getting Started

### Defining a Rule
1. **Identify the Use Case:** Define the business goal you want to achieve.
2. **Define Conditions:** Specify when the rule should apply.
3. **Define Actions:** Specify what actions should be taken when conditions pass.

### Example Steps:
- **Goal:** Provide cashback for amount over 20 million VND.
- **Condition:** If `amount > 20,000,000 VND`.
- **Action:** Apply `2% cashback`.

### Using the Rule Engine
1. **Setup:** Integrate the Rule Engine library into your Spring Boot project.
2. **Load Configuration:** Load rules from a YAML or JSON file.
3. **Execute Rules:** Use the `RuleEngine` class to execute rules against a transaction context.

```kotlin
val ruleEngine = RuleEngine(ruleConfiguration)
ruleEngine.executeRules(transactionContext)
```

## Class Diagram for Rule Engine and Rule models
```mermaid
---
title: Class Diagram for Rule Engine and Rule models
---
classDiagram
    class RuleEngine {
        - Map~String, Condition~ conditionMap
        - Map~String, Action~ actionMap
        - Map~String, RuleExecutor~ executorByType
        + executeRules(context: TransactionContext)
    }

    class RuleContext {
        + buildConditionMap(context: TransactionContext): - Map~String, Condition~
        + buildActionMap(context: TransactionContext): - Map~String, Action~
    }

    class RuleExecutor {
        - String name
        - List~RuleSetWrapper~ rules
        + execute(context: TransactionContext)
    }

    class RuleSetWrapper {
        - List~ConditionWithParameter~ConditionType~~ conditions
        - List~ActionWithParameter~ActionType~~ actions
    }

    class ConditionWithParameter {
        - Condition~ParameterType~ condition
        - ParameterType parameter
    }

    class ActionWithParameter {
        - Action~ParameterType~ action
        - ParameterType parameter
    }

    class RuleConfiguration {
        + Map~String, RuleSetDefinition~ paymentUseCases
    }

    class RuleSetDefinition {
        + String name
        + List~RuleDefinition~ rules
    }

    class RuleDefinition {
        + String name
        + List~ConditionDefinition~ conditions
        + List~ActionDefinition~ actions
    }

    class ConditionDefinition {
        + String name
        + ConditionType type
        + Map~String, String~ parameters
        + List~ConditionDefinition~ or
        + List~ConditionDefinition~ and
        + List~ConditionDefinition~ not
    }

    class ActionDefinition {
        + String name
        + ActionType type
        + Map~String, String~ parameters
    }

    class TransactionContext {
        + String type
        + Number amount
        + ...
        + Map~String, String~ additionalInfo
    }

    class Condition {
        + evaluate(context: TransactionContext, parameter: ParameterType): Boolean
    }

    class Action {
        + execute(context: TransactionContext, parameter: ParameterType)
    }

    RuleEngine --> RuleExecutor : manages
    RuleExecutor --> RuleSetWrapper : contains
    RuleSetWrapper --> ConditionWithParameter : has
    RuleSetWrapper --> ActionWithParameter : has
    RuleEngine --> RuleConfiguration : uses
    RuleContext --> RuleEngine : inject
    RuleConfiguration --> RuleSetDefinition : contains
    RuleSetDefinition --> RuleDefinition : contains
    RuleDefinition --> ConditionDefinition : has
    RuleDefinition --> ActionDefinition : has
    ConditionDefinition --> ConditionDefinition : links
    ConditionWithParameter --> Condition : references
    ActionWithParameter --> Action : references
    RuleEngine --> TransactionContext : operates on
```

### Flowchart for RuleEngine Execution

```mermaid
---
title: Flowchart for RuleEngine Execution
---
flowchart TD
    Start([Start]) --> GetTransaction[Receive TransactionContext]
    GetTransaction --> FindExecutor[Find RuleExecutor for TransactionContext.type]
    FindExecutor -->|Executor Found| CallExecutor["Call RuleExecutor.execute()"]
    FindExecutor -->|No Executor Found| LogIgnored[Log: No Executor Found]
    LogIgnored --> End([End])

    CallExecutor --> EvaluateRules[Iterate through all Rules]
    EvaluateRules --> CheckConditions[Evaluate Conditions for Rule]

    CheckConditions -->|All Conditions Pass| ExecuteActions[Execute Actions for Rule]
    CheckConditions -->|Some Conditions Fail| IgnoreRule[Skip Current Rule]

    ExecuteActions --> NextRule[Move to Next Rule]
    IgnoreRule --> NextRule
    NextRule -->|More Rules| EvaluateRules
    NextRule -->|No More Rules| End
```

### Flowchart for OperatorCondition Evaluation: support NOT, AND and OR operators

```mermaid
---
---
title: Flowchart for OperatorCondition Evaluation (Updated)
---
flowchart TD
    Start([Start]) --> CheckNOTList[Are NOT Operators Defined?]
    
    CheckNOTList -->|Yes| EvaluateNOTList[Evaluate All NOT Operators]
    EvaluateNOTList --> AggregateNOTResults{Aggregate Results of NOT Operators}
    AggregateNOTResults -->|Inverted Results| ReturnNOTResult[Return Aggregated NOT Results]
    ReturnNOTResult --> End([End])
    
    CheckNOTList -->|No| CheckAND[Are AND Operators Defined?]
    
    CheckAND -->|Yes| EvaluateAND[Evaluate All AND Operators]
    EvaluateAND --> CheckAllANDTrue{Are All AND Results True?}
    CheckAllANDTrue -->|Yes| ReturnANDTrue[Return TRUE]
    CheckAllANDTrue -->|No| ReturnANDFalse[Return FALSE]
    ReturnANDTrue --> End
    ReturnANDFalse --> End
    
    CheckAND -->|No| CheckOR[Are OR Operators Defined?]
    
    CheckOR -->|Yes| EvaluateOR[Evaluate All OR Operators]
    EvaluateOR --> CheckAnyORTrue{Is Any OR Result True?}
    CheckAnyORTrue -->|Yes| ReturnORTrue[Return TRUE]
    CheckAnyORTrue -->|No| ReturnORFalse[Return FALSE]
    ReturnORTrue --> End
    ReturnORFalse --> End
    
    CheckOR -->|No| ReturnDefaultFalse[Return FALSE]
    ReturnDefaultFalse --> End
```

This Rule Engine empowers both developers and business teams to manage and extend promotional logic with minimal technical intervention, improving system flexibility and reducing time-to-market for new features.