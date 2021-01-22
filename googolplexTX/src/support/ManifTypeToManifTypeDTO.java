package support;

import java.util.ArrayList;
import java.util.Collection;

import model.ManifestationType;
import web.dto.ManifestationTypeDTO;

public class ManifTypeToManifTypeDTO {

	public static ManifestationTypeDTO convert(ManifestationType manifType) {
		
		ManifestationTypeDTO retVal = new ManifestationTypeDTO();
		
		retVal.setName(manifType.getName());
		
		return retVal;
	}
	
	public static Collection<ManifestationTypeDTO> convert(Collection<ManifestationType> allManifsTypes){
		Collection<ManifestationTypeDTO> retVal = new ArrayList<ManifestationTypeDTO>();
		for (ManifestationType manifType : allManifsTypes) {
			retVal.add(convert(manifType));
		}	
		return retVal;
	}
	
	
	
}
