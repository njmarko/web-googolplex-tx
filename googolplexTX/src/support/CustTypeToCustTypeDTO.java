package support;

import java.util.Collection;
import java.util.stream.Collectors;

import model.CustomerType;
import web.dto.CustomerTypeDTO;

public class CustTypeToCustTypeDTO {

	
	public static CustomerTypeDTO convert(CustomerType entity) {
		CustomerTypeDTO dto = new CustomerTypeDTO();
		
		dto.setName(entity.getName());
		dto.setDiscount(entity.getDiscount());
		dto.setRequiredPoints(entity.getRequiredPoints());
		
		return dto;
	}
	
	public static Collection<CustomerTypeDTO> convert(Collection<CustomerType> entities) {
		return entities.stream().map(CustTypeToCustTypeDTO::convert).collect(Collectors.toList());		
	}
}
