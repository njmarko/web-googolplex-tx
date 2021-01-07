package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.Ticket;

public class TicketDAO implements GenericDAO<Ticket, String> {

	private Map<String, Ticket> tickets = new ConcurrentHashMap<String, Ticket>();

	@Override
	public Collection<Ticket> findAll() {
		return tickets.values();
	}

	@Override
	public Ticket findOne(String key) {
		return tickets.get(key);
	}

	@Override
	public Ticket save(Ticket ticket) {
		return tickets.put(ticket.getId(), ticket);
	}

	@Override
	public Ticket delete(String key) {
		Ticket ticket = tickets.get(key);
		if (ticket != null) {
			ticket.setDeleted(true);
		}
		return ticket;
	}

}
