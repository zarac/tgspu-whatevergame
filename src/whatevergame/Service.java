package whatevergame;

import java.util.LinkedList;

import whatevergame.communication.Communicator;

// TODO : ? Should every service have their own thread?
abstract public class Service extends Thread
{
    public final static int NO_SERVICE_ID = -1;
    // TODO : Should not be hard-coded.
    public final static int LOGIN = 0;
    public final static int CHAT = 1;
    public final static int PEWPEW = 2;
    public final static int FIVEPAD = 3;
    public final static int COUNT = 4;

    protected int serviceId;
    // TODO : LinkedList?
    protected LinkedList<Session> sessions;
    protected Communicator communicator;

    abstract public void sendPackage(Package p_package);
    abstract public void recievePackage(Package p_package);

    public Service(int serviceId, Communicator communicator)
    {
        this.serviceId = serviceId;
        this.communicator = communicator;
        sessions = new LinkedList<Session>();
    }
}
