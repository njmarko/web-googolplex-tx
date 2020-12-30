package model;

import java.time.LocalDate;

import model.enumerations.Gender;
import model.enumerations.UserRole;

public class User {
	   private String username;
	   private String password;
	   private String firstName;
	   private String lastName;
	   private Gender gender;
	   private LocalDate birthDate;
	   private UserRole userRole;
	   private boolean deleted;
}
