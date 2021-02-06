package support;

import java.time.ZoneOffset;
import java.util.Collection;
import java.util.stream.Collectors;

import model.Comment;
import model.Customer;
import model.Manifestation;
import model.Salesman;
import model.Ticket;
import model.User;
import model.enumerations.UserRole;
import web.dto.UserDTO;

public class UserToUserDTO {

	public static UserDTO convert(User u) {

		UserDTO retVal = new UserDTO();

		retVal.setUsername(u.getUsername());
		retVal.setFirstName(u.getFirstName());
		retVal.setLastName(u.getLastName());
		retVal.setGender(u.getGender().name());

		Long birthDate = u.getBirthDate().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();

		retVal.setBirthDate(birthDate);

		retVal.setUserRole(u.getUserRole().name());

		retVal.setBlocked(u.getBlocked());

		if (u.getUserRole() == UserRole.SALESMAN) {
			retVal.setManifestations(((Salesman) u).getManifestation().stream().map(Manifestation::getName)
					.collect(Collectors.toList()));
		} else if (u.getUserRole() == UserRole.CUSTOMER) {
			Customer cust = (Customer) u;
			retVal.setPoints(cust.getPoints());
			retVal.setCustomerType(cust.getCustomerType().getName());

			retVal.setComments(cust.getComments().stream().map(Comment::getId).collect(Collectors.toList()));

			retVal.setTickets(cust.getTickets().stream().map(Ticket::getId).collect(Collectors.toList()));
		}

		return retVal;
	}

	public static Collection<UserDTO> convert(Collection<User> users) {
		return users.stream().map(UserToUserDTO::convert).collect(Collectors.toList());
	}

}
