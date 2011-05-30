package whatevergame.services.fivepad.server;

import whatevergame.server.Client;
import whatevergame.services.fivepad.Content;
import whatevergame.services.ServerService;
import whatevergame.services.fivepad.server.logic.Administrator;

public class Server extends ServerService
{
    private Administrator administrator;
            
    public Server(int id, whatevergame.server.Server server)
    {
        super(id, server);
        administrator = new Administrator();
    }

    /**
     * {@inheritDoc}
     * @see ServerService#receive(Client,Content)
     */
    @Override
    public void receive(Client client, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
        logger.info("client sent us fivepad content '" + content + "'");
        
        if(content.toString().equals("WantToPlay")){

            send(client, new Content(administrator.wantToPlay(client.getSessionId())));
            
            if(administrator.amIFirst(client.getSessionId())){
                send(client, new Content("You are first"));
            }
            
        } else if(administrator.isPlayer(client.getSessionId())) {
            
            if(administrator.tryToMakeMove(client.getSessionId(), content.toString())){
                sendToOtherClient(client.getSessionId(), content.toString());
                administrator.iterateCurrentPlayer(client.getSessionId());
            }
                isThereWinner(client);
                tellUserWhoseTurn(client.getSessionId());
        }
    }
    
    public void isThereWinner(Client client){
        if(administrator.getWinner(client.getSessionId())){
            for(int j = 0; j < clients.size(); j++){

                if(clients.get(j).getSessionId()==client.getSessionId()){
                    send(clients.get(j), new Content("You are winner."));
                } else {
                    send(clients.get(j), new Content("You did not win."));
                }
            }
        }
    }
    
    public void tellUserWhoseTurn(int clientId){
        for(int j = 0; j < clients.size(); j++){
            if(administrator.getCurrentClientID(clientId) == clients.get(j).getSessionId()){
                send(clients.get(j), new Content("Your turn"));
            }
        }
    }
    
    public void sendToOtherClient(int id, String in){
        int buddy = administrator.findBuddy(id);
        for(int i = 0; i < clients.size(); i++){
            if(clients.get(i).getSessionId() == buddy){
                send(clients.get(i), new Content(in));
            }
        }
    }

    @Override
    public void addClient(Client client)
    {
        super.addClient(client);
        logger.info("Client added to five pad service..");
    }
}
