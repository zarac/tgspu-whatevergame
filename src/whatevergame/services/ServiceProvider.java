package whatevergame.services;

import java.lang.reflect.Array;

public class ServiceProvider
{
    Service[] services;

    public void init(int size)
    {
        services = new Service[size];
    }

    public void add(int id, Service service)
    {
        services[id] = service;
    }

    public Service get(int id)
    {
        return services[id];
    }
}
