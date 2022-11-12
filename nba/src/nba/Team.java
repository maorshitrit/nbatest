package nba;

public class Team {
	
	private int teamid;
	private String teamnameString;
	
	public Team(int teamid, String teamnameString) {
		super();
		this.teamid = teamid;
		this.teamnameString = teamnameString;
	}
	public int getTeamid() {
		return teamid;
	}
	public void setTeamid(int teamid) {
		this.teamid = teamid;
	}
	public String getTeamnameString() {
		return teamnameString;
	}
	public void setTeamnameString(String teamnameString) {
		this.teamnameString = teamnameString;
	}
	
}

