package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.CustomerType;

public class CustomerTypeDAO implements GenericDAO<CustomerType, String> {

	private Map<String, CustomerType> customerTypes = new ConcurrentHashMap<String, CustomerType>();

	@Override
	public Collection<CustomerType> findAll() {
		return customerTypes.values();
	}

	@Override
	public CustomerType findOne(String key) {
		return customerTypes.get(key);
	}

	@Override
	public CustomerType save(CustomerType customerType) {
		return customerTypes.put(customerType.getName(), customerType);
	}

	@Override
	public CustomerType delete(String key) {
		CustomerType customerType = customerTypes.get(key);
		if (customerType != null) {
			customerType.setDeleted(true);
		}
		return customerType;
	}

}
