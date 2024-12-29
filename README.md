# Rule Engine
Demo project for Rule engine using Spring Boot and Kotlin

### Class Diagram for Rule Engine and Rule models
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

    class TransactionContext {
        + String type
        + Number amount
        + ...
        + Map~String, String~ additionalInfo
    }

    class Condition {
        + evaluate(context: TransactionContext, parameter: ParameterType): Boolean
        + convertParameter(parameters: Map~String, Any~): ParameterType
    }

    class Action {
        + execute(context: TransactionContext, parameter: ParameterType)
        + convertParameter(parameters: Map~String, Any~): ParameterType
    }

    RuleEngine --> RuleExecutor : manages
    RuleExecutor --> RuleSetWrapper : contains
    RuleSetWrapper --> ConditionWithParameter : has
    RuleSetWrapper --> ActionWithParameter : has
    RuleEngine --> RuleConfiguration : uses
    RuleConfiguration --> RuleSetDefinition : uses
    RuleSetDefinition --> RuleDefinition : uses
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
title: Flowchart for OperatorCondition Evaluation
---
flowchart TD
    Start([Start]) --> CheckNOT[Is NOT Operator Defined?]
    
    CheckNOT -->|Yes| EvaluateNOT[Evaluate NOT Operator]
    EvaluateNOT --> ReturnNOTResult[Return Inverted Result of NOT Operator]
    ReturnNOTResult --> End([End])
    
    CheckNOT -->|No| CheckAND[Is AND Operators Defined?]
    
    CheckAND -->|Yes| EvaluateAND[Evaluate All AND Operators]
    EvaluateAND --> CheckAllANDTrue{Are All AND Results True?}
    CheckAllANDTrue -->|Yes| ReturnANDTrue[Return TRUE]
    CheckAllANDTrue -->|No| ReturnANDFalse[Return FALSE]
    ReturnANDTrue --> End
    ReturnANDFalse --> End
    
    CheckAND -->|No| CheckOR[Is OR Operators Defined?]
    
    CheckOR -->|Yes| EvaluateOR[Evaluate All OR Operators]
    EvaluateOR --> CheckAnyORTrue{Is Any OR Result True?}
    CheckAnyORTrue -->|Yes| ReturnORTrue[Return TRUE]
    CheckAnyORTrue -->|No| ReturnORFalse[Return FALSE]
    ReturnORTrue --> End
    ReturnORFalse --> End
    
    CheckOR -->|No| ReturnDefaultFalse[Return FALSE]
    ReturnDefaultFalse --> End
```
