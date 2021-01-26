package repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import model.Ticket;
import support.JsonAdapter;

public class TicketDAO implements GenericDAO<Ticket, String> {

	private Map<String, Ticket> tickets = new ConcurrentHashMap<String, Ticket>();

	
	public String findNextId() {
		if (tickets != null && tickets.size() > 0) {		
			Long highest = tickets.keySet().stream().map(Long::valueOf).sorted(Comparator.reverseOrder()).findFirst().get();
			Long id = highest + 1 ;
			return id.toString();
		}else {
			return "1";
		}
	}
	
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
			Gson gson = JsonAdapter.ticketsSeraialization();

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
