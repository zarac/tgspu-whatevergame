package whatevergame.services.pewpew.server;

import java.util.ArrayList;
import java.util.LinkedList;
//import server.Gun;
import whatevergame.server.Client;

import whatevergame.services.pewpew.State;
import whatevergame.services.pewpew.StateContent;

import whatevergame.services.ServerService;

import whatevergame.services.pewpew.Content;
import whatevergame.services.pewpew.ContentServer;
import whatevergame.services.pewpew.server.logic.Gun;
import whatevergame.services.pewpew.server.logic.Player;

public class Server extends ServerService
{
    protected static int nextRoomId = 1;
    protected LinkedList<Room> rooms;

    private int counter = 0;
    private int minlimit = 2;
    private Gun gun;
    private ArrayList<Player> players;
    
    public Server(int id, whatevergame.server.Server server)
    {
        super(id, server);
        gun = new Gun(3);
        players = new ArrayList<Player>();
        rooms = new LinkedList<Room>();
    }

    /**
     * {@inheritDoc}
     * @see ServerService#removeClient(Client)
     */
    public void removeClient(Client client)
    {
        super.removeClient(client);
        // TODO : graceful removal of client
        // loop through all rooms and remove player if part of one...
    }

    public void receive(Client client, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
        logger.debug("Received content '" + content + "' from client '" + client + "'.");
            
        if (content.getCommand() == Content.PEWPEW_INIT)
        {
            logger.debug("command=init");
            Room room = getAvailableRoom();
            room.addPlayer(new Player(gun, client));
            if (room.isFull())
                room.start();
            room.sendToAll(new Content(Content.PEWPEW_INIT));
        }
        else if (content.getCommand() == Content.PEWPEW_SPINN)
        {
            logger.warning("spin");

            Room room = rooms.get(content.getRoomId());
            Player player = room.getPlayer(client);
            if (player.equals(room.getCurrentPlayer()))
            {
                player.spinChamber();
                room.sendToAll(new StateContent(room.getState()));
            }
            else
            {
                send(client, new Content(Content.NOT_YOUR_TURN, room.getId()));
            }
        }
        else if (content.getCommand() == Content.PEWPEW_SHOOT)
        {
            logger.warning("shoot");
            Room room = rooms.get(content.getRoomId());
            Player player = room.getPlayer(client);
            if (player.equals(room.getCurrentPlayer()))
            {
                if (gun.fireGun())
                {
                    player.setToDead();
                    room.resetPoints();
                }
                else
                    room.increasePoints();

                if (room.nextPlayer() == false)
                {
                    sendToAll(new StateContent(room.getState()));
                    room.finish();
                }
                else
                    room.sendToAll(new StateContent(room.getState()));
            }
            else
            {
                send(client, new Content(Content.NOT_YOUR_TURN, room.getId()));
            }
        }
        else if (content.getCommand() == Content.PEWPEW_REPLAY)
        {
            logger.warning("replay");
            Room room = rooms.get(content.getRoomId());
            Player player = room.getPlayer(client);
            player.setReady(true);

            room.sendToAll(new StateContent(room.getState()));

            // TODO : Check if everyone is ready, if so, start new "round"
            //room.start();
        }
        else if (content.getCommand() == Content.PEWPEW_EXIT)
        {
            logger.warning("exit");
            //room.sendToAll(new StateContent(room.getState()));
        }
    }
    
    public boolean isAllDead(){
        boolean isAllDead = true;
        for(int i = 0; i < players.size(); i++){
            if(!players.get(i).isDead()){
                isAllDead = false;
            }
        }
        return isAllDead;
    }
    
    public void iterateCounter(){
            boolean check = true;
            if(isAllDead()){
//                "All players are dead.";
                sendMessageToAll(false, false, false, true);
                players = new ArrayList<Player>();
//                JOptionPane.showMessageDialog(null, "Players size = "+players.size());
                counter = 0;
            }
            else {
                while(check){
                    counter++;
                    if(counter > players.size()){
                        counter = 0;
                    }
                    check = players.get(counter).isDead();
                }   
            }
    }

    /**
     * Says hello to added clients and tells them the service ID.
     *
     * {@inheritDoc}
     * @see ServerService#addClient(Client)
     */
    public void addClient(Client client)
    {
        super.addClient(client);
        players.add(new Player(gun, client));
        
        if(clients.size() < minlimit){
//            String text = "Await more players";
            sendMessageToAll(false,false, true, false);
        } else {
//            String text = "Enough players";
            sendMessageToAll(false,false, true, false);
        }
    }
    
    public void sendMessageToAll(){
        sendMessageToAll(true,false, false, false);
    }
    
    public void sendMessageToAll(boolean in1, boolean in2, boolean in3, boolean in4){
        ContentServer content;
        for(int i = 0; i < clients.size() && i < players.size(); i++){
            if(counter == i){
                content = new ContentServer(players.get(i).isDead(),players.get(i).getRound(),in1, in3, in4);
                send(clients.get(i), content);
            } else {
                content = new ContentServer(players.get(i).isDead(),players.get(i).getRound(),in1, in3, in4);
                send(clients.get(i), content);
            }
        }
    }

