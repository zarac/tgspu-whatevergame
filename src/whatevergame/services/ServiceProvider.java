package whatevergame.services;

public class ServiceProvider
{
    protected Service[] services;
    public int count = 0;

    public void init(int maxSize)
    {
        services = new Service[maxSize];
    }

    public void add(int id, Service service)
    {
        services[id] = service;
        count++;
    }

    public Service get(int id)
    {
        return services[id];
    }
}
