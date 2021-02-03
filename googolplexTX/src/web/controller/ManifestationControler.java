package web.controller;

import static spark.Spark.*;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;

import io.jsonwebtoken.io.IOException;
import model.Comment;
import model.CustomerType;
import model.Manifestation;
import model.ManifestationType;
import model.User;
import model.enumerations.CommentStatus;
import model.enumerations.ManifestationStatus;
import model.enumerations.UserRole;
import service.ManifestationService;
import service.implementation.ManifestationServiceImpl;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteImpl;
import spark.utils.IOUtils;
import support.CommentToCommentDTO;
import support.CustTypeToCustTypeDTO;
import support.JsonAdapter;
import support.ManifToManifDTO;
import support.ManifTypeToManifTypeDTO;
import web.dto.CommentDTO;
import web.dto.CustomerTypeDTO;
import web.dto.ManifestationDTO;
import web.dto.ManifestationSearchDTO;
import web.dto.ManifestationTypeDTO;

public class ManifestationControler {

	private ManifestationService manifService;

	private Gson gManifAdapter;
	private Gson g;
	private UserController userController;

	// TODO consider if empty constructor is needed

	public ManifestationControler(ManifestationService manifService, UserController uCntr) {
		super();
		this.manifService = manifService;
		this.g = new Gson();
		this.gManifAdapter = JsonAdapter.manifestationSeraialization();
		this.userController = uCntr;

	}

//	public ManifestationControler() {
//		
//		/**
//		 * Regular way to write paths in controllers by using lambda functions
//		 * Tradeoff is that you have to write the full path
//		 */
//		path("/manifestations",()->{
//			get("",(req,res)->{
//				
//				res.type("application/json");
//				Collection<Manifestation> mans = manifService.findAll();
//				System.out.println(mans);
//				return g.toJson(mans);	
//				
//			});	
//		});
//	}

	public final Route findAllManifestations = new Route() {

		@Override
		public Object handle(Request req, Response res) {
			// No login needed for this request.
			// TODO add pagination
			res.type("application/json");

			final Map<String, String> queryParams = new HashMap<>();
			req.queryMap().toMap().forEach((k, v) -> {
				queryParams.put(k, v[0]);
			});

			ManifestationSearchDTO searchParams = g.fromJson(g.toJson(queryParams), ManifestationSearchDTO.class);

			// TODO remove debug print message
			System.out.println("[DBG] searchParamsDTO" + searchParams);

			// TODO if admin return tickets of all statuses

			User loggedInUser = userController.getAuthedUser(req);
			System.out.println(loggedInUser);
			Collection<Manifestation> foundEntities = null;
			if (loggedInUser == null || loggedInUser.getUserRole() != UserRole.ADMIN) {
				// only admin can view manifestations of all statuses
				searchParams.setStatus("ACTIVE");
			}
			foundEntities = manifService.search(searchParams);

			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404, "No manifestations found");
			}

			return g.toJson(ManifToManifDTO.convert(foundEntities));
		}
	};

	public final Route findOneManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {

			res.type("application/json");
			String id = req.params("idm");
			Manifestation foundEntity = manifService.findOne(id);
			if (foundEntity == null) {
				halt(HttpStatus.NOT_FOUND_404, "No manifestation found");
			}

			return g.toJson(ManifToManifDTO.convert(foundEntity));
		}
	};

	public final Route saveOneManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			userController.authenticateSalesmanOrAdmin.handle(req, res);
			res.type("application/json");

			
			String body = req.body();

			ManifestationDTO manifestationData = g.fromJson(body, ManifestationDTO.class);

			User loggedInUser = userController.getAuthedUser(req);
			if (loggedInUser.getUserRole() == UserRole.SALESMAN) {
				manifestationData.setSalesman(loggedInUser.getUsername());
				manifestationData.setStatus(ManifestationStatus.INACTIVE.name());
			}

			// if (manifestationData.getPoster() == null)
			manifestationData.setPoster("default-manif.png");

			String err = manifestationData.validate();
			if (err != null) {
				halt(HttpStatus.BAD_REQUEST_400, err);
			}

			Manifestation savedEntity = manifService.save(manifestationData);
			if (savedEntity == null) {
				halt(HttpStatus.BAD_REQUEST_400);
			}
			return g.toJson(ManifToManifDTO.convert(savedEntity));

