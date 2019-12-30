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

    public DataStore dataStore;
    private ACLMessage msg = null;

    public waitingFromConsumer(DataStore dataStore) {
        this.dataStore = dataStore;
    }

//    public boolean flag = false;


    @Override
    public void action() {
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                MessageTemplate.MatchProtocol("start"));
        msg = myAgent.receive(template);
        if (msg != null) {
//            System.out.println("стартанул "+getAgent().getLocalName());
            dataStore.setConsumer(msg.getSender().getLocalName());
//            flag = true;
            msg = null;
        } else block();
    }

//    @Override
//    public int onEnd() {
//        msg = null;
////        flag = false;
//    return 1;
//    }

    @Override
    public boolean done() {
        return msg != null;
    }

}
