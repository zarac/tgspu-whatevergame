package whatevergame.services.pewpew.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import whatevergame.server.Client;

import whatevergame.services.pewpew.State;
import whatevergame.services.pewpew.StateContent;

import whatevergame.services.ServerService;

import whatevergame.services.pewpew.Content;
import whatevergame.services.pewpew.ContentServer;
import whatevergame.services.pewpew.server.logic.Gun;
import whatevergame.services.pewpew.server.logic.Player;

import whatevergame.services.Service;

public class Server extends ServerService
{
    protected static int nextRoomId = 1;
    protected LinkedList<Room> rooms;

    private int counter = 0;
    private int minlimit = 2;
    private Gun gun;
    private ArrayList<Player> players;

    protected Random random;
    
    public Server(int id, whatevergame.server.Server server)
    {
        super(id, server);
        gun = new Gun(3);
        players = new ArrayList<Player>();
        rooms = new LinkedList<Room>();
        random = new Random();
    }

    /**
     * {@inheritDoc}
     * @see ServerService#removeClient(Client)
     */
    public void removeClient(Client client)
    {
        super.removeClient(client);

        for (int i = 0; i < rooms.size(); ++i)
        {
            Player player = rooms.get(i).getPlayer(client);
            if (player != null)
                rooms.get(i).removePlayer(player);
        }
    }

    public void receive(Client client, whatevergame.services.Content p_content)
    {
        whatevergame.services.score.server.Server score = (whatevergame.services.score.server.Server)getService(Service.SCORE);

        Content content = (Content)p_content;
        logger.debug("Received content '" + content + "' from client '" + client + "'.");
            
        if (content.getCommand() == Content.PEWPEW_INIT)
        {
            logger.debug("command=init");
            Room room = getAvailableRoom();
            room.addPlayer(new Player(gun, client));
            if (room.isFull())
                room.start();
            room.sendToAll(new StateContent(room.getState()));
        }
        else if (content.getCommand() == Content.PEWPEW_SPIN)
        {
            logger.warning("spin");

            Room room = rooms.get(content.getRoomId());
            Player player = room.getPlayer(client);
            if (player.equals(room.getCurrentPlayer()))
            {
                player.spinChamber();
                room.resetPoints();
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
                    logger.debug("BOOM");
                    player.setToDead();
                    room.resetPoints();
                }
                else
                {
                    logger.debug("no boom, phew");
                    score.add(client.getUser(), room.getPointsForShooting());
                    room.increasePoints();
                }

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
            removeClient(client);
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
        protected final static int NOT_PLAYING = -1;
        protected final static int DEFAULT_SIZE = 2;

        protected int id;
        protected int currentPlayerId = NOT_PLAYING;
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
                    send(player.getClient(), new Content(Content.PLAYER_ID, id, i));
                    return true;
                }
            }

            return false;
        }

        // TODO : clean up
        public boolean nextPlayer()
        {
            int counter = 0;
            boolean dead;

            currentPlayerId++;
            if (currentPlayerId >= players.length)
                currentPlayerId = 0;
            Player player = players[currentPlayerId];
            if (player == null)
                dead = true;
            else
                dead = player.isDead();

            while (dead)
            {
                // reached end?
                currentPlayerId++;
                if (currentPlayerId >= players.length)
                    currentPlayerId = 0;

                // checked all?
                if (counter++ >= players.length -1)
                    return false;

                player = players[currentPlayerId];
                if (player == null)
                    dead = true;
                else
                    dead = player.isDead();
            }

            return true;
        }

        /**
         * Removes a player from the room. Returns false if player is not in
         * room. When last player leaves, remove the room itself.
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
                    if (playerCount == 0)
                        rooms.remove(this);
                    // TODO : ? What happens if a game is active!?
                    // TODO : ? What happens if someone is left all alone
                    sendToAll(new StateContent(getState()));
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
            if (currentPlayerId == NOT_PLAYING)
                return null;

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
         * Gets a players for this instance.
         *
         * @param index The index to get.
         * @return The players.
         */
        public Player getPlayer(int index)
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
                if (players[i] != null && players[i].getClient().equals(client))
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
            currentPlayerId = NOT_PLAYING;
            for (int i = 0; i < players.length; ++i)
                players[i].setReady(false);

            sendToAll(new Content(Content.GAME_OVER));
        }

        public void start()
        {
            int starter = random.nextInt(players.length);
            logger.debug("starter=" + starter);
            currentPlayerId = starter;
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
