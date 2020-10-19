package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository
		extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositorioQueries, JpaSpecificationExecutor<Restaurante> {

	@Query("from Restaurante r join fetch r.cozinha join fetch r.endereco.cidade c join fetch c.estado")
	public List<Restaurante> findAll();
	
	public List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

//	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	public List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);
//	public List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);

	public Optional<Restaurante> findFirstByNomeContaining(String nome);

	public List<Restaurante> findTop2ByNomeContaining(String nome);

	public int countByCozinhaId(Long cozinhaId);
	
	public boolean existsResponsavel(Long restauranteId, Long usuarioId);

}
