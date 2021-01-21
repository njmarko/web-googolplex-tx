package repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import model.CustomerType;
import support.JsonAdapter;

public class CustomerTypeDAO implements GenericDAO<CustomerType, String> {

	private Map<String, CustomerType> customerTypes = new ConcurrentHashMap<String, CustomerType>();

	public Map<String, CustomerType> getCustomerTypes() {
		return customerTypes;
	}

	public void setCustomerTypes(Map<String, CustomerType> customerTypes) {
		this.customerTypes = customerTypes;
	}

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

	@Override
	public Boolean saveFile() {
		System.out.println("[LOG] CustomerType saving");
		try {
			Gson gson = JsonAdapter.customerTypeSerializationToFile();

			FileWriter writer = new FileWriter("data/customerTypes.json");
			gson.toJson(customerTypes, writer);
			writer.flush();
			writer.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