////			Example with adapter
//			String body = req.body();
//			Gson g= FileToJsonAdapter.manifestationsSerializationFromFile();
//			System.out.println("Jel se pre buni");
//			Manifestation manif = g.fromJson(body, Manifestation.class);
//			System.out.println("Ili se posle buni");
//			Manifestation saved = manifService.save(manif);
//			System.out.println(saved);
//			return  JsonToFileAdapter.manifestationSeraialization().toJson(saved);
		}
	};

	public final Route deleteOneManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			userController.authenticateAdmin.handle(req, res);

			// res.type("application/json");
			String id = req.params("idm");
			Manifestation deletedEntity = manifService.delete(id);
			if (deletedEntity == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}

			return HttpStatus.NO_CONTENT_204;
		}
	};

	public final Route editOneManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {

			res.type("application/json");
			String idm = req.params("idm");
			String body = req.body();

			userController.authenticateSalesmanOrAdmin.handle(req, res);

			ManifestationDTO newEntity = g.fromJson(body, ManifestationDTO.class);

			User loggedIn = userController.getAuthedUser(req);

			if (idm == null || newEntity == null) {
				halt(HttpStatus.BAD_REQUEST_400, "Manifestation data must be sent");
			} else if (idm.compareToIgnoreCase(newEntity.getId()) != 0) {
				halt(HttpStatus.BAD_REQUEST_400, "Id in the path must match the manifestation id in the data.");
			}

			Manifestation existingManif = manifService.findOne(newEntity.getId());
			String err = newEntity.validate(loggedIn, existingManif);
			if (err != null) {
				halt(HttpStatus.BAD_REQUEST_400, err);
			}

			// Set old poster
			if (newEntity.getPoster() == null)
				newEntity.setPoster(existingManif.getPoster());

			if (existingManif == null) {
				halt(HttpStatus.BAD_REQUEST_400, "Manifestation not found");
			} else if (loggedIn.getUserRole() == UserRole.SALESMAN && !loggedIn.equals(existingManif.getSalesman())) {
				halt(HttpStatus.BAD_REQUEST_400, "Salesman can only edit his own manifestations");
			}
			Manifestation savedEntity = manifService.save(newEntity);

			return g.toJson(ManifToManifDTO.convert(savedEntity));
		}
	};

	public final Route findAllManifestationsForSalesman = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			// No login needed for this request.
			// TODO add pagination
			res.type("application/json");

			userController.authenticateSalesmanOrAdmin.handle(req, res);

			String idu = req.params("idu");

			final Map<String, String> queryParams = new HashMap<>();
			req.queryMap().toMap().forEach((k, v) -> {
				queryParams.put(k, v[0]);
			});

			ManifestationSearchDTO searchParams = g.fromJson(g.toJson(queryParams), ManifestationSearchDTO.class);

			Collection<Manifestation> foundEntities = manifService.findBySalesman(idu, searchParams);
			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404, "No manifestations found");
			}

			return g.toJson(ManifToManifDTO.convert(foundEntities));
		}
	};

	public final Route findAllManifestationTypes = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			// No login needed for this request.
			// TODO add pagination

			res.type("application/json");

			Collection<ManifestationType> foundEntities = manifService.findAllManifestationTypes();
			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404, "No manifestation types found");
			}

			return g.toJson(ManifTypeToManifTypeDTO.convert(foundEntities));
		}
	};
	
	public final Route findOneManifestationType = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {

			res.type("application/json");

			String id = req.params("idmt");

			ManifestationType foundEntities = manifService.findOneManifestationType(id);
			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404, "Not found manifestation type.");
			}

			return g.toJson(ManifTypeToManifTypeDTO.convert(foundEntities));
		}
	};

	
	public final Route addOneManifestationType = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			userController.authenticateAdmin.handle(req, res);
			
			res.type("application/json");

			String body = req.body();

			ManifestationTypeDTO newEntity = g.fromJson(body, ManifestationTypeDTO.class);
			
			ManifestationType foundEntities = manifService.putOneManifestationType(null, newEntity);
			if (foundEntities == null) {
				halt(HttpStatus.BAD_REQUEST_400, "Name Already exists.");
			}

			return g.toJson(ManifTypeToManifTypeDTO.convert(foundEntities));
		}
	};
	
	public final Route putOneManifestationType = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			userController.authenticateAdmin.handle(req, res);
			
			res.type("application/json");

			String id = req.params("idmt");
			String body = req.body();

			ManifestationTypeDTO newEntity = g.fromJson(body, ManifestationTypeDTO.class);

			
			
			ManifestationType foundEntities = manifService.putOneManifestationType(id, newEntity);
			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404, "Name already exists or old-Name is invalid.");
			}

			return g.toJson(ManifTypeToManifTypeDTO.convert(foundEntities));
		}
	};
	
	public final Route deleteOneManifestationType = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			userController.authenticateAdmin.handle(req, res);

			// res.type("application/json");
			String id = req.params("idmt");
			ManifestationType deletedEntity = manifService.deleteOneManifestationType(id);
			if (deletedEntity == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}

			return HttpStatus.NO_CONTENT_204;
		}
	};

	public final Route findAllCommentsFromManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			// No login needed for this request.
			// No need to check if the manifestation is finished, because users are not
			// allowed to post comments that are not finished.
			// TODO add pagination

			res.type("application/json");
			String idm = req.params("idm");

			User loggedIn = userController.getAuthedUser(req);
			Manifestation manif = manifService.findOne(idm);

			if (manif == null) {
				halt(HttpStatus.BAD_REQUEST_400, "Manifestation doesn't exist");
			}

			Collection<Comment> foundEntities = null;

			if (loggedIn != null && (loggedIn.getUserRole() == UserRole.ADMIN
					|| (loggedIn.getUserRole() == UserRole.SALESMAN && manif.getSalesman().equals(loggedIn)))) {
				foundEntities = manifService.findAllCommentsFromManifestation(idm);
			}else {
					foundEntities = manifService.findAllApprovedCommentsForManif(idm);
			}

			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404, "No comments found");
			}

			return g.toJson(CommentToCommentDTO.convert(foundEntities));
		}
	};
	
	
	public final Route saveOneComment = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			// TODO add pagination

			res.type("application/json");
			String idm = req.params("idm");

			Manifestation manif = manifService.findOne(idm);

			if (manif == null) {
				halt(HttpStatus.BAD_REQUEST_400, "Manifestation doesn't exist");
			}

			String body = req.body();


			CommentDTO newEntity = g.fromJson(body, CommentDTO.class);
			
			userController.authenticateUser.handle(req,res);
			User loggedIn = userController.getAuthedUser(req);

			Comment comment = null;
			
			if (loggedIn == null) {
				halt(HttpStatus.FORBIDDEN_403, "You are not logged in");
			}
			else if (loggedIn.getUserRole() == UserRole.CUSTOMER) {
				newEntity.setApproved(CommentStatus.PENDING.name());
				comment = manifService.addUniqueComment(newEntity);
			}else if (loggedIn.getUserRole() == UserRole.ADMIN || loggedIn.getUserRole() == UserRole.SALESMAN) {
				comment = manifService.addUniqueComment(newEntity);
			}
			
			if (comment == null) {
				halt(HttpStatus.BAD_REQUEST_400, "Your comment was not added");
			}

			return g.toJson(CommentToCommentDTO.convert(comment));
		}
	};
	
	public final Route deleteManifestationComment = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			// TODO: Consider if user can delete comment
			// userController.authenticateAdmin.handle(req, res);

		//	String manifId = req.params("idm");
			String ticketId = req.params("idc");
			
			Comment deletedEntity = manifService.deleteComment(ticketId);
			if (deletedEntity == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}

			return HttpStatus.NO_CONTENT_204;
		}
	};

	public final Route uploadImage = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			req.attribute("org.eclipse.jetty.multipartConfig",
					new MultipartConfigElement(Paths.get("static", "uploads").toString()));

			String id = req.raw().getParameter("id");

			Manifestation manifestation = manifService.findOne(id);

			if (manifestation == null) {
				halt(HttpStatus.NOT_FOUND_404, "No manifestation found to add poster");
			}

			String fileName = "manif-" + id;

			fileName = uploadImage(req, "filename", fileName);
			if (fileName == null) {
				halt(HttpStatus.INTERNAL_SERVER_ERROR_500, "Error while uploading photo, try again or another one");
				System.out.println("not uploaded");
			}

			manifestation.setPoster(fileName);
			manifService.save(manifestation);
			System.out.println("uploaded");

			return "upload";
		}
	};

	public static String uploadImage(Request req, String paramName, String fileName) {
		Part filePart;

		try {
			req.attribute("org.eclipse.jetty.multipartConfig",
					new MultipartConfigElement(Paths.get("static").toAbsolutePath().toString()));
			filePart = req.raw().getPart(paramName);

			System.out.println(filePart.getSubmittedFileName());
			String uploadName = filePart.getSubmittedFileName();
			String[] extensionToken = uploadName.split("\\.");

			String extension;
			if (extensionToken.length > 1) {
				extension = extensionToken[extensionToken.length - 1];
			} else {
				extension = "jpg";
			}

			// TODO: Check if folder exist othervise create

			try (InputStream inputSteram = filePart.getInputStream()) {
				OutputStream outputStream = new FileOutputStream(
						Paths.get("static", "uploads", fileName + "." + extension).toAbsolutePath().toString());
				IOUtils.copy(inputSteram, outputStream);
				outputStream.close();
			}
			return fileName + "." + extension;

		} catch (IOException | ServletException | java.io.IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public final Route findOneComment = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {

			res.type("application/json");
			String id = req.params("idc");
			Comment foundEntity = manifService.findOneComment(id);
			if (foundEntity == null) {
				halt(HttpStatus.NOT_FOUND_404, "No comment found");
			}

			return g.toJson(CommentToCommentDTO.convert(foundEntity));
		}
	};


	
}

