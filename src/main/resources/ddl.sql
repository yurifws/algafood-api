create table grupo_permissao_id (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB
create table restaurante_forma_pagamento (restaurante_id bigint not null, forma_pagamento_id bigint not null) engine=InnoDB
create table tb_cidade (id bigint not null auto_increment, nome varchar(255) not null, estado_id bigint not null, primary key (id)) engine=InnoDB
create table tb_cozinha (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table tb_estado (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table tb_forma_pagamento (id bigint not null auto_increment, descricao varchar(255) not null, primary key (id)) engine=InnoDB
create table tb_grupo (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table tb_permissao (id bigint not null auto_increment, descricao varchar(255) not null, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table tb_produto (id bigint not null auto_increment, ativo bit not null, descricao varchar(255) not null, nome varchar(255) not null, preco decimal(19,2) not null, restaurante_id bigint not null, primary key (id)) engine=InnoDB
create table tb_restaurante (id bigint not null auto_increment, data_atualizacao datetime not null, data_cadastro datetime not null, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_logradouro varchar(255), endereco_numero varchar(255), nome varchar(255) not null, taxa_frete decimal(19,2) not null, cozinha_id bigint not null, endereco_cidade_id bigint, primary key (id)) engine=InnoDB
create table tb_usuario (id bigint not null auto_increment, data_cadastro datetime not null, email varchar(255) not null, nome varchar(255) not null, senha varchar(255) not null, primary key (id)) engine=InnoDB
create table usuario_grupo_id (usuario_id bigint not null, grupo_id bigint not null) engine=InnoDB
alter table grupo_permissao_id add constraint FKoci73fgbcrj23768d1vs1vnkp foreign key (permissao_id) references tb_permissao (id)
alter table grupo_permissao_id add constraint FK6ht15sccns33t2n6l5wcksmvy foreign key (grupo_id) references tb_grupo (id)
alter table restaurante_forma_pagamento add constraint FKvmcyfbxboylw9gcoxooanja8 foreign key (forma_pagamento_id) references tb_forma_pagamento (id)
alter table restaurante_forma_pagamento add constraint FKi24qvgjh60batfb1qm9tty4yq foreign key (restaurante_id) references tb_restaurante (id)
alter table tb_cidade add constraint FKlxge3ne91xrep1oe4cvrjldmm foreign key (estado_id) references tb_estado (id)
alter table tb_produto add constraint FK1fh03prrjxetvrl6d4ut5r1r0 foreign key (restaurante_id) references tb_restaurante (id)
alter table tb_restaurante add constraint FKpsus3qnrv5sxgm95m8epaievn foreign key (cozinha_id) references tb_cozinha (id)
alter table tb_restaurante add constraint FKp5f7gcswjq064qro5pfjoj49r foreign key (endereco_cidade_id) references tb_cidade (id)
alter table usuario_grupo_id add constraint FKo5hgih06tnvqb7w6cn3b1w15u foreign key (grupo_id) references tb_grupo (id)
alter table usuario_grupo_id add constraint FKtbvmfrnad93x4lp77a0te8td6 foreign key (usuario_id) references tb_usuario (id)
INSERT INTO TB_COZINHA (ID, NOME) VALUES (1, 'Tailandesa')
INSERT INTO TB_COZINHA (ID, NOME) VALUES (2, 'Indiana')
INSERT INTO TB_COZINHA (ID, NOME) VALUES (3, 'Chinesa')
INSERT INTO TB_ESTADO (ID, NOME) VALUES (1, 'Pernambuco')
INSERT INTO TB_ESTADO (ID, NOME) VALUES (2, 'São Paulo')
INSERT INTO TB_ESTADO (ID, NOME) VALUES (3, 'Acre')
INSERT INTO TB_ESTADO (ID, NOME) VALUES (4, 'Amapá')
INSERT INTO TB_CIDADE (ID, NOME, ESTADO_ID) VALUES (1, 'Recife', 1)
INSERT INTO TB_CIDADE (ID, NOME, ESTADO_ID) VALUES (2, 'São Paulo', 2)
INSERT INTO TB_RESTAURANTE (NOME, TAXA_FRETE, COZINHA_ID, DATA_CADASTRO, DATA_ATUALIZACAO, ENDERECO_CIDADE_ID, ENDERECO_CEP, ENDERECO_LOGRADOURO, ENDERECO_NUMERO, ENDERECO_BAIRRO) VALUES ('China in Box', 100.0, 1, UTC_TIMESTAMP, UTC_TIMESTAMP, 1, "50751-567", "Rua Itapemirim", "2209", "Chovena")
INSERT INTO TB_RESTAURANTE (NOME, TAXA_FRETE, COZINHA_ID, DATA_CADASTRO, DATA_ATUALIZACAO) VALUES ('Coowok', 50.0, 1, UTC_TIMESTAMP, UTC_TIMESTAMP)
INSERT INTO TB_RESTAURANTE (NOME, TAXA_FRETE, COZINHA_ID, DATA_CADASTRO, DATA_ATUALIZACAO) VALUES ('Matuto', 150.0, 2, UTC_TIMESTAMP, UTC_TIMESTAMP)
INSERT INTO TB_RESTAURANTE (NOME, TAXA_FRETE, COZINHA_ID, DATA_CADASTRO, DATA_ATUALIZACAO) VALUES ('Haiako', 0.0, 1, UTC_TIMESTAMP, UTC_TIMESTAMP)
INSERT INTO TB_FORMA_PAGAMENTO (ID, DESCRICAO) VALUES (1, 'Cartão de Credito')
INSERT INTO TB_FORMA_PAGAMENTO (ID, DESCRICAO) VALUES (2, 'Cartão de Debito')
INSERT INTO TB_FORMA_PAGAMENTO (ID, DESCRICAO) VALUES (3, 'Cheque')
INSERT INTO TB_PERMISSAO (ID, NOME, DESCRICAO) VALUES (1, 'Consultar', 'Permite consulta')
INSERT INTO TB_PERMISSAO (ID, NOME, DESCRICAO) VALUES (2, 'Incluir/Alterar', 'Permite inclusão e alteração')
INSERT INTO RESTAURANTE_FORMA_PAGAMENTO (RESTAURANTE_ID, FORMA_PAGAMENTO_ID) VALUES (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3)
INSERT INTO TB_PRODUTO (ID, NOME, DESCRICAO, PRECO, ATIVO, RESTAURANTE_ID) VALUES (1, 'Guarana', 'Guarana feito dos alpes', 7.0, true, 1)
INSERT INTO TB_PRODUTO (ID, NOME, DESCRICAO, PRECO, ATIVO, RESTAURANTE_ID) VALUES (2, 'Suco', 'Suco feito dos alpes', 5.0, true, 1)
INSERT INTO TB_PRODUTO (ID, NOME, DESCRICAO, PRECO, ATIVO, RESTAURANTE_ID) VALUES (3, 'Sanduiche', 'Sanduiche feito dos alpes', 10.0, true, 2)
