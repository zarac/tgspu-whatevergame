package whatevergame;

import whatevergame.Content;

public class Package
{
    protected Content content;
    protected int serviceId;
    protected int sessionId;

    public Package(Content content, int serviceId)
    {
        this(content, serviceId, -1);
    }

    //// TODO : ? Is this an option, or is it ugly!?
    //public WrappedPackage wrap(Connection connection)
    //{
    //}

    public Package(Content content, int serviceId, int sessionId)
    {
        this.content = content;
        this.serviceId = serviceId;
        this.sessionId = sessionId;
    }
}
