

package whatevergame.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

// TODO : Find out wutfux the deal is with the streams.. when should it be
// closed etc?
public class Connection
{
    protected String address;
    protected int port;
    protected Socket socket;
    protected ObjectOutputStream objectOutputStream;
    protected ObjectInputStream objectInputStream;

    public Connection(String address, int port)
    {
        this.address = address;
        this.port = port;
        //this.connect(address, port);
    }
    
    public boolean connect()
    {
        try
        {
            socket = new Socket(address, port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            return true;
        }
        catch (UnknownHostException e)
        {
            // TODO : ? Act properly...
            System.out.println("ERROR (UnknownHost): " + e.getMessage());
        }
        catch (IOException e)
        {
            // TODO : ? Act properly...
            System.out.println("ERROR (IO): " + e.getMessage());
        }
        return false;
    }

    // TODO : Not used at the moment, is it needed?
    public void disconnect()
    {
        try
        {
            objectOutputStream.close();
            objectInputStream.close();
        }
        catch (IOException e)
        {
            System.out.println("ERROR (IO): " + e.getMessage());
        }
    }

    /**
     * Gets the socket for this instance.
     *
     * @return The socket.
     */
    public Socket getSocket()
    {
        return this.socket;
    }

    /**
     * Gets the objectOutputStream for this instance.
     *
     * @return The objectOutputStream.
     */
    public ObjectOutputStream getObjectOutputStream()
    {
        return this.objectOutputStream;
    }

    /**
     * Gets the objectInputStream for this instance.
     *
     * @return The objectInputStream.
     */
    public ObjectInputStream getObjectInputStream()
    {
        return this.objectInputStream;
    }
}
