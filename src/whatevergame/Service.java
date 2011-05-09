package whatevergame;

import java.util.LinkedList;

import whatevergame.communication.Communicator;

// TODO : ? Should every service have their own thread?
abstract public class Service extends Thread
{
    public final static int NO_SESSION_ID = -1;
    protected int serviceId;
    // TODO : LinkedList?
    protected LinkedList<Session> sessions;
    protected Communicator communicator;

    abstract public void sendPackage(Package p_package);
    abstract public void recievePackage(Package p_package);
}
