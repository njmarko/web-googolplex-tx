package repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import model.Ticket;
import support.JsonToFileAdapter;

public class TicketDAO implements GenericDAO<Ticket, String> {

	private Map<String, Ticket> tickets = new ConcurrentHashMap<String, Ticket>();

	public Map<String, Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Map<String, Ticket> tickets) {
		this.tickets = tickets;
	}

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

	@Override
	public Boolean saveFile() {
		System.out.println("[LOG] Tickets saving...");
		try {
			Gson gson = JsonToFileAdapter.ticketsSeraialization();

			FileWriter writer = new FileWriter("data/tickets.json");
			gson.toJson(tickets, writer);
			writer.flush();
			writer.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
			return true;
		}

		return false;
	}

}
