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
		team[0].setTeamnameString("WAS");team[1].setTeamnameString("CHA");team[2].setTeamnameString("ATL");team[3].setTeamnameString("MIA");team[4].setTeamnameString("ORL");
		team[5].setTeamnameString("NY");team[6].setTeamnameString("PHI");team[7].setTeamnameString("BKN");team[8].setTeamnameString("BOS");team[9].setTeamnameString("TOR");
		team[10].setTeamnameString("CHI");team[11].setTeamnameString("CLE");team[12].setTeamnameString("IND");team[13].setTeamnameString("DET");team[14].setTeamnameString("MIL");
		team[15].setTeamnameString("MIN");team[16].setTeamnameString("UTA");team[17].setTeamnameString("OKC");team[18].setTeamnameString("POR");team[19].setTeamnameString("DEN");
		team[20].setTeamnameString("MEM");team[21].setTeamnameString("HOU");team[22].setTeamnameString("NO");team[23].setTeamnameString("SA");team[24].setTeamnameString("DAL");
		team[25].setTeamnameString("GS");team[26].setTeamnameString("LAL");team[27].setTeamnameString("LAC");team[28].setTeamnameString("PHO");team[29].setTeamnameString("SAC");
		
		
		Team [] teamarray = {new Team(1,"WAS"),new Team(2,"CHA"),new Team(3,"ATL"),new Team(4,"MIA"),new Team(5,"ORL"),new Team(6,"NY"),new Team(7,"PHI"),new Team(8,"BKN"),new Team(9,"BOS")
				,new Team(10,"TOR"),new Team(11,"CHI"),new Team(12,"IND"),new Team(13,"DET"),new Team(14,"MIL"),new Team(15,"MIN"),new Team(16,"UTA"),new Team(17,"OKC"),new Team(18,"POR"),new Team(19,"DEN")
				,new Team(20,"MEM"),new Team(21,"HOU"),new Team(22,"NO"),new Team(23,"SA"),new Team(24,"DAL"),new Team(25,"GS"),new Team(26,"LAL"),new Team(27,"LAC"),new Team(1,"WAS"),new Team(1,"WAS")};
		insertdata(teamarray);
		for(int i=0;i<30;i++)
		{
		System.out.println(teamarray[i].toString());
		}
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
	
	public static void insertdata(Team [] team)
	{
		
		for(int i=0;i<30;i++)
		{
			team[i].setTeamid(i);
		}
		team[0].setTeamnameString("WAS");team[1].setTeamnameString("CHA");team[2].setTeamnameString("ATL");team[3].setTeamnameString("MIA");team[4].setTeamnameString("ORL");
		team[5].setTeamnameString("NY");team[6].setTeamnameString("PHI");team[7].setTeamnameString("BKN");team[8].setTeamnameString("BOS");team[9].setTeamnameString("TOR");
		team[10].setTeamnameString("CHI");team[11].setTeamnameString("CLE");team[12].setTeamnameString("IND");team[13].setTeamnameString("DET");team[14].setTeamnameString("MIL");
		team[15].setTeamnameString("MIN");team[16].setTeamnameString("UTA");team[17].setTeamnameString("OKC");team[18].setTeamnameString("POR");team[19].setTeamnameString("DEN");
		team[20].setTeamnameString("MEM");team[21].setTeamnameString("HOU");team[22].setTeamnameString("NO");team[23].setTeamnameString("SA");team[24].setTeamnameString("DAL");
		team[25].setTeamnameString("GS");team[26].setTeamnameString("LAL");team[27].setTeamnameString("LAC");team[28].setTeamnameString("PHO");team[29].setTeamnameString("SAC");
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
