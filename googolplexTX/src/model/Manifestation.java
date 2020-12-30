package model;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;

import model.enumerations.ManifestationStatus;

public class Manifestation {
	   private String id;
	   private String name;
	   private int availableSeats;
	   private LocalDateTime dateOfOccurence;
	   private double regularPrice;
	   private ManifestationStatus status;
	   private Base64 poster;
	   private boolean deleted;
	   
	   public Salesman salesman;
	   public Location location;
	   public Collection<Comment> comments;
}
