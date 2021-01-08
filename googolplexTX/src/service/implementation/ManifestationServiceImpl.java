package service.implementation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
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
		entities = entities.stream().filter((Manifestation ent)->{return !ent.getDeleted();}).collect(Collectors.toList());
		return entities;
	}
	
	@Override
	public Collection<Manifestation> search(ManifestationSearchDTO searchParams) {
		Collection<Manifestation> entities = this.findAll();
		


//		private Long endDate;
//		// Location searches for city OR state
//		private String location;
//		private Double minPrice;
//		private Double maxPrice;
//
//		// search
//		private Boolean ascending;
//		private String sortCriteria;
//
//		// filter
//		private String manifestationType;
//		// Manifestation is not sold out
//		private Boolean hasAvailableTickets;
		
		
		// Search
		if (searchParams.getName()!=null) {
			entities = entities.stream()
					.filter((ent)->{return ent.getName().equalsIgnoreCase(searchParams.getName());})
					.collect(Collectors.toList());
		}
		
		
		// From local date time to miliseconds
//		date.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
		

		if (searchParams.getBeginDate()!=null) {
			
			Instant epochTime = java.time.Instant.ofEpochMilli(searchParams.getBeginDate());
			
			LocalDateTime beginDate = java.time.LocalDateTime
                    .ofInstant(epochTime, java.time.ZoneId.of("UTC"));
		
			entities = entities.stream()
					.filter((ent)->{return ent.getDateOfOccurence().isBefore(beginDate);})
					.collect(Collectors.toList());
		}
		
		
		return entities;
		
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
