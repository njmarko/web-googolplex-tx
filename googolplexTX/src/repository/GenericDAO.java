package repository;

import java.util.Collection;

/**
 * Generic dao interface https://www.baeldung.com/java-dao-pattern
 * 
 * @author Marko
 *
 * @param <T>
 * @param <KEY>
 */
public interface GenericDAO<T, KEY> {

	Collection<T> findAll();

	T findOne(KEY key);

	T save(T saved);

	T delete(T key);

}
