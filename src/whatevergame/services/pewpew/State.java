package whatevergame.services.pewpew;

import java.io.Serializable;

import whatevergame.services.pewpew.server.logic.Player;

import whatevergame.services.pewpew.server.Server;

@SuppressWarnings("serial")
public class State implements Serializable
{
    protected int pointsForShooting;
    protected int currentPlayer;
    protected StatePlayer[] players;

    public State(Server.Room room)
    {
        Player[] players = room.getPlayers();

        StatePlayer[] statePlayers = new StatePlayer[players.length];

        pointsForShooting = room.getPointsForShooting();
        currentPlayer = room.getCurrentPlayerId();

        for (int i = 0; i < players.length; ++i)
        {
            if (players[i] != null)
            {
                statePlayers[i] = new StatePlayer(players[i].getClient().getUser().getUsername(), players[i].isDead(), players[i].isReady());
            }
        }
    }

    public boolean isAnyoneAlive()
    {
        for (int i = 0; i < players.length; ++i)
        {
            if (!players[i].isDead())
                return true;
        }

        return false;
    }

    protected class StatePlayer
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
