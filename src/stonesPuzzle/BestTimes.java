package stonesPuzzle;

import java.io.Serializable;
import java.util.*;

public class BestTimes implements Iterable<BestTimes.PlayerTime>, Serializable {

    private List<PlayerTime> playerTimes = new ArrayList<>();

    public Iterator<PlayerTime> iterator() {
        return playerTimes.iterator();
    }

    public void addPlayerTime(String name, int time) {
        PlayerTime player = new PlayerTime(name, time);
        playerTimes.add(player);
        Collections.sort(playerTimes);
        printBesetTimes();
    }

    public void printBesetTimes() {
        Formatter f = new Formatter();
        for (int i = 0; i < playerTimes.size(); i++) {
            System.out.println((i+1)+". "+playerTimes.get(i).getTime()+" seconds - "+playerTimes.get(i).getName());
        }
    }

    public static class PlayerTime implements Comparable<PlayerTime>, Serializable {

        private final String name;
        private final int time;

        public PlayerTime(String name, int time) {
            this.name = name;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public int getTime() {
            return time;
        }

        @Override
        public int compareTo(PlayerTime o) {
            return time - o.getTime();
        }

        public boolean equals(Object o) {
            if (o instanceof BestTimes) {
                PlayerTime playerTime = (PlayerTime) o;
                return name == playerTime.getName() && time == playerTime.getTime();
            }
            return false;
        }

        public int hashCode() {
            int hash = 0;
            int mult = 1;
            for (int i = 0; i < name.length(); i++) {
                hash += (name.toLowerCase().charAt(i)-97) * mult * time;
                mult *= 10;
            }
            return hash;
        }
    }
}
