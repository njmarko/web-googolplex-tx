package service.implementation;

import java.util.Collection;

import model.Ticket;
import repository.TicketDAO;
import service.TicketService;

public class TicketServiceImpl implements TicketService {

	private TicketDAO ticketDAO;

	public TicketServiceImpl(TicketDAO ticketDAO) {
		super();
		this.ticketDAO = ticketDAO;
	}

	@Override
	public Collection<Ticket> findAll() {
		return this.ticketDAO.findAll();
	}

	@Override
	public Ticket findOne(String key) {
		return this.ticketDAO.findOne(key);
	}

	@Override
	public Ticket save(Ticket entity) {
		return this.ticketDAO.save(entity);
	}

	@Override
	public Ticket delete(String key) {
		return this.ticketDAO.delete(key);
	}

	@Override
	public Ticket update(Ticket entity) {
		return this.ticketDAO.save(entity);
	}

}
