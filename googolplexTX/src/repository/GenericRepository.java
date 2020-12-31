package repository;

/**
 * @author Marko
 *	Generic interface for repository operations
 * 	Load and save operations are supported
 * @param <T> generic type of object that is being saved
 */
public interface GenericRepository<T> {

	T save(T object);
	
	T load();
}
