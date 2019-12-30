package Generation;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class NewlistenToTopic extends Behaviour {
    private boolean StopAuction = false;

    public NewlistenToTopic(AID topic, double myBet, Data myData) {
        this.topic = topic;
        this.myBet = myBet;
        this.myData = myData;
    }

    AID topic;
    public double myBet;
    public Data myData;
    public double minStavka;

    @Override
    public void onStart() {
        minStavka = myBet / 2;
    }

    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchTopic(myData.getTopic()),
                MessageTemplate.MatchProtocol("bet"));

        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            if (!msg.getSender().getLocalName().equals(getAgent().getLocalName())) {
                System.out.println(getAgent().getLocalName() + " " + msg.getContent());
                if (Double.parseDouble(msg.getContent().split(",")[0]) < myBet) {
                    myBet = 0.7 * myBet;
                    System.out.println(myAgent.getLocalName() + " текущая ставка " + myBet);
                    if (myBet > minStavka) {
                        ACLMessage msg1 = new ACLMessage(ACLMessage.PROPOSE);
                        msg.setProtocol("bet");
                        msg1.setContent(myBet + "," + "I'm in");
                        msg1.addReceiver(myData.getTopic());
                        myAgent.send(msg1);
                    } else {
                        System.out.println("Я отправил I'm out" + getAgent().getLocalName());
                        ACLMessage msg1 = new ACLMessage(ACLMessage.PROPOSE);
                        msg.setProtocol("bet");
                        msg1.setContent(myBet + "," + "I'm out");
                        msg1.addReceiver(myData.getTopic());
                        myAgent.send(msg1);
                        myData.setFlag(true);
                    }
                } else {
                    System.out.println("Я отправил I'll pass" + getAgent().getLocalName());
                    ACLMessage msg1 = new ACLMessage(ACLMessage.PROPOSE);
                    msg1.setContent(myBet + "," + "I'll pass");
                    msg.setProtocol("bet");
                    msg1.addReceiver(myData.getTopic());
                    myAgent.send(msg1);
                }
            }
        } else block();
    }

    @Override
    public boolean done() {
        return myData.isFlag();
    }

    @Override
    public int onEnd() {
        System.out.println(" Я закончил торги " +getAgent().getLocalName());
        return 1;
    }
}
