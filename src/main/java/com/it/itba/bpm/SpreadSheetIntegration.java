package com.it.itba.bpm;
// ...
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
// ...

import java.util.Map;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

public class SpreadSheetIntegration {

	final static String GOOGLE_DRIVE_FEED_URL_STRING = "https://docs.google.com/feeds/default/private/full/";
	private static final String SPREADSHEET_SERVICE_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
	private static SpreadsheetService service;

	static void init(Credential credential) throws IOException,
			ServiceException {

		service = new SpreadsheetService("Aplication-name");
		service.setOAuth2Credentials(credential);
		printDocumentsTitles();
	}

	static void printDocumentContent(int index) throws IOException,
			ServiceException {
		List<com.google.gdata.data.spreadsheet.SpreadsheetEntry> spreadsheets = getFeed();

		com.google.gdata.data.spreadsheet.SpreadsheetEntry spreadsheet = spreadsheets
				.get(index);
		System.out.println(spreadsheet.getTitle().getPlainText());
		// Get the first worksheet of the first spreadsheet.
		// TODO: Choose a worksheet more intelligently based on your
		// app's needs.
		WorksheetFeed worksheetFeed = service.getFeed(
				spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
		List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		WorksheetEntry worksheet = worksheets.get(0);

		// Fetch the cell feed of the worksheet.
		URL cellFeedUrl = worksheet.getCellFeedUrl();
		CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);

		// Iterate through each cell, printing its value.
		for (CellEntry cell : cellFeed.getEntries()) {
			// Print the cell's address in A1 notation
			System.out.print(cell.getTitle().getPlainText() + "\t");
			// Print the cell's address in R1C1 notation
			System.out.print(cell.getId().substring(
					cell.getId().lastIndexOf('/') + 1)
					+ "\t");
			// Print the cell's formula or text value
			System.out.print(cell.getCell().getInputValue() + "\t");
			// Print the cell's calculated value if the cell's value is numeric
			// Prints empty string if cell's value is not numeric
			System.out.print(cell.getCell().getNumericValue() + "\t");
			// Print the cell's displayed value (useful if the cell has a
			// formula)
			System.out.println(cell.getCell().getValue() + "\t");
		}
	}

	static void printDocumentsTitles() throws IOException, ServiceException {
		List<com.google.gdata.data.spreadsheet.SpreadsheetEntry> spreadsheets = getFeed();
		System.out.println("Printing all Documents Titles");
		for (SpreadsheetEntry spreadsheetEntry : spreadsheets) {
			System.out.println(spreadsheetEntry.getTitle().getPlainText());
		}

	}

	private static List<com.google.gdata.data.spreadsheet.SpreadsheetEntry> getFeed()
			throws MalformedURLException, IOException, ServiceException {
		// Instantiate and authorize a new SpreadsheetService object.
		// entries.
		URL SPREADSHEET_FEED_URL = new URL(
				"https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		// Make a request to the API and get all spreadsheets.
		SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
				SpreadsheetFeed.class);
		List<com.google.gdata.data.spreadsheet.SpreadsheetEntry> spreadsheets = feed
				.getEntries();
		return spreadsheets;
	}

	private static SpreadsheetEntry getSpreadsheet(SpreadsheetService service,
			String sheetName) {
		try {

			SpreadsheetQuery spreadsheetQuery = new SpreadsheetQuery(new URL(
					GOOGLE_DRIVE_FEED_URL_STRING));
			spreadsheetQuery.setTitleQuery(sheetName);
			spreadsheetQuery.setTitleExact(true);

			SpreadsheetFeed spreadsheet = service.getFeed(spreadsheetQuery,
					SpreadsheetFeed.class);

			if (spreadsheet.getEntries() != null
					&& spreadsheet.getEntries().size() == 1) {
				return spreadsheet.getEntries().get(0);
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	private static WorksheetEntry getWorkSheet(SpreadsheetService service,
			String sheetName, String workSheetName) {
		try {
			SpreadsheetEntry spreadsheet = getSpreadsheet(service, sheetName);

			if (spreadsheet != null) {
				WorksheetFeed worksheetFeed = service.getFeed(
						spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
				List<WorksheetEntry> worksheets = worksheetFeed.getEntries();

				for (WorksheetEntry worksheetEntry : worksheets) {
					String wktName = worksheetEntry.getTitle().getPlainText();
					if (wktName.equals(workSheetName)) {
						return worksheetEntry;
					}
				}
			}
		} catch (Exception ex) {
		}

		return null;
	}

	private static Map<String, Object> getRowData(ListEntry row) {
		Map<String, Object> rowValues = new HashMap<String, Object>();
		for (String tag : row.getCustomElements().getTags()) {
			Object value = row.getCustomElements().getValue(tag);
			rowValues.put(tag, value);
		}
		return rowValues;
	}

	private static ListEntry createRow(Map<String, Object> rowValues) {
		ListEntry row = new ListEntry();
		for (String columnName : rowValues.keySet()) {
			Object value = rowValues.get(columnName);
			row.getCustomElements().setValueLocal(columnName,
					String.valueOf(value));
		}
		return row;
	}

	private static void updateRow(ListEntry row, Map<String, Object> rowValues) {
		for (String columnName : rowValues.keySet()) {
			Object value = rowValues.get(columnName);
			row.getCustomElements().setValueLocal(columnName,
					String.valueOf(value));
		}
	}

	// ...
}