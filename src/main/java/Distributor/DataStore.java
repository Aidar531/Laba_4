package Distributor;

import jade.core.AID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DataStore {
    public AID topic;
    public String consumer=null;
    public String winner=null;
    public int numberOfGenerators = 3;

    public Map<String, String> getListOfMembers() {
        return listOfMembers;
    }

    public void setListOfMembers(Map<String, String> listOfMembers) {
        this.listOfMembers = listOfMembers;
    }

    public Map<String,String> listOfMembers = new Map<String, String>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object o) {
            return false;
        }

        @Override
        public boolean containsValue(Object o) {
            return false;
        }

        @Override
        public String get(Object o) {
            return null;
        }

        @Override
        public String put(String s, String s2) {
            return null;
        }

        @Override
        public String remove(Object o) {
            return null;
        }

        @Override
        public void putAll(Map<? extends String, ? extends String> map) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<String> keySet() {
            return null;
        }

        @Override
        public Collection<String> values() {
            return null;
        }

        @Override
        public Set<Entry<String, String>> entrySet() {
            return null;
        }
    };

    public int getNumberOfGenerators() {
        return numberOfGenerators;
    }

    public void setNumberOfGenerators(int numberOfGenerators) {
        this.numberOfGenerators = numberOfGenerators;
    }

    public AID getTopic() {
        return topic;
    }

    public void setTopic(AID topic) {
        this.topic = topic;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
