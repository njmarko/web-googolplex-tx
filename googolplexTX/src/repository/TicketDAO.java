package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.Ticket;

public class TicketDAO implements GenericDAO<Ticket, String> {

	private Map<String, Ticket> tickets = new ConcurrentHashMap<String, Ticket>();

	@Override
	public Collection<Ticket> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket findOne(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket save(Ticket saved) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket delete(Ticket key) {
		// TODO Auto-generated method stub
		return null;
	}

}
