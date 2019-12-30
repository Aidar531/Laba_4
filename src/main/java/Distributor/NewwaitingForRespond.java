package Distributor;

import Consumer.Energy;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.Map;

public class NewwaitingForRespond extends Behaviour {
    public DataStore dataStore;

    public AID topic;

    public ACLMessage msg;
    public NewwaitingForRespond(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public int numberOfParticipants;
    public int numberOfGenerators;
    private ArrayList<Double> listOfPrices = new ArrayList<>();
    public ArrayList<String> listOfParticipants = new ArrayList<>();
    private boolean StopAuction=false;
    public double min = Double.MAX_VALUE;

    @Override
    public void onStart() {
        numberOfParticipants = dataStore.getNumberOfGenerators();
        numberOfGenerators = numberOfParticipants;
        topic = dataStore.topic;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchTopic(dataStore.getTopic()),
                MessageTemplate.MatchProtocol("bet"));

        msg = getAgent().receive(mt);
        if (msg != null) {
            System.out.println(msg.getContent());
            dataStore.listOfMembers.put(msg.getSender().getLocalName(),msg.getContent().split(",")[0]);
//            System.out.println("Писльмо пришло от:"+ msg.getSender().getLocalName() + " " + msg.getContent());
            if (msg.getContent().split(",")[1].equals("I'm out")) {
                numberOfParticipants = numberOfParticipants - 1 ;
            }
            if (numberOfParticipants == 1 && dataStore.listOfMembers.size() == numberOfGenerators) {
                StopAuction = true;
            }
        }
        else block();

    }
    @Override
    public int onEnd() {
        System.out.println("Я все");
        StopAuction = false;
        min = Double.MAX_VALUE;
        numberOfParticipants = numberOfGenerators;
        for (Map.Entry<String,String> X: dataStore.listOfMembers.entrySet()) {
            if (Double.parseDouble(X.getValue().split(",")[0]) < min) {
                min = Double.parseDouble(X.getValue().split(",")[0]);
                dataStore.setWinner(X.getKey());
            }
        }
        ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
        msg1.addReceiver(dataStore.getTopic());
        msg1.setProtocol("Winner");
        msg1.setContent(dataStore.getWinner());
        myAgent.send(msg1);
        dataStore.listOfMembers.clear();
        return super.onEnd();
    }

    @Override
    public boolean done() {
        return StopAuction;
    }

}
