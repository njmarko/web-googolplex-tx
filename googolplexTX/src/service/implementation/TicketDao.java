package service.implementation;

import java.util.Collection;

import model.Ticket;
import repository.InMemoryRepository;
import service.TicketService;

public class TicketDao implements TicketService {

	@Override
	public Collection<Ticket> findAll() {
		return InMemoryRepository.findAllTickets();
	}

	@Override
	public Ticket findOne(String key) {
		return InMemoryRepository.findOneTicket(key);
	}

	@Override
	public Ticket save(Ticket entity) {
		return InMemoryRepository.save(entity);
	}

	@Override
	public Ticket delete(String key) {
		return InMemoryRepository.deleteTicket(key);
	}

	@Override
	public Ticket update(Ticket entity) {
		return InMemoryRepository.save(entity);
	}

}
