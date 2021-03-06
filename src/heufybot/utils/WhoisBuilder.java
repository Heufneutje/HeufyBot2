package heufybot.utils;

import java.util.List;

public class WhoisBuilder
{
    private String nickname, login, hostname, realname, server, serverInfo, operPrivs;
    private List<String> channels;
    private long idleSeconds, signOnTime;

    public String getNickname()
    {
        return this.nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getLogin()
    {
        return this.login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getHostname()
    {
        return this.hostname;
    }

    public void setHostname(String hostname)
    {
        this.hostname = hostname;
    }

    public String getRealname()
    {
        return this.realname;
    }

    public void setRealname(String realname)
    {
        this.realname = realname;
    }

    public String getServer()
    {
        return this.server;
    }

    public void setServer(String server)
    {
        this.server = server;
    }

    public String getServerInfo()
    {
        return this.serverInfo;
    }

    public void setServerInfo(String serverInfo)
    {
        this.serverInfo = serverInfo;
    }

    public List<String> getChannels()
    {
        return this.channels;
    }

    public void setChannels(List<String> channels)
    {
        this.channels = channels;
    }

    public long getIdleSeconds()
    {
        return this.idleSeconds;
    }

    public void setIdleSeconds(long idleSeconds)
    {
        this.idleSeconds = idleSeconds;
    }

    public long getSignOnTime()
    {
        return this.signOnTime;
    }

    public void setSignOnTime(long signOnTime)
    {
        this.signOnTime = signOnTime;
    }

    public String getOperPrivs()
    {
        return this.operPrivs;
    }

    public void setOperPrivs(String operPrivs)
    {
        this.operPrivs = operPrivs;
    }
}
