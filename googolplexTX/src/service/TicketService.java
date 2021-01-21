package service;

import java.util.Collection;

import model.Ticket;
import web.dto.TicketSearchDTO;

public interface TicketService extends GenericService<Ticket, String> {

	public Collection<Ticket> findAllByUser(String key);
	public Collection<Ticket> findAllBySalesman(String key);
	public Collection<Ticket> findAllByManifestation(String key);

	public Collection<Ticket> searchByUser(String key, TicketSearchDTO searchParams);
	
}
