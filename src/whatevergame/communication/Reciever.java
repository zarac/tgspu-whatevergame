package whatevergame.communication;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import whatevergame.Service;

public class Reciever extends Thread
{
    protected Service[] services;
    protected ServerSocket serverSocket;
    protected short port;

    public Reciever(short port)
    {
        this.port = port;
        services = new Service[Service.COUNT];
        connect();
    }

    public boolean connect()
    {
        try
        {
            serverSocket = new ServerSocket();
            start();
            return true;
        }
        catch (IOException e)
        {
            System.out.println("Error (IO): " + e.getMessage());
        }

        return false;
    }

    /**
     * {@inheritDoc}
     * @see Runnable#run()
     */
    public void run()
    {
        while (true)
        {
            System.out.println("Waiting for connection.");
            try
            {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted a connection from: " +
                        socket.getInetAddress());
            }
            catch (IOException e)
            {
                System.out.println("Error (IO): " + e.getMessage());
            }
        }
    }
}
