package Distributor;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class DoneOfDistro extends OneShotBehaviour {
    public DataStore dataStore;

    public DoneOfDistro(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.CONFIRM);
        msg.setProtocol("Done");
        msg.addReceiver(new AID(dataStore.getConsumer(), false));
        msg.setContent(dataStore.getWinner());
        myAgent.send(msg);
    }

    @Override
    public int onEnd() {
//        System.out.println("Запастил по новой");
        return 1;
    }
}
