package nba;

public class game {
	
	private int gameid,season,CrewChiefID;
	private String HomeTeam,awayteam;
	
	
	
	
	public game(int gameid, int season, int crewChiefID, String homeTeam, String awayteam) {
		super();
		this.gameid = gameid;
		this.season = season;
		CrewChiefID = crewChiefID;
		HomeTeam = homeTeam;
		this.awayteam = awayteam;
	}
	public int getGameid() {
		return gameid;
	}
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	public int getSeason() {
		return season;
	}
	public void setSeason(int season) {
		this.season = season;
	}
	public int getCrewChiefID() {
		return CrewChiefID;
	}
	public void setCrewChiefID(int crewChiefID) {
		CrewChiefID = crewChiefID;
	}
	public String getHomeTeam() {
		return HomeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		HomeTeam = homeTeam;
	}
	public String getAwayteam() {
		return awayteam;
	}
	public void setAwayteam(String awayteam) {
		this.awayteam = awayteam;
	}
	@Override
	public String toString() {
		return "game [gameid=" + gameid + ", season=" + season + ", CrewChiefID=" + CrewChiefID + ", HomeTeam="
				+ HomeTeam + ", awayteam=" + awayteam + "]";
	}

	
	
}
