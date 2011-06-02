package whatevergame.services.pewpew;

import java.io.Serializable;

import whatevergame.services.pewpew.server.logic.Player;

import whatevergame.services.pewpew.server.Server;

@SuppressWarnings("serial")
public class State implements Serializable
{
    public static final int NOT_PLAYING = 0;
    public static final int PLAYING     = 1;
    public static final int GAME_OVER   = 2;
    
    protected int state;
    protected int pointsForShooting;
    protected int currentPlayerId;
    protected StatePlayer[] players;

    /**
     * Returns the current state of the room
     * 
     * NOT_PLAYING = 0, PLAYING = 1, GAME_OVER = 2
     * 
     * @return current state of the room
     */
    public int getState() {
        return state;
    }

    public State(Server.Room room)
    {
        Player[] roomPlayers = room.getPlayers();

        players = new StatePlayer[roomPlayers.length];
        state = room.getCurrentState();
        pointsForShooting = room.getPointsForShooting();
        currentPlayerId = room.getCurrentPlayerId();

        for (int i = 0; i < roomPlayers.length; ++i)
        {
            if (roomPlayers[i] != null)
            {
                players[i] = new StatePlayer(roomPlayers[i].getClient().getUser().getUsername(), roomPlayers[i].isDead(), roomPlayers[i].isReady());
            }
        }
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
    public StatePlayer[] getPlayers()
    {
        return this.players;
    }

    /**
     * Gets a player for this instance.
     *
     * @param index The index to get.
     * @return The players.
     */
    public StatePlayer getPlayer(int index)
    {
        return this.players[index];
    }

    public class StatePlayer implements Serializable
    {
        protected String name;
        protected boolean dead;
        protected boolean ready;

        public StatePlayer(String name, boolean dead, boolean ready)
        {
            this.name = name;
            this.dead = dead;
            this.ready = ready;
        }

        /**
         * Gets the name for this instance.
         *
         * @return The name.
         */
        public String getName()
        {
            return this.name;
        }

        /**
         * Determines if this instance is dead.
         *
         * @return The dead.
         */
        public boolean isDead()
        {
            return this.dead;
        }

        /**
         * Determines if this instance is ready.
         *
         * @return The ready.
         */
        public boolean isReady()
        {
            return this.ready;
        }

        /**
         * Sets whether or not this instance is ready.
         *
         * @param ready The ready.
         */
        public void setReady(boolean ready)
        {
            this.ready = ready;
        }
    }
}
