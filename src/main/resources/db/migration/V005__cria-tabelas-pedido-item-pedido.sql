create table tb_pedido (
	id bigint not null auto_increment,
	subtotal decimal(10,2) not null,
	taxa_frete decimal(10,2) not null,
	valor_total decimal(10,2) not null,
	data_criacao datetime not null,
	data_confirmacao datetime,
    data_cancelamento datetime,
    data_entrega datetime,
	
	endereco_cidade_id bigint,
	endereco_cep varchar(9),
	endereco_logradouro varchar(100),
	endereco_numero varchar(20),
	endereco_complemento varchar(60),
	endereco_bairro varchar(60),
    
    status varchar(10),
    
    forma_pagamento_id bigint not null,
    restaurante_id bigint not null,
    cliente_id bigint not null, 
	
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_item_pedido (
	id bigint not null auto_increment,
	quantidade integer not null,
	preco_unitario decimal(10,2) not null,
	preco_total decimal(10,2) not null,
	observacao varchar(80),
    
    produto_id bigint not null,
    pedido_id bigint not null,
	
	primary key (id)
) engine=InnoDB default charset=utf8;

alter table tb_pedido add constraint fk_pedido_forma_pagamento
foreign key (forma_pagamento_id) references tb_forma_pagamento (id);

alter table tb_pedido add constraint fk_pedido_restaurante
foreign key (restaurante_id) references tb_restaurante (id);

alter table tb_pedido add constraint fk_pedido_cliente
foreign key (cliente_id) references tb_usuario (id);

alter table tb_item_pedido add constraint fk_item_pedido_produto
foreign key (produto_id) references tb_produto (id);

alter table tb_item_pedido add constraint fk_item_pedido_pedido
foreign key (pedido_id) references tb_pedido (id);

alter table  tb_item_pedido add constraint id unique uk_item_pedido_produto (pedido_id, produto_id);