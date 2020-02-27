package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>{
	
	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha join fetch r.endereco.cidade c join fetch c.estado")
	List<Pedido> findAll();

}