    //public void sendMessageToAll(boolean in1, boolean in2, boolean in3, boolean in4){
        //ContentServer content;
        //for(int i = 0; i < clients.size() && i < players.size(); i++){
            //if(counter == i){
                //content = new ContentServer(players.get(i).isDead(),players.get(i).getRound(),in1, in3, in4);
                //send(clients.get(i), content);
            //} else {
                //content = new ContentServer(players.get(i).isDead(),players.get(i).getRound(),in1, in3, in4);
                //send(clients.get(i), content);
            //}
        //}
    //}

    private void pewpewExit(Client client) {
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getID() == client.getSessionId()){
               players.get(i).setToDead();
            }
        }
    }

    private void pewpewQue(Client client) {
//        JOptionPane.showMessageDialog(null, "Players size (que) = "+players.size());
        boolean addToGame = true;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getID() == client.getSessionId()){
               addToGame = false;
//               JOptionPane.showMessageDialog(null, "Add to game false");
            }
        }
        if(addToGame){
//            JOptionPane.showMessageDialog(null, "Add to game if true");
            players.add(new Player(gun, client));
        }
        iterateCounter();
//        JOptionPane.showMessageDialog(null, "Players size (que) = "+players.size());
    }

    protected Room getAvailableRoom()
    {
        for (int i = 0; i < rooms.size(); ++i)
        {
            if (rooms.get(i).isFull() == false)
            {
                return rooms.get(i);
            }
        }

        Room room = new Room(Room.DEFAULT_SIZE);
        rooms.add(room);
        return room;
    }

    public class Room
    {
        protected final static int DEFAULT_SIZE = 2;
        protected int id;
        protected int currentPlayerId;
        protected Player[] players;
        protected int playerCount = 0;
        protected int pointsForShooting;
        protected int pointsPerShot = 1;

        public Room(int size)
        {
            id = nextRoomId++;
            players = new Player[size];
            pointsForShooting = 1;
        }

        public boolean isFull()
        {
            return (playerCount == players.length);
        }

        /**
         * Adds a player to the room. Returns false if room is full, true
         * otherwise.
         * 
         * @param player The player to add.
         * @return If successful or not.
         */
        public boolean addPlayer(Player player)
        {
            for (int i = 0; i < players.length; ++i)
            {
                if (players[i] == null)
                {
                    players[i] = player;
                    playerCount++;
                    return true;
                }
            }

            return false;
        }

        public boolean nextPlayer()
        {
            int counter = 0;

            currentPlayerId++;
            while (players[currentPlayerId].isDead())
            {
                if (currentPlayerId >= players.length)
                    currentPlayerId = 0;

                currentPlayerId++;
                if (counter++ >= players.length -1)
                    return false;
            }

            return true;
        }

        /**
         * Removes a player from the room. Returns false if player is not in
         * room.
         * 
         * @param player The player to remove.
         * @return If successful or not.
         */
        public boolean removePlayer(Player player)
        {
            for (int i = 0; i < players.length; ++i)
            {
                if (players[i] == player)
                {
                    players[i] = null;
                    playerCount--;
                    return true;
                }
            }

            return false;
        }

        /**
         * Gets the id for this instance.
         *
         * @return The id.
         */
        public int getId()
        {
            return this.id;
        }

        /**
         * Gets the current Player for this instance.
         *
         * @return The current Player.
         */
        public Player getCurrentPlayer()
        {
            return players[currentPlayerId];
        }

        /**
         * Gets the currentPlayerId for this instance.
         *
         * @return The currentPlayerId.
         */
        public int getCurrentPlayerId()
        {
            return this.currentPlayerId;
        }

        /**
         * Gets the players for this instance.
         *
         * @return The players.
         */
        public Player[] getPlayers()
        {
            return this.players;
        }

        /**
         * Gets the players for this instance.
         *
         * @param index The index to get.
         * @return The players.
         */
        public Player getPlayers(int index)
        {
            return this.players[index];
        }

        /**
         * Gets the pointsForShooting for this instance.
         *
         * @return The pointsForShooting.
         */
        public int getPointsForShooting()
        {
            return this.pointsForShooting;
        }

        /**
         * Sets the pointsForShooting for this instance.
         *
         * @param pointsForShooting The pointsForShooting.
         */
        public void setPointsForShooting(int pointsForShooting)
        {
            this.pointsForShooting = pointsForShooting;
        }

        protected Player getPlayer(Client client)
        {
            for (int i = 0; i < players.length; ++i)
            {
                if (players[i].getClient().equals(client))
                    return players[i];
            }

            return null;
        }

        public State getState()
        {
            return new State(this);
        }

        public void increasePoints()
        {
            pointsForShooting += pointsPerShot;
        }

        public void resetPoints()
        {
            pointsForShooting = pointsPerShot;
        }

        public void finish()
        {
            for (int i = 0; i < players.length; ++i)
                players[i].setReady(false);

            sendToAll(new Content(Content.GAME_OVER));
        }

        public void start()
        {
            // TODO : randomize who starts
            // send state content to all
        }

        public void sendToAll(whatevergame.services.Content content)
        {
            for (int i = 0; i < players.length; ++i)
            {
                Player player = players[i];
                if (player != null)
                    send(player.getClient(), content);
            }
        }
    }
}
