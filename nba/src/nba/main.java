package nba;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.beans.JavaBean;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.PublicKey;
import java.sql.Date;
import java.sql.Struct;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.management.Notification;
import javax.security.auth.login.CredentialNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;

import netscape.javascript.JSObject;


public class main {
	
	static final String API_KEY = "a5fb04339f84532afc45e0c034c02590"; // FOR LIVE SCORES + NAMES
	static final String Referees_Url = "https://api.sportsdata.io/v3/nba/scores/json/Referees?key=ae3e9b9d6dec4f9a9dedc0c29f2d289d";  // REFEEREE IDS
	static final String SEASON_Standings = "https://api.sportsdata.io/v3/nba/scores/json/Standings/2023?key=ae3e9b9d6dec4f9a9dedc0c29f2d289d"; // SEASON STANDINGS
	static final String TEAMID_STRING  = "https://api.sportsdata.io/v3/nba/scores/json/teams?key=ae3e9b9d6dec4f9a9dedc0c29f2d289d"; // TEAM ID STRING
	final static java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
	static final String game_by_date = "https://api.sportsdata.io/v3/nba/scores/json/GamesByDate/"+date+"?key=ae3e9b9d6dec4f9a9dedc0c29f2d289d"; // game by date
	static int scott_Foster_id = 20000044;
	// nba referees  ------- https://official.nba.com/referee-assignments/ -------------
	
	public static void main(String[] args) throws AWTException {
		// TODO Auto-generated method stub

		System.out.println(scott_Foster_id);
		System.out.println(date);	
		System.out.println("test");
		System.out.println("david");	
		
		getthegamesToday();
	}
	
	public static boolean CheckReferee()
	{
		return true;
	}
	
	public static boolean isNull(Object obj) {
	     return obj == null;
	 }
	
	 public static void getthegamesToday()
		{
			HttpClient client =  HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(game_by_date)).build();
			client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
			.thenApply(HttpResponse::body)
			.thenApply(main::Parse).join();
		}
	 
	 public static game Parse(String responsebody)
		{int i;
		int CrewChiefID = 0;
			// responsebody = responsebody.substring(1);
			JSONArray albumsArray = new JSONArray(responsebody);
		for( i =0; i<albumsArray.length();i++)
			{
				JSONObject albumJsonObject = albumsArray.getJSONObject(i);
				int GameID = albumJsonObject.getInt("GameID");
				int Season = albumJsonObject.getInt("Season");
				String HomeTeam = albumJsonObject.getString("HomeTeam");
				String AwayTeam = albumJsonObject.getString("AwayTeam");
				
				if(!(albumJsonObject.isNull("CrewChiefID"))) 
				{
					CrewChiefID = albumJsonObject.getInt("CrewChiefID");
					if(CrewChiefID == scott_Foster_id)
					{
						game game = new game(GameID, Season, CrewChiefID, HomeTeam, AwayTeam);
						try {
							createnotification();
						} catch (AWTException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return game;
					}
				}
				
				
				
				System.out.println(GameID +" " + HomeTeam + " " +AwayTeam + " " +CrewChiefID);
			}
		return null;
		  }
	
	public void insertdata(Team [] team)
	{
		
		for(int i=1;i<=30;i++)
		{
			team[i].setTeamid(i);
		}
		team[0].setTeamnameString("WAS");
	}
	
	public static void WhengameHappend() throws AWTException
	{
		//Getlivescores();
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

	        trayIcon.displayMessage("Adam Hagever", "notification demo", MessageType.INFO);
	}
	
	/* public static void Getlivescores()
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
			String home_team = albumJsonObject.getString("home_team");
			String away_team = albumJsonObject.getString("away_team");
			String commence_time = albumJsonObject.getString("commence_time");
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
		         System.out.println(id + " " + sportkeyString +" " + home_team + " " + away_team +" " +commence_time );
			}
		}
	  }
	System.out.println(albumsArray.length());
	System.out.println(i);
		return null;

}*/
}
