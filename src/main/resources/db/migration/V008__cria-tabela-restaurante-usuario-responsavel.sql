create table tb_restaurante_usuario_responsavel (
	restaurante_id bigint not null,
	usuario_id bigint not null,
	
	primary key (restaurante_id, usuario_id)
) engine=InnoDB default charset=utf8;

alter table tb_restaurante_usuario_responsavel add constraint fk_rest_usuario_resp_usuario
foreign key (usuario_id) references tb_usuario (id);

alter table tb_restaurante_usuario_responsavel add constraint fk_rest_usuario_resp_restaurante
foreign key (restaurante_id) references tb_restaurante (id);
