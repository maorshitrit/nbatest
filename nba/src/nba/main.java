package nba;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.PublicKey;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.management.Notification;
import javax.security.auth.login.CredentialNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;

import netscape.javascript.JSObject;


public class main {
	
	static final String API_KEY = "a5fb04339f84532afc45e0c034c02590";


	public static void main(String[] args) throws AWTException {
		// TODO Auto-generated method stub

		System.out.println("test");
		System.out.print("david");
		System.out.println("tetbsro");	
		Getlivescores();
		createnotification();
	}
	public static void createnotification() throws AWTException {
		 SystemTray tray = SystemTray.getSystemTray();

	        //If the icon is a file
	        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
	        //Alternative (if the icon is on the classpath):
	        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

	        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
	        //Let the system resize the image if needed
	        trayIcon.setImageAutoSize(true);
	        //Set tooltip text for the tray icon
	        trayIcon.setToolTip("System tray icon demo");
	        tray.add(trayIcon);

	        trayIcon.displayMessage("Hello, World", "notification demo", MessageType.INFO);
	}
	
	public static void Getlivescores()
	{
		HttpClient client =  HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.the-odds-api.com/v4/sports/basketball_nba/scores/?daysFrom=2&apiKey="+API_KEY)).build();
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
		.thenApply(HttpResponse::body)
		.thenApply(main::Parse).join();
	}
	
	public static String Parse(String responsebody)
	{int i;
		// responsebody = responsebody.substring(1);
		JSONArray albumsArray = new JSONArray(responsebody);
	for( i =0; i<albumsArray.length();i++)
		{
			JSONObject albumJsonObject = albumsArray.getJSONObject(i);
			String id = albumJsonObject.getString("id");
			String sportkeyString = albumJsonObject.getString("sport_key");
			Boolean completed = albumJsonObject.getBoolean("completed");
		if(completed == true) {
			JSONArray jsonArray = (JSONArray)albumJsonObject.get("scores");
	         System.out.println("");
	         System.out.println(id + " " + sportkeyString +" ");
	         //Iterating the contents of the array
	         Iterator<Object> iterator = jsonArray.iterator();
	         while(iterator.hasNext() && iterator != null) {
	            System.out.println(iterator.next());
	         }
		}
		else {
			{
				 System.out.println("");
		         System.out.println(id + " " + sportkeyString +" ");
			}
		}
	  }
	System.out.println(albumsArray.length());
	System.out.println(i);
		return null;

}
}
