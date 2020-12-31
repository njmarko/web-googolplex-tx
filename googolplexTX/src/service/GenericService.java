package service;

import java.util.Collection;


/**
 * @author Marko
 *	Generic interface with methods that all entities will use
 * @param <T> Entity type
 * @param <ID> Entity key
 */
public interface GenericService<T, ID> {

	Collection<T> findAll();
	
	T findOne(ID id);
	
	T save(T object);
	
	T delete(ID id);
	
	/*
	 * Updated object already contains the key
	 */
	T update(T object);
}
