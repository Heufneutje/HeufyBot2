package heufybot.modules;

public class Message
{
    private String from;
    private String text;
    private String dateSent;
    private String messageSource;

    Message(String from, String text, String dateSent, String messageSource)
    {
        this.from = from;
        this.text = text;
        this.dateSent = dateSent;
        this.messageSource = messageSource;
    }

    public String getFrom()
    {
        return this.from;
    }

    public String getText()
    {
        return this.text;
    }

    public String getDateSent()
    {
        return this.dateSent;
    }

    public String getMessageSource()
    {
        return this.messageSource;
    }
}
