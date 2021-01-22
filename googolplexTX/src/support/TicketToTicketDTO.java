package support;


import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;

import model.Ticket;
import web.dto.TicketDTO;

public class TicketToTicketDTO {

	
	public static TicketDTO convert(Ticket t) {
		
		TicketDTO retVal = new TicketDTO();
		
		retVal.setId(t.getId());
		retVal.setPrice(t.getPrice());
		retVal.setTicketType(t.getTicketType().name());
		retVal.setTicketStatus(t.getTicketStatus().name());
		retVal.setDeleted(t.getDeleted());
		retVal.setCustomer(t.getCustomer().getUsername());
		retVal.setManifestation(t.getManifestation().getId());
		

		Long dateOfManifestation = t.getDateOfManifestation().toInstant(ZoneOffset.UTC).toEpochMilli();
		retVal.setDateOfManifestation(dateOfManifestation);
		
		if (t.getCancelationDate() != null) {
			Long cancelationDate = t.getCancelationDate().toInstant(ZoneOffset.UTC).toEpochMilli();
			retVal.setCancelationDate(cancelationDate);
		}
		
		retVal.setCutomerFullName(t.getCustomer().getFirstName() + " " + t.getCustomer().getLastName());
		retVal.setManifestationName(t.getManifestation().getName());
		
		
		return retVal;
	}
	
	public static Collection<TicketDTO> convert(Collection<Ticket> tickets){
		Collection<TicketDTO> retVal = new ArrayList<TicketDTO>();
		for (Ticket ticket : tickets) {
			retVal.add(convert(ticket));
		}	
		return retVal;
	}
	
	
	
	
}
