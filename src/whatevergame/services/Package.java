package whatevergame.services;

import java.io.Serializable;

/**
 * A package. It contains some arbitrary content and an ID of the service to
 * receive the package.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Package implements Serializable
{
    /**
     * Arbitrary content. It should be specific to every service.
     */
    protected Content content;

    /**
     * The service ID to handle the package.
     */
    protected int serviceId;

    /**
     * Packages content to be delivered to a service.
     * 
     * @param content The content to package.
     * @param serviceId ID of service to receive package.
     */
    public Package(Content content, int serviceId)
    {
        this.content = content;
        this.serviceId = serviceId;
    }

    /**
     * Gets the content for this instance.
     *
     * @return The content.
     */
    public Content getContent()
    {
        return this.content;
    }

    /**
     * Gets the serviceId for this instance.
     *
     * @return The serviceId.
     */
    public int getServiceId()
    {
        return this.serviceId;
    }
    
    /**
     * {@inheritDoc}
     * @see Object#toString()
     */
    public String toString()
    {
        return "Package[serviceId='" + serviceId + "', Content='" + content.toString() + "]";
    }
}
