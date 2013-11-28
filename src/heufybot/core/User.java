package heufybot.core;

public class User 
{
	private String nickname;
	private String login;
	private String hostmask;
	
	public User(String nickname, String login, String hostmask)
	{
		this.nickname = nickname;
		this.login = login;
		this.hostmask = hostmask;
	}
	
	public User(String nickname)
	{
		this.nickname = nickname;
	}
	
	public String getNickname()
	{
		return nickname;
	}
	
	public String getLogin()
	{
		return login;
	}
	
	public String getHostmask()
	{
		return hostmask;
	}
	
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	
	public void setLogin(String login)
	{
		this.login = login;
	}
	
	public void setHostmask(String hostmask)
	{
		this.hostmask = hostmask;
	}
}