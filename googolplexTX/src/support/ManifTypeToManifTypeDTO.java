package support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import model.ManifestationType;
import web.dto.ManifestationTypeDTO;

public class ManifTypeToManifTypeDTO {

	public static ManifestationTypeDTO convert(ManifestationType manifType) {
		
		ManifestationTypeDTO retVal = new ManifestationTypeDTO();
		
		retVal.setName(manifType.getName());
		
		return retVal;
	}
	
	public static Collection<ManifestationTypeDTO> convert(Collection<ManifestationType> allManifsTypes){
		return allManifsTypes.stream().map(ManifTypeToManifTypeDTO::convert).collect(Collectors.toList());	
	}
	
	
	
}
