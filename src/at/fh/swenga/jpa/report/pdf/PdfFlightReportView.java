package at.fh.swenga.jpa.report.pdf;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import at.fh.swenga.jpa.model.FlightModel;

public class PdfFlightReportView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// change the file name
		response.setHeader("Content-Disposition", "attachment; filename=\"report.pdf\"");

		List<FlightModel> flights = (List<FlightModel>) model.get("flights");

		document.add(new Paragraph("Flight list"));

		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 1.0f, 3.0f, 3.0f, 3.0f, 3.0f, 2.0f });
		table.setSpacingBefore(10);

		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		// write table header
		cell.setPhrase(new Phrase("ID", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Aircraft", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Origin", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Destination", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Airline", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Cancelled", font));
		table.addCell(cell);

		// write table row data
		for (FlightModel flight : flights) {
			table.addCell(flight.getFlightId() + "");
			table.addCell(flight.getAircraft());
			table.addCell(flight.getOrigin());
			table.addCell(flight.getDestination());
			table.addCell(flight.getAirline());
			table.addCell(flight.getCancelled() ? "true" : "false");
		}

		document.add(table);
	}

}
