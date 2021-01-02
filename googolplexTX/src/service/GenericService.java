package service;

import java.util.Collection;


/**
 * @author Marko
 *	Generic interface with methods that all entities will use
 * @param <T> Entity type
 * @param <KEY> Entity key
 */
public interface GenericService<T, KEY> {

	Collection<T> findAll();
	
	T findOne(KEY key);
	
	T save(T entity);
	
	T delete(KEY key);
	
	/*
	 * Updated object already contains the key
	 */
	T update(T entity);
}
