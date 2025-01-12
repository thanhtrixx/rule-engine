create table rule_set_definition_entity
(
  id         bigint auto_increment primary key,
  `use-case`       varchar(255) null,
  name       varchar(255) null,
  rules json         null
);

