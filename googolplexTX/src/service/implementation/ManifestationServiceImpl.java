package service.implementation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import model.Manifestation;
import repository.ManifestationDAO;
import service.ManifestationService;
import web.dto.ManifestationSearchDTO;

public class ManifestationServiceImpl implements ManifestationService {

	private ManifestationDAO manifestationDAO;
	// TODO: Add required DAOs

	public ManifestationServiceImpl(ManifestationDAO manifestationDAO) {
		super();
		this.manifestationDAO = manifestationDAO;
	}

	@Override
	public Collection<Manifestation> findAll() {
		Collection<Manifestation> entities = this.manifestationDAO.findAll();
		entities = entities.stream().filter((Manifestation ent) -> {
			return !ent.getDeleted();
		}).collect(Collectors.toList());
		return entities;
	}

	@Override
	public Collection<Manifestation> search(ManifestationSearchDTO searchParams) {
		Collection<Manifestation> entities = this.findAll();

		// Search
		if (searchParams.getName() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getName().toLowerCase().contains(searchParams.getName().toLowerCase());
			}).collect(Collectors.toList());
		}

		// From local date time to miliseconds
//		date.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();

		if (searchParams.getBeginDate() != null) {

			Instant epochTime = java.time.Instant.ofEpochMilli(searchParams.getBeginDate());

			LocalDateTime beginDate = java.time.LocalDateTime.ofInstant(epochTime, java.time.ZoneId.of("UTC"));

			entities = entities.stream().filter((ent) -> {
				return ent.getDateOfOccurence().isAfter(beginDate);
			}).collect(Collectors.toList());
		}

		if (searchParams.getEndDate() != null) {
			Instant epochTime = java.time.Instant.ofEpochMilli(searchParams.getEndDate());

			LocalDateTime endDate = java.time.LocalDateTime.ofInstant(epochTime, java.time.ZoneId.of("UTC"));

			entities = entities.stream().filter((ent) -> {
				return ent.getDateOfOccurence().isBefore(endDate);
			}).collect(Collectors.toList());
		}

		// Location he searchParams is just a string for City name OR Country name
		// TODO Add check for Country name if it is implemented in the model
		if (searchParams.getLocation() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getLocation().getCity().equalsIgnoreCase(searchParams.getLocation());
			}).collect(Collectors.toList());
		}

		if (searchParams.getMinPrice() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getRegularPrice() >= searchParams.getMinPrice();
			}).collect(Collectors.toList());
		}

		if (searchParams.getMaxPrice() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getRegularPrice() <= searchParams.getMaxPrice();
			}).collect(Collectors.toList());
		}

		if (searchParams.getMaxPrice() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getRegularPrice() <= searchParams.getMaxPrice();
			}).collect(Collectors.toList());
		}

		if (searchParams.getManifestationType() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getManifestationType().equals(searchParams.getManifestationType());
			}).collect(Collectors.toList());
		}

		if (searchParams.getHasAvailableTickets() != null && searchParams.getHasAvailableTickets() == true) {
			entities = entities.stream().filter((ent) -> {
				return ent.getTickets().size() < ent.getAvailableSeats();
			}).collect(Collectors.toList());
		}

		// It sorts by ascending by default
		if (searchParams.getSortCriteria() != null) {
			Boolean ascending = searchParams.getAscending() != null ? searchParams.getAscending() : true;

			final Map<String, Comparator<Manifestation>> critMap = new HashMap<String, Comparator<Manifestation>>();
			critMap.put("name", Comparator.comparing(Manifestation::getName));
			critMap.put("date", Comparator.comparing(Manifestation::getName));
			critMap.put("price", Comparator.comparing(Manifestation::getName));
			critMap.put("location", Comparator.comparing(Manifestation::getName)); // everything except lattitude and
																					// lognitude for now
			// TODO add radius search for location

			// If sortCriteria is wrong it doesn't sort the collection
			Comparator<Manifestation> comp = critMap.get(searchParams.getSortCriteria().toLowerCase().trim());
			if (comp != null) {
				entities = entities.stream().sorted(ascending ? comp : comp.reversed()).collect(Collectors.toList());
			}
		}

		return entities;

	}

	public static int ascending(Manifestation o1, Manifestation o2) {

		return 0;
	}

	@Override
	public Manifestation findOne(String key) {
		return this.manifestationDAO.findOne(key);
	}

	@Override
	public Manifestation save(Manifestation entity) {
		this.manifestationDAO.save(entity);
		return entity;
	}

	@Override
	public Manifestation delete(String key) {
		return this.manifestationDAO.delete(key);
	}

	@Override
	public Manifestation update(Manifestation entity) {
		return this.manifestationDAO.save(entity);
	}

}