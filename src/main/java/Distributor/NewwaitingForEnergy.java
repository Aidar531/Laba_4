package Distributor;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class NewwaitingForEnergy extends Behaviour {
    public boolean flag = false;
    public DataStore dataStore;

    public NewwaitingForEnergy(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public boolean gotEnergy;

    @Override
    public void action() {
        flag = false;
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchTopic(dataStore.getTopic()),
                MessageTemplate.MatchProtocol("Energy"));
        ACLMessage msg = getAgent().receive(mt);
        if (msg != null) {
            if (msg.getPerformative() == ACLMessage.CONFIRM) {
                flag = true;
                gotEnergy = true;
            }
            if (msg.getPerformative() == ACLMessage.REFUSE) {
                flag = true;
                gotEnergy = false;
            }
        }
    }

    @Override
    public int onEnd() {
        if (gotEnergy) {
            return 1;
        }
        else return  0;
    }

    @Override
    public boolean done() {
        return flag;
    }
}

