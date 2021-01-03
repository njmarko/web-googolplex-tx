package web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jetty.util.resource.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Address;
import model.Comment;
import model.Customer;
import model.CustomerType;
import model.Location;
import model.Manifestation;
import model.ManifestationType;
import model.Salesman;
import model.Ticket;
import model.User;
import model.enumerations.Gender;
import model.enumerations.ManifestationStatus;
import model.enumerations.TicketStatus;
import model.enumerations.TicketType;
import model.enumerations.UserRole;
import repository.InMemoryRepository;
import service.ManifestationService;
import service.UserService;
import service.implementation.ManifestationDao;
import support.JsonAdapterUtil;
import web.controller.ManifestationControler;
import web.controller.UserController;


import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import spark.Request;
import spark.Session;


public class GoogolplexTXMain {

	
	public static void main(String[] args) {

		TestData.createTestData();
		
		new ManifestationControler(new ManifestationDao());
		new UserController();
		
	}

}
