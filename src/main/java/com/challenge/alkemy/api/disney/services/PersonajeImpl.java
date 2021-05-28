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

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.alkemy.api.disney.models.entity.Personaje;
import com.challenge.alkemy.api.disney.models.repository.PersonajeRepository;

import ch.qos.logback.classic.Logger;

@Service
public class PersonajeImpl implements PersonajeService{


	@Autowired
	private PersonajeRepository repository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional(readOnly = true)
	@Override
	public Iterable<Personaje> findAll() {
		
		return repository.findAll();
	}

	private final static Logger logger = (Logger) LoggerFactory.getLogger(PersonajeImpl.class);

	@Transactional(readOnly = true)
	@Override
	public Optional<Personaje> findById(Long id) {
		return repository.findById(id);
	}

	@Transactional
	@Override
	public Personaje save(Personaje personaje) {
		return repository.save(personaje);
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception {
		Optional<Personaje> personaje = repository.findById(id);
		if(!personaje.isPresent()) {
    		throw new Exception("No existe el personaje con Id: ");
		}
		repository.deleteRelation(id);
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Iterable<Personaje> getPersonajesFilter(String name, Integer age, List<Long> moviesId) {

		CriteriaBuilder  criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Personaje> criteriaQuery = criteriaBuilder.createQuery(Personaje.class);      
	    Root<Personaje> personajeRoot = criteriaQuery.from(Personaje.class);
	    criteriaQuery.select(personajeRoot);
	    
	    // For like expression
	    Expression<String> path = personajeRoot.get("nombre");
	    Expression<String> lowerName = criteriaBuilder.lower(path);
	    
	    List<Predicate> predicates = new ArrayList<>();

	    if(name!=null && !name.isEmpty()) predicates.add(criteriaBuilder.like(lowerName, '%' + name.toLowerCase() + '%'));
	    
	    if(age!=null) predicates.add(criteriaBuilder.equal(personajeRoot.get("edad"), age));
	    
	    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

	    TypedQuery<Personaje> typedQuery = entityManager.createQuery(criteriaQuery);
	    List<Personaje> personajeList = typedQuery.getResultList();
	    
	    // Filter by movie ID
	    if(moviesId!=null && !moviesId.isEmpty()) {
	    	List<Personaje> listFilterByMovie = new ArrayList<>();
	    	
	    	personajeList.forEach(peli->{
	    		if(peli.getPeliculas().stream().filter(pel->moviesId.contains(pel.getId())).findAny().isPresent()) {
	    			listFilterByMovie.add(peli);
	    		}
	    	});
	    	return listFilterByMovie;
	    }

		return personajeList;
	}
}
