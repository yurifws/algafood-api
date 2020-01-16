package com.algaworks.algafood.infraestructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositorioQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositorioQueries{
	
	@PersistenceContext
	private EntityManager entityManager;
	
//	public List<Restaurante> find(String nome,
//			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
//		var jpql = new StringBuilder();
//		var parametros = new HashMap<String, Object>();
//		
//		jpql.append("from Restaurante where 0 = 0 ");
//		if(StringUtils.hasLength(nome)) {
//			jpql.append("and nome like concat('%', :nome, '%') ");
//			parametros.put("nome", nome);
//		}
//		if(taxaFreteInicial != null) {
//			jpql.append("and taxaFrete >= :taxaInicial ");
//			parametros.put("taxaInicial", taxaFreteInicial);
//		}
//		if(taxaFreteFinal != null) {
//			jpql.append("and taxaFrete <= :taxaFinal ");
//			parametros.put("taxaFinal", taxaFreteFinal);
//		}
//		
//		TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);
//		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
//		return query.getResultList();
//	}
	
	public List<Restaurante> find(String nome,
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		
		Root<Restaurante> root = criteria.from(Restaurante.class); // from Restaurante
		
		Predicate predicateNome = builder.like( root.get("nome"), '%' + nome + '%');
		Predicate predicateTaxaInicial = builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
		Predicate predicateTaxaFinal = builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);
		criteria.where(predicateNome, predicateTaxaInicial, predicateTaxaFinal);
		
		TypedQuery<Restaurante> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}

}
