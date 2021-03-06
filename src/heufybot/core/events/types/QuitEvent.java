package heufybot.core.events.types;

import heufybot.core.IRCUser;

public class QuitEvent implements BotEvent
{
    private IRCUser user;
    private String serverName, message;

    public QuitEvent(String serverName, IRCUser user, String message)
    {
        this.serverName = serverName;
        this.user = user;
        this.message = message;
    }

    public String getServerName()
    {
        return this.serverName;
    }

    public IRCUser getUser()
    {
        return this.user;
    }

    public String getMessage()
    {
        return this.message;
    }

    @Override
    public String toString()
    {
        return "QuitEvent";
    }
}
