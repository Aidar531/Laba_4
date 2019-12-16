package Distributor;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class waitingForRespond extends Behaviour {
    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchTopic(topic),
                MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));

        ACLMessage msg = getAgent().receive(mt);
        if (msg!=null){

        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
