insert into tb_cozinha (id, nome) values (1, 'Tailandesa');
insert into tb_cozinha (id, nome) values (2, 'Indiana');
insert into tb_cozinha (id, nome) values (3, 'Chinesa');

insert into tb_estado (id, nome) values (1, 'Pernambuco');
insert into tb_estado (id, nome) values (2, 'São Paulo');
insert into tb_estado (id, nome) values (3, 'Acre');
insert into tb_estado (id, nome) values (4, 'Amapá');

insert into tb_cidade (id, nome, estado_id) values (1, 'Recife', 1);
insert into tb_cidade (id, nome, estado_id) values (2, 'São Paulo', 2);

insert into tb_restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values ('China in Box', 100.0, 1, UTC_TIMESTAMP, UTC_TIMESTAMP, 1, "50751-567", "Rua Itapemirim", "2209", "Chovena");
insert into tb_restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values ('Coowok', 50.0, 1, UTC_TIMESTAMP, UTC_TIMESTAMP, 1, "50751-567", "Rua Itapemirim", "2209", "Chovena");
insert into tb_restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values ('Matuto', 150.0, 2, UTC_TIMESTAMP, UTC_TIMESTAMP, 1, "50751-567", "Rua Itapemirim", "2209", "Chovena");
insert into tb_restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values ('Haiako', 0.0, 1, UTC_TIMESTAMP, UTC_TIMESTAMP, 1, "50751-567", "Rua Itapemirim", "2209", "Chovena");

insert into tb_forma_pagamento (id, descricao) values (1, 'Cartão de Credito');
insert into tb_forma_pagamento (id, descricao) values (2, 'Cartão de Debito');
insert into tb_forma_pagamento (id, descricao) values (3, 'Cheque');

insert into tb_permissao (id, nome, descricao) values (1, 'Consultar', 'Permite consulta');
insert into tb_permissao (id, nome, descricao) values (2, 'Incluir/Alterar', 'Permite inclusão e alteração');


insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);

insert into tb_produto (id, nome, descricao, preco, ativo, restaurante_id) values (1, 'Guarana', 'Guarana feito dos alpes', 7.0, true, 1);
insert into tb_produto (id, nome, descricao, preco, ativo, restaurante_id) values (2, 'Suco', 'Suco feito dos alpes', 5.0, true, 1);
insert into tb_produto (id, nome, descricao, preco, ativo, restaurante_id) values (3, 'Sanduiche', 'Sanduiche feito dos alpes', 10.0, true, 2);
