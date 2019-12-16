package Distributor;

import Consumer.Time_sync;
import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class waitingFromConsumer extends Behaviour {

    public boolean flag = false;


    @Override
    public void action() {
        MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage msg = myAgent.receive(template);
        if (msg != null) {
            System.out.println("Начал создание топика");
            flag = true;
        } else block();

    }
    @Override
    public boolean done() {
        return flag;
    }

}
