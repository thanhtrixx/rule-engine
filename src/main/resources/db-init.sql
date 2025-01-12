create table rule_set_definition_entity
(
  id         bigint auto_increment
    primary key,
  name       varchar(255) null,
  parameters json         null
);

INSERT INTO `rule-engine-db`.rule_set_definition_entity (id, name, parameters) VALUES (1, 'Regular Card Purchases', '[{"name": "Earn points for regular card purchases with a minimum spend of 10,000 VND", "actions": [{"name": "", "type": "EARN_POINT_BY_RATE", "parameters": {"rate": "0.02"}}, {"name": "", "type": "EARN_POINT_BY_RATE", "parameters": {"rate": "0.05"}}], "conditions": [{"or": [], "and": [], "not": [], "name": "", "type": "EQUALS", "parameters": {"amount": "10000"}}]}]');
INSERT INTO `rule-engine-db`.rule_set_definition_entity (id, name, parameters) VALUES (2, 'Super Card Purchases', '[{"name": "Earn points for super card purchases of 10,000 or 10,001 VND", "actions": [{"name": "", "type": "EARN_POINT_BY_RATE", "parameters": {"rate": "0.1"}}], "conditions": [{"or": [{"or": [], "and": [], "not": [], "name": "", "type": "EQUALS", "parameters": {"amount": "10000"}}, {"or": [], "and": [], "not": [], "name": "", "type": "EQUALS", "parameters": {"amount": "10001"}}], "and": [], "not": [], "name": "", "type": "OPERATOR", "parameters": {}}]}]');
INSERT INTO `rule-engine-db`.rule_set_definition_entity (id, name, parameters) VALUES (3, 'Online Card Purchases', '[{"name": "Earn points for online purchases not equal to 1 VND", "actions": [{"name": "", "type": "EARN_POINT_BY_RATE", "parameters": {"rate": "0.04"}}], "conditions": [{"or": [], "and": [], "not": [{"or": [], "and": [], "not": [], "name": "", "type": "EQUALS", "parameters": {"amount": "1"}}], "name": "", "type": "OPERATOR", "parameters": {}}]}]');
