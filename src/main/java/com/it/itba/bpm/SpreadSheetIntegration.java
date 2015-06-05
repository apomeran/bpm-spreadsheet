package com.it.itba.bpm;

// ...
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;
import com.it.itba.bpm.model.Status;
import com.it.itba.bpm.model.entry.ActiveSchoolEntry;
import com.it.itba.bpm.model.entry.FormEntry;
import com.it.itba.bpm.model.entry.SchoolEntry;
import com.it.itba.bpm.model.form.ActiveSchoolsForm;
import com.it.itba.bpm.model.form.ContactForm;
import com.it.itba.bpm.model.form.Form;

// ...

public class SpreadSheetIntegration {

	final static String GOOGLE_DRIVE_FEED_URL_STRING = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
	private static SpreadsheetService service;

	static void init(Credential credential) throws IOException,
			ServiceException, IllegalArgumentException, IllegalAccessException {

		service = new SpreadsheetService("Aplication-name");
		service.setOAuth2Credentials(credential);

		Form activeSchoolsForm = mockActiveSchoolsForm();

		Form contactForm = mockContactFormEntry();

		populateSpreadsheetWithForm(service, contactForm, "ContactForm");
		populateSpreadsheetWithForm(service, activeSchoolsForm,
				"ActiveSchoolsForm");
		// printDocumentsTitles();
	}

	private static Form mockActiveSchoolsForm() {
		FormEntry entry = new ActiveSchoolEntry();
		List<FormEntry> activeSchoolEntries = new ArrayList<FormEntry>();
		activeSchoolEntries.add(entry);
		Form activeSchoolForm = new ActiveSchoolsForm("Active Schools Form",
				activeSchoolEntries);
		return activeSchoolForm;
	}

	public static Form mockContactFormEntry() {

		SchoolEntry entry = new SchoolEntry("SchoolName", "Zone",
				"Neighborhood", "TElephone", "email", "contact", "mode", "map",
				"volunteer", true, true, DateTime.now(), "comments",
				DateTime.now(), "meetingComments", Status.ACCEPTED,
				"statusComments");
		List<FormEntry> contactFormEntry = new ArrayList<FormEntry>();
		contactFormEntry.add(entry);
		Form contactForm = new ContactForm("Contact Form", contactFormEntry);
		return contactForm;
	}

	private static void populateSpreadsheetWithForm(SpreadsheetService service,
			Form form, String spreadsheetName) throws IOException,
			ServiceException, IllegalArgumentException, IllegalAccessException {
		// Define the URL to request. This should never change.
		URL SPREADSHEET_FEED_URL = new URL(
				"https://spreadsheets.google.com/feeds/spreadsheets/private/full");

		// Make a request to the API and get all spreadsheets.
		SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
				SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();

		if (spreadsheets.size() == 0) {
			// TODO: There were no spreadsheets, act accordingly.
		}

		// TODO: Choose a spreadsheet more intelligently based on your
		// app's needs.
		SpreadsheetEntry spreadsheet = null;
		for (SpreadsheetEntry spreadsheetEntry : spreadsheets) {
			if (spreadsheetEntry.getTitle().getPlainText()
					.equals(spreadsheetName))
				spreadsheet = spreadsheetEntry;
		}

		if (spreadsheet == null)
			throw new IllegalStateException("Spreadsheet Not Found");

		System.out.println(spreadsheet.getTitle().getPlainText());

		// Get the first worksheet of the first spreadsheet.
		// TODO: Choose a worksheet more intelligently based on your
		// app's needs.
		WorksheetFeed worksheetFeed = service.getFeed(
				spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
		List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		WorksheetEntry worksheet = worksheets.get(0);

		// Fetch the list feed of the worksheet.
		URL listFeedUrl = worksheet.getListFeedUrl();
		ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

		// Create a local representation of the new row.
		ListEntry row = new ListEntry();

		// Use reflection to populate with Contact Form
		for (FormEntry schoolEntry : form.getEntries()) {
			for (Field field : SchoolEntry.class.getDeclaredFields()) {
				if (!Modifier.isStatic(field.getModifiers())) {
					field.setAccessible(true);
					row.getCustomElements().setValueLocal(field.getName(),
							field.get(schoolEntry).toString());
				}
			}
		}

		// Send the new row to the API for insertion.
		row = service.insert(listFeedUrl, row);

	}

	static void printDocumentContent(int index) throws IOException,
			ServiceException {
		final List<com.google.gdata.data.spreadsheet.SpreadsheetEntry> spreadsheets = getFeed();

		final com.google.gdata.data.spreadsheet.SpreadsheetEntry spreadsheet = spreadsheets
				.get(index);
		System.out.println(spreadsheet.getTitle().getPlainText());
		// Get the first worksheet of the first spreadsheet.
		// TODO: Choose a worksheet more intelligently based on your
		// app's needs.
		final WorksheetFeed worksheetFeed = service.getFeed(
				spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
		final List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		final WorksheetEntry worksheet = worksheets.get(0);

		// Fetch the cell feed of the worksheet.
		final URL cellFeedUrl = worksheet.getCellFeedUrl();
		final CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);

		// Iterate through each cell, printing its value.
		for (final CellEntry cell : cellFeed.getEntries()) {
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
		final List<com.google.gdata.data.spreadsheet.SpreadsheetEntry> spreadsheets = getFeed();
		System.out.println("Printing all Documents Titles");
		for (final SpreadsheetEntry spreadsheetEntry : spreadsheets) {
			System.out.println(spreadsheetEntry.getTitle().getPlainText());
		}

	}

	private static List<com.google.gdata.data.spreadsheet.SpreadsheetEntry> getFeed()
			throws MalformedURLException, IOException, ServiceException {
		// Instantiate and authorize a new SpreadsheetService object.
		// entries.
		final URL SPREADSHEET_FEED_URL = new URL(
				"https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		// Make a request to the API and get all spreadsheets.
		final SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
				SpreadsheetFeed.class);
		final List<com.google.gdata.data.spreadsheet.SpreadsheetEntry> spreadsheets = feed
				.getEntries();
		return spreadsheets;
	}

}