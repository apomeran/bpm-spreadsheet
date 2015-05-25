import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gdata.util.ServiceException;

public class OAuthConnector {

	// Retrieve the CLIENT_ID and CLIENT_SECRET from an APIs Console project:
	// https://code.google.com/apis/console
	static String CLIENT_ID = "876335379105-9oq8o26bftg9r4mgo68543ahh4bbmlnf.apps.googleusercontent.com";
	static String CLIENT_SECRET = "giCDIfgqMkRKHDM6wf2ZH-Bi";
	// Change the REDIRECT_URI value to your registered redirect URI for web
	// applications.
	static String REDIRECT_URI = "http://localhost:8089/callback";
	// Add other requested scopes.
	static List<String> SCOPES = Arrays
			.asList("https://spreadsheets.google.com/feeds");

	public static void main(String args[]) throws IOException, ServiceException {

		Credential credential = getRefreshToken();
		if (credential == null) { // First time calling
			credential = getCredentials();
		}
		SpreadSheetIntegration.init(credential);
	}

	private static Credential getRefreshToken() {
		Credential credential = null;
		BufferedReader aTokenbf;
		BufferedReader rTokenbf;
		try {
			aTokenbf = new BufferedReader(new FileReader("rtoken.txt"));
			rTokenbf = new BufferedReader(new FileReader("atoken.txt"));
		} catch (FileNotFoundException e) {
			return null;
		}
		String aToken = null;
		String rToken = null;
		try {
			StringBuilder atokenBuilder = new StringBuilder();
			StringBuilder rtokenBuilder = new StringBuilder();
			String atokenLine = aTokenbf.readLine();
			String rtokenLine = rTokenbf.readLine();

			while (rtokenLine != null && atokenLine != null) {
				rtokenBuilder.append(rtokenLine);
				//rtokenBuilder.append(System.lineSeparator());
				rtokenLine = rTokenbf.readLine();

				atokenBuilder.append(atokenLine);
				//atokenBuilder.append(System.lineSeparator());
				atokenLine = aTokenbf.readLine();
			}
			rToken = rtokenBuilder.toString();
			aToken = atokenBuilder.toString();
		} catch (IOException e) {
			return null;
		} finally {
			try {
				rTokenbf.close();
				aTokenbf.close();
			} catch (IOException e) {
				return null;
			}
		}
		HttpTransport transport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();

		if (aToken.contains("null") || rToken.contains("null"))
			return null;
		//System.out.println(aToken + rToken);
		return new GoogleCredential.Builder()
				.setClientSecrets(CLIENT_ID, CLIENT_SECRET)
				.setJsonFactory(jsonFactory).setTransport(transport).build()
				.setAccessToken(aToken).setRefreshToken(rToken);

	}

	/**
	 * Retrieve OAuth 2.0 credentials.
	 * 
	 * @return OAuth 2.0 Credential instance.
	 */
	static Credential getCredentials() throws IOException {
		HttpTransport transport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();

		// Step 1: Authorize -->
		String authorizationUrl = new GoogleAuthorizationCodeRequestUrl(
				CLIENT_ID, REDIRECT_URI, SCOPES).build();

		// Point or redirect your user to the authorizationUrl.
		System.out.println("Go to the following link in your browser:");
		System.out.println(authorizationUrl + "&access_type=offline");

		// Read the authorization code from the standard input stream.
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("What is the authorization code?");
		String code = in.readLine();
		// End of Step 1 <--

		// Step 2: Exchange -->
		GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(
				transport, jsonFactory, CLIENT_ID, CLIENT_SECRET, code,
				REDIRECT_URI).execute();
		// End of Step 2 <--

		// SAVE REFRESH TOKEN TO REUSE !

		PrintWriter outAccessToken = new PrintWriter("atoken.txt");
		outAccessToken.println(response.getAccessToken());
		outAccessToken.close();

		PrintWriter outRefreshToken = new PrintWriter("rtoken.txt");
		outRefreshToken.println(response.getRefreshToken());
		outRefreshToken.close();
		
		// Build a new GoogleCredential instance and return it.
		return new GoogleCredential.Builder()
				.setClientSecrets(CLIENT_ID, CLIENT_SECRET)
				.setJsonFactory(jsonFactory).setTransport(transport).build()
				.setAccessToken(response.getAccessToken())
				.setRefreshToken(response.getRefreshToken());
	}

}
