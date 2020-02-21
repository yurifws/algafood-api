alter table tb_restaurante add aberto boolean not null;
update tb_restaurante set aberto = false;