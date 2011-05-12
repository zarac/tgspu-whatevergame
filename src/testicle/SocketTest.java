package testicle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Timer;
import java.util.TimerTask;

public class SocketTest
{
    Server server;
    Client clientOne;

    public SocketTest() 
    {
        server = new Server();
        Timer timer = new Timer();
        timer.schedule(new ScheduledTask(timer), 5000);
        clientOne = new Client();
    }

    class ScheduledTask extends TimerTask
    {
        Timer timer;

        public ScheduledTask(Timer timer)
        {
            this.timer = timer;
        }

        public void run()
        {
            System.out.println("TIME IS UP!");
            timer.cancel();
        }
    }
    
    public static void main(String[] args)
    {
        new SocketTest();
    }

    protected class Server extends Thread
    {
        Socket clientOne;
        ServerSocket serverSocket;
        boolean acceptingConnections = true;
        ObjectInputStream ois;
        ObjectOutputStream oos;

        public Server()
        {
            System.out.println("Server():");
            try
            {
                System.out.println("Server(): trying...");
                serverSocket = new ServerSocket(3000);
                System.out.println("Server(): created socket...");
                start();
                System.out.println("Server(): started...");
            }
            catch (IOException e)
            {
                System.out.println("Error (IO): " + e.getMessage());
            }
        }

        /**
         * {@inheritDoc}
         * @see Runnable#run()
         */
        public void run()
        {
            System.out.println("Server.run():");
            while (acceptingConnections)
            {
                try
                {
                    System.out.println("Waiting for connection.");
                    Socket socket = serverSocket.accept();
                    ois = new ObjectInputStream(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject("HEJ KLIENTEEEN");
                    oos.flush();
                }
                catch (IOException e)
                {
                    System.out.println("Error (IO): " + e.getMessage());
                }
            }
        }
    }

    protected class Client extends Thread
    {
        Socket socket;
        ObjectInputStream ois;
        ObjectOutputStream oos;

        public Client()
        {
            System.out.println("Client():");
            start();
        }

        public void run()
        {
            System.out.println("Client.run():");
            try
            {
                System.out.println("Client.run(): trying...");
                socket = new Socket("127.0.0.1", 3000);
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                String str = (String)ois.readObject();
                System.out.println("We've got: " + str);
                ois.close();
                oos.close();
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("Error (ClassNotFound): " + e.getMessage());
            }
            catch (UnknownHostException e)
            {
                System.out.println("ERROR (UnknownHost): " + e.getMessage());
            }
            catch (IOException e)
            {
                System.out.println("Error (IO): " + e.getMessage());
            }
            System.out.println("Client.run(): tried...");
        }
    }
}
