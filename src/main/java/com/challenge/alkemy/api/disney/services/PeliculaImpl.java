package com.challenge.alkemy.api.disney.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.alkemy.api.disney.commons.ControllerUtils;
import com.challenge.alkemy.api.disney.models.entity.Pelicula;
import com.challenge.alkemy.api.disney.models.repository.PeliculaRepository;

@Service
public class PeliculaImpl implements PeliculaService {
	
	@Autowired
	private PeliculaRepository repository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Pelicula> findAll() {
		
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Pelicula> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Pelicula save(Pelicula pelicula) {
		
		return repository.save(pelicula);
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception {
		Optional<Pelicula> pelicula = repository.findById(id);
		if(!pelicula.isPresent()) {
    		throw new Exception("No existe la pelicula con Id: ");
		}
		repository.deleteRelation(id);
		repository.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Iterable<Pelicula> getPeliculasFilter(String name, Integer genre, String order) {

		CriteriaBuilder  criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Pelicula> criteriaQuery = criteriaBuilder.createQuery(Pelicula.class);      
	    Root<Pelicula> peliculaRoot = criteriaQuery.from(Pelicula.class);
	    criteriaQuery.select(peliculaRoot);

	    List<Predicate> predicates = new ArrayList<>();


	 // For like expression
	    Expression<String> path = peliculaRoot.get("titulo");
	    Expression<String> lowerName = criteriaBuilder.lower(path);
	    
	    if(genre!=null) predicates.add(criteriaBuilder.equal(peliculaRoot.get("genero"), genre));

	    if(name!=null && !name.isEmpty()) predicates.add(criteriaBuilder.like(lowerName, '%' + name.toLowerCase() + '%'));
	    
	    criteriaQuery.orderBy(order.compareTo(ControllerUtils.FILTER_ORDER_ASC)==0 ? 
	    							criteriaBuilder.asc(peliculaRoot.get( "fechaCreacion")) : 
	    							criteriaBuilder.desc(peliculaRoot.get("fechaCreacion")));

	    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
	    TypedQuery<Pelicula> typedQuery = entityManager.createQuery(criteriaQuery);
	    Iterable<Pelicula> peliculasList = typedQuery.getResultList();
	    
	    
		return peliculasList;
	}
}
