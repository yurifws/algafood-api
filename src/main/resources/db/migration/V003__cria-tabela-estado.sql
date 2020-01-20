create table tb_estado (
	ID bigint not null auto_increment,
    nome varchar(60) not null,
    primary key(id)
) engine=InnoDB default charset=utf8;

insert into tb_estado (nome) 
	select distinct nome_estado from tb_cidade;

alter table tb_cidade 
	add column estado_id bigint not null;

update tb_cidade c set c.estado_id = 
	(select e.id from tb_estado e where e.nome = c.nome_estado);
    
alter table tb_cidade add constraint fk_cidade_estado
	foreign key (estado_id) references tb_estado (id);
    
alter table tb_cidade drop column nome_estado;

alter table tb_cidade change nome_cidade nome varchar(80) not null;