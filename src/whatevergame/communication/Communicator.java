package whatevergame.communication;

public class Communicator
{
    protected Sender sender;
    protected Reciever reciever;

    public Communicator(Sender sender, Reciever reciever)
    {
        this.sender = sender;
        this.reciever = reciever;
    }

    public void send(WrappedPackage wrappedPackage)
    {
        sender.send(wrappedPackage);
    }
}
