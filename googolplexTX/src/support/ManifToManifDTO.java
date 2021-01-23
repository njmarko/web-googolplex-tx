package support;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import model.Comment;
import model.Manifestation;
import model.Ticket;
import web.dto.ManifestationDTO;

public class ManifToManifDTO {

	public static ManifestationDTO convert(Manifestation manif) {
		
		ManifestationDTO retVal = new ManifestationDTO();
		
		retVal.setId(manif.getId());
		retVal.setName(manif.getName());
		retVal.setAvailableSeats(manif.getAvailableSeats());
		// DATUM
		Long dateOfOccurence = manif.getDateOfOccurence().toInstant(ZoneOffset.UTC).toEpochMilli();
		retVal.setDateOfOccurence(dateOfOccurence);
		
		retVal.setRegularPrice(manif.getRegularPrice());
		retVal.setStatus(manif.getStatus().name());
		// TODO ADD POSTER
		retVal.setManifestationType(manif.getManifestationType().getName());
		retVal.setSalesman(manif.getSalesman().getUsername());
		retVal.setLocation(manif.getLocation());
		
		retVal.setComments(manif.getComments()
				.stream()
				.map(Comment::getId)
				.collect(Collectors.toList()));
		
		retVal.setTickets(manif.getTickets()
				.stream()
				.map(Ticket::getId)
				.collect(Collectors.toList())
				);
		
		return retVal;
	}
	
	public static Collection<ManifestationDTO> convert(Collection<Manifestation> allManifs){
		return allManifs.stream().map(ManifToManifDTO::convert).collect(Collectors.toList());	
	}
	
	
	
}
