package service;

import java.util.Collection;

import model.CustomerType;
import model.Ticket;
import web.dto.ReservationDTO;
import web.dto.TicketSearchDTO;

public interface TicketService extends GenericService<Ticket, String> {

	public Collection<Ticket> findAllByUser(String key);
	public Collection<Ticket> findAllBySalesman(String key, TicketSearchDTO searchParams);
	public Collection<Ticket> findAllByManifestation(String key);

	public Collection<Ticket> search(String key, TicketSearchDTO searchParams);
	
	public Collection<Ticket> reserve(ReservationDTO reservation);
	
	public CustomerType determineCustomerType(Double points);
	
	public Ticket cancelTicket(String key);

}
