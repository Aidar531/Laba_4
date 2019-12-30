package Generation;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.concurrent.atomic.AtomicInteger;

public class NewwaitingForEnding extends Behaviour {

    public Data myPrice;
    public AtomicInteger battery;
    AID topic;
    public Behaviour beh;
    public Data myData;

    public NewwaitingForEnding(Data myPrice, AtomicInteger battery, AID topic, Behaviour beh, Data myData) {
        this.myPrice = myPrice;
        this.battery = battery;
        this.topic = topic;
        this.beh = beh;
        this.myData = myData;
    }

    public String winner;
    private boolean Stop = false;

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchTopic(topic),
                MessageTemplate.MatchProtocol("Stop"));
        ACLMessage msg = myAgent.receive(mt);

        if (msg!=null) {
            if (msg.getContent().equals(getAgent().getLocalName())) {
                myData.setFlag(true);
                Stop = true;
                    if ((battery.get() > 1)) {
                        battery.addAndGet(-1);
                        ACLMessage reply = new ACLMessage(ACLMessage.CONFIRM);
                        reply.setProtocol("Energy");
                        reply.addReceiver(topic);
                        myAgent.send(reply);
                    } else {
                        ACLMessage reply = new ACLMessage(ACLMessage.REFUSE);
                        reply.setProtocol("Energy");
                        reply.addReceiver(topic);
                        myAgent.send(reply);
                    }
            }
        }
        else block();
    }

    @Override
    public int onEnd() {
        myAgent.addBehaviour(new waitingForRequest(battery,myPrice));
        return super.onEnd();
    }

    @Override
    public boolean done() {
        return Stop;
    }
}
