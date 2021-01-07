package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.CustomerType;

public class CustomerTypeDAO implements GenericDAO<CustomerType, String> {

	private Map<String, CustomerType> customerTypes = new ConcurrentHashMap<String, CustomerType>();

	@Override
	public Collection<CustomerType> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerType findOne(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerType save(CustomerType saved) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerType delete(CustomerType key) {
		// TODO Auto-generated method stub
		return null;
	}

}
