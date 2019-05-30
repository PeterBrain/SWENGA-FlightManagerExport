package at.fh.swenga.jpa.report;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import at.fh.swenga.jpa.model.FlightModel;

public class ExcelFlightReportView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// change the file name
		response.setHeader("Content-Disposition", "attachment; filename=\"report.xlsx\"");

		List<FlightModel> flights = (List<FlightModel>) model.get("flights");

		// ------------------------------------------------------
		// APACHE POI Documenations and examples:
		// https://poi.apache.org/spreadsheet/index.html
		// ------------------------------------------------------

		// create a worksheet
		Sheet sheet = workbook.createSheet("Flight Report");

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColorPredefined.BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		font.setBold(true);
		font.setColor(HSSFColorPredefined.WHITE.getIndex());
		style.setFont(font);

		// create a new row in the worksheet
		Row headerRow = sheet.createRow(0);

		// create a new cell in the row
		Cell cell0 = headerRow.createCell(0);
		cell0.setCellValue("ID");
		cell0.setCellStyle(style);

		// create a new cell in the row
		Cell cell1 = headerRow.createCell(1);
		cell1.setCellValue("Aircraft");
		cell1.setCellStyle(style);

		// create a new cell in the row
		Cell cell2 = headerRow.createCell(2);
		cell2.setCellValue("Origin");
		cell2.setCellStyle(style);

		// create a new cell in the row
		Cell cell3 = headerRow.createCell(3);
		cell3.setCellValue("Destination");
		cell3.setCellStyle(style);

		// create a new cell in the row
		Cell cell4 = headerRow.createCell(4);
		cell4.setCellValue("Airline");
		cell4.setCellStyle(style);

		// create a new cell in the row
		Cell cell5 = headerRow.createCell(5);
		cell5.setCellValue("Cancelled");
		cell5.setCellStyle(style);

		// create multiple rows with flight data
		int rowNum = 1;
		for (FlightModel flight : flights) {
			// create the row data
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(flight.getFlightId());
			row.createCell(1).setCellValue(flight.getAircraft());
			row.createCell(2).setCellValue(flight.getOrigin());
			row.createCell(3).setCellValue(flight.getDestination());
			row.createCell(4).setCellValue(flight.getAirline());
			row.createCell(5).setCellValue(flight.getCancelled());
		}

		// adjust column width to fit the content
		sheet.autoSizeColumn((short) 0);
		sheet.autoSizeColumn((short) 1);
		sheet.autoSizeColumn((short) 2);
		sheet.autoSizeColumn((short) 3);
		sheet.autoSizeColumn((short) 4);
		sheet.autoSizeColumn((short) 5);
	}

}
