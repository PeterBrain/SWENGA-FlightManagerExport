package at.fh.swenga.jpa.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import at.fh.swenga.jpa.dao.DocumentRepository;
import at.fh.swenga.jpa.dao.FlightRepository;
import at.fh.swenga.jpa.model.DocumentModel;
import at.fh.swenga.jpa.model.FlightModel;

@Controller
public class FlightController {

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	DocumentRepository documentRepository;

	@RequestMapping(value = { "/", "list" })
	public String index(Model model) {
		List<FlightModel> flights = flightRepository.findAll();
		model.addAttribute("flights", flights);
		return "index";
	}

	@RequestMapping("/fill")
	@Transactional
	public String fillData(Model model) {

		Date now = new Date();

		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, "Boeing 737", "Germany", "Spain",
				now, now, 148, "Lufthansa", false));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, "Boeing 767", "Austria", "Belgium",
				now, now, 72, "Austrian", true));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, "Boeing 777", "France", "Portugal",
				now, now, 65, "Eurowings", false));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, "Airbus A380", "Germany",
				"Thailand", now, now, 225, "Emirates", false));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, "Boeing 767", "Switzerland",
				"Germany", now, now, 103, "Swiss", false));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, "Airbus A320", "Spain", "Portugal",
				now, now, 62, "Iberia", true));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, "Boeing 747", "France", "Sweden",
				now, now, 98, "Norwegian", false));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, "Boeing 737", "Sweden", "Spain",
				now, now, 46, "Iberia", false));

		return "forward:list";
	}

	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		flightRepository.deleteById(id);

		return "forward:list";
	}

	/**
	 * Display the upload form
	 * 
	 * @param model
	 * @param flightId
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String showUploadForm(Model model, @RequestParam("id") int flightId) {
		model.addAttribute("flightId", flightId);
		return "uploadFile";
	}

	/**
	 * Save uploaded file to the database (as 1:1 relationship to flight)
	 * 
	 * @param model
	 * @param flightId
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadDocument(Model model, @RequestParam("id") int flightId,
			@RequestParam("myFile") MultipartFile file) {

		try {

			Optional<FlightModel> flightOpt = flightRepository.findById(flightId);
			if (!flightOpt.isPresent())
				throw new IllegalArgumentException("No flight with id " + flightId);

			FlightModel flight = flightOpt.get();

			// Already a document available -> delete it
			if (flight.getDocument() != null) {
				documentRepository.delete(flight.getDocument());
				// Don't forget to remove the relationship too
				flight.setDocument(null);
			}

			// Create a new document and set all available infos
			DocumentModel document = new DocumentModel();
			document.setContent(file.getBytes());
			document.setContentType(file.getContentType());
			document.setCreated(new Date());
			document.setFilename(file.getOriginalFilename());
			document.setName(file.getName());
			flight.setDocument(document);
			flightRepository.save(flight);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
		}

		return "forward:/list";
	}

	@RequestMapping("/download")
	public void download(@RequestParam("documentId") int documentId, HttpServletResponse response) {

		Optional<DocumentModel> docOpt = documentRepository.findById(documentId);
		if (!docOpt.isPresent())
			throw new IllegalArgumentException("No document with id " + documentId);

		DocumentModel doc = docOpt.get();

		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getFilename() + "\"");
			OutputStream out = response.getOutputStream();
			// application/octet-stream
			response.setContentType(doc.getContentType());
			out.write(doc.getContent());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "error";
	}

}
