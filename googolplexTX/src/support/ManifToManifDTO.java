package support;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.stream.Collectors;

import model.Comment;
import model.Manifestation;
import model.Ticket;
import model.enumerations.CommentStatus;
import model.enumerations.ManifestationStatus;
import model.enumerations.TicketStatus;
import web.dto.ManifestationDTO;

public class ManifToManifDTO {

	public static ManifestationDTO convert(Manifestation manif) {

		ManifestationDTO retVal = new ManifestationDTO();

		retVal.setId(manif.getId());
		retVal.setName(manif.getName());
		retVal.setAvailableSeats(manif.getAvailableSeats());

		Integer numReservedTickets = manif.getTickets().stream().filter((t) -> {
			return t.getTicketStatus() == TicketStatus.RESERVED;
		}).collect(Collectors.toList()).size();
		Integer totalSeats = manif.getAvailableSeats();
		if (numReservedTickets != null) {
			totalSeats = totalSeats + numReservedTickets;
		}
		retVal.setTotalSeats(totalSeats);

		// DATUM
		Long dateOfOccurence = manif.getDateOfOccurence().toInstant(ZoneOffset.UTC).toEpochMilli();
		retVal.setDateOfOccurence(dateOfOccurence);

		retVal.setRegularPrice(manif.getRegularPrice());
		retVal.setStatus(manif.getStatus().name());

		retVal.setPoster(manif.getPoster());
		retVal.setManifestationType(manif.getManifestationType().getName());
		retVal.setSalesman(manif.getSalesman().getUsername());
		retVal.setLocation(manif.getLocation());

		retVal.setComments(manif.getComments().stream().map(Comment::getId).collect(Collectors.toList()));

		retVal.setTickets(manif.getTickets().stream().map(Ticket::getId).collect(Collectors.toList()));

		// check if the manifestation was approved (is active) and it has passed
		if (manif.getStatus() == ManifestationStatus.ACTIVE) {
			LocalDate manifDate = manif.getDateOfOccurence().toLocalDate();
			LocalDate today = LocalDate.now();
			if (today.isAfter(manifDate)) {
				// if the manifestation occurred then i can calculate the average score from the
				// comments
				Double sum = 0d;
				Integer n = 0;
				for (Comment c : manif.getComments()) {
					if (c.getApproved() == CommentStatus.ACCEPTED && c.getDeleted() == false) {
						sum += c.getRating();
						n = n + 1;
					}
				}
				if (n > 0) {
					retVal.setAverageRating(sum / n);
				}

			}
		}

		return retVal;
	}

	public static Collection<ManifestationDTO> convert(Collection<Manifestation> allManifs) {
		return allManifs.stream().map(ManifToManifDTO::convert).collect(Collectors.toList());
	}

}
