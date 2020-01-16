package com.algaworks.algafood.infraestructure.repository;

import static com.algaworks.algafood.infraestructure.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infraestructure.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositorioQueries;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositorioQueries{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired 
	@Lazy
	private RestauranteRepository restauranteRepository;

	/**
	 * Com JPQL
	 */
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
	/**
	 * Com Criteria API
	 */
	public List<Restaurante> find(String nome,
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		
		Root<Restaurante> root = criteria.from(Restaurante.class); // from Restaurante
		
		var predicates = new ArrayList<Predicate>();
		if(StringUtils.hasText(nome)) {
			predicates.add(builder.like( root.get("nome"), '%' + nome + '%'));
		}
		if(taxaFreteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}
		if(taxaFreteFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
			return restauranteRepository.findAll(comFreteGratis()
					.and(comNomeSemelhante(nome)));
	}

}
