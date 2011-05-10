package whatevergame.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.PriorityQueue;

public class Sender extends Thread
{
    protected PriorityQueue<WrappedPackage> queue;

    public Sender()
    {
        queue = new PriorityQueue<WrappedPackage>();
        start();
    }

    public void send(WrappedPackage wrappedPackage)
    {
        queue.add(wrappedPackage);
    }

    /**
     * {@inheritDoc}
     * @see Runnable#run()
     */
    public void run()
    {
        while (true)
        {
            if (!queue.isEmpty())
            {
                WrappedPackage wrappedPackage = queue.remove();
                try
                {
                    System.out.println("1" + wrappedPackage.getConnection());
                    System.out.println("2" + wrappedPackage.getConnection().getObjectOutputStream());
                    ObjectOutputStream oos = wrappedPackage.getConnection().getObjectOutputStream();
                    oos.writeObject(wrappedPackage.getPackage());
                    oos.flush();
                }
                catch (IOException e)
                {
                    System.out.println("ERROR (IO): " + e.getMessage());
                }
            }
            else
            {
                try
                {
                    sleep(1);
                }
                catch (InterruptedException e)
                {
                    System.out.println("ERROR (Interrupted): " + e.getMessage());
                }
            }
        }
    }
}
