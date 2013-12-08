package heufybot.core.events.types;

import heufybot.core.Channel;
import heufybot.core.User;

public class JoinEvent implements BotEvent
{
	private User user;
	private Channel channel;
	
	public JoinEvent(User user, Channel channel)
	{
		this.user = user;
		this.channel = channel;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public Channel getChannel()
	{
		return channel;
	}
	
	public String toString()
	{
		return "JoinEvent";
	}
}