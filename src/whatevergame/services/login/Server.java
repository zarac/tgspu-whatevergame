package whatevergame.services.login;

import whatevergame.communication.Communicator;

import whatevergame.Package;
import whatevergame.Service;

public class Server extends Service
{

    /**
     * @see Service#Service(int,Communicator)
     */
    public Server(int serviceId, Communicator communicator)
    {
        super(serviceId, communicator);
    }

    /**
     * {@inheritDoc}
     * @see Service#sendPackage(Package)
     */
    public void sendPackage(Package p_package)
    {
    }

    /**
     * {@inheritDoc}
     * @see Service#recievePackage(Package)
     */
    public void recievePackage(Package p_package)
    {
    }
}
