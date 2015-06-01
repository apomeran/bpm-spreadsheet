package com.it.itba.bpm;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gdata.util.ServiceException;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class OAuthConnector
{

	// Retrieve the CLIENT_ID and CLIENT_SECRET from an APIs Console project:
	// https://code.google.com/apis/console
	static String CLIENT_ID;
	static String CLIENT_SECRET;
	// Change the REDIRECT_URI value to your registered redirect URI for web
	// applications.
	static String REDIRECT_URI;
	// Add other requested scopes.
	static List<String> SCOPES = Collections.singletonList("https://spreadsheets.google.com/feeds");

	/**
	 * Retrieve OAuth 2.0 credentials.
	 *
	 * @return OAuth 2.0 Credential instance.
	 */
	static Credential getCredentials() throws IOException {
		final HttpTransport transport = new NetHttpTransport();
		final JacksonFactory jsonFactory = new JacksonFactory();

		// Step 1: Authorize -->
		final String authorizationUrl = new GoogleAuthorizationCodeRequestUrl(
				CLIENT_ID, REDIRECT_URI, SCOPES).build();

		// Point or redirect your user to the authorizationUrl.
		System.out.println("Go to the following link in your browser:");
		System.out.println(authorizationUrl + "&access_type=offline");

		// Read the authorization code from the standard input stream.
		final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("What is the authorization code?");
		final String code = in.readLine();
		// End of Step 1 <--

		// Step 2: Exchange -->
		final GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(
				transport, jsonFactory, CLIENT_ID, CLIENT_SECRET, code,
				REDIRECT_URI).execute();
		// End of Step 2 <--

		// SAVE REFRESH TOKEN TO REUSE !

		final PrintWriter outAccessToken = new PrintWriter("atoken.txt");
		outAccessToken.println(response.getAccessToken());
		outAccessToken.close();

		final PrintWriter outRefreshToken = new PrintWriter("rtoken.txt");
		outRefreshToken.println(response.getRefreshToken());
		outRefreshToken.close();

		// Build a new GoogleCredential instance and return it.
		return new GoogleCredential.Builder()
				.setClientSecrets(CLIENT_ID, CLIENT_SECRET)
				.setJsonFactory(jsonFactory).setTransport(transport).build()
				.setAccessToken(response.getAccessToken())
				.setRefreshToken(response.getRefreshToken());
	}

	private static Credential getRefreshToken() {
		final Credential credential = null;
		final BufferedReader aTokenbf;
		final BufferedReader rTokenbf;
		try {
			aTokenbf = new BufferedReader(new FileReader("rtoken.txt"));
			rTokenbf = new BufferedReader(new FileReader("atoken.txt"));
		} catch (final FileNotFoundException e) {
			return null;
		}
		String aToken = null;
		String rToken = null;
		try {
			final StringBuilder atokenBuilder = new StringBuilder();
			final StringBuilder rtokenBuilder = new StringBuilder();
			String atokenLine = aTokenbf.readLine();
			String rtokenLine = rTokenbf.readLine();

			while (rtokenLine != null && atokenLine != null) {
				rtokenBuilder.append(rtokenLine);
				// rtokenBuilder.append(System.lineSeparator());
				rtokenLine = rTokenbf.readLine();

				atokenBuilder.append(atokenLine);
				// atokenBuilder.append(System.lineSeparator());
				atokenLine = aTokenbf.readLine();
			}
			rToken = rtokenBuilder.toString();
			aToken = atokenBuilder.toString();
		} catch (final IOException e) {
			return null;
		} finally {
			try {
				rTokenbf.close();
				aTokenbf.close();
			} catch (final IOException e) {
				return null;
			}
		}
		final HttpTransport transport = new NetHttpTransport();
		final JacksonFactory jsonFactory = new JacksonFactory();

		if (aToken.contains("null") || rToken.contains("null"))
			return null;
		// System.out.println(aToken + rToken);
		return new GoogleCredential.Builder()
				.setClientSecrets(CLIENT_ID, CLIENT_SECRET)
				.setJsonFactory(jsonFactory).setTransport(transport).build()
				.setAccessToken(aToken).setRefreshToken(rToken);

	}

	public static Properties loadProperties() {
		final Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("src/main/resources/config.properties");

			// load a properties file
			prop.load(input);

		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	public static void main(String args[]) throws IOException, ServiceException {
		final Properties properties = loadProperties();
		CLIENT_ID = properties.getProperty("client_id");
		CLIENT_SECRET = properties.getProperty("client_secret");
		REDIRECT_URI = properties.getProperty("http://localhost:8089/callback");

		Credential credential = getRefreshToken();
		if (credential == null) { // First time calling
			credential = getCredentials();
		}
		SpreadSheetIntegration.init(credential);
	}

}
