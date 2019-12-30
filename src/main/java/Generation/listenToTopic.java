package Generation;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.sql.SQLOutput;

public class listenToTopic extends Behaviour {

    private boolean StopAuction = false;

    public listenToTopic(AID topic, double myBet, Data myData) {
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
        minStavka = myBet/2;
//        System.out.println(minStavka);
//        System.out.println(myAgent.getLocalName() +  " Жду минимальную ставку" );

            }

    @Override
    public void action() {


        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchTopic(myData.getTopic()),
                MessageTemplate.MatchProtocol("minBet"));

        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
//            System.out.println(getAgent().getLocalName() + " " + msg.getContent());
            if (Double.parseDouble(msg.getContent()) < myBet) {
                myBet = 0.7*myBet;
//                System.out.println(myAgent.getLocalName() +  " текущая ставка " + myBet);
                if (myBet > minStavka) {
                    ACLMessage msg1 = new ACLMessage(ACLMessage.PROPOSE);
                    msg1.setContent(Double.toString(myBet));
                    msg1.addReceiver(myData.getTopic());
                    myAgent.send(msg1);
                }
                else {
                    ACLMessage msg1 = new ACLMessage(ACLMessage.PROPOSE);
                    msg1.setContent("I'm out");
                    msg1.addReceiver(myData.getTopic());
                    myAgent.send(msg1);
                    myData.setFlag(true);
                }
            }
            else {
                ACLMessage msg1 = new ACLMessage(ACLMessage.PROPOSE);
                msg1.setContent("I'll pass");
                msg1.addReceiver(myData.getTopic());
                myAgent.send(msg1);
            }
        }
        else block();
    }

    @Override
    public boolean done() {
        return myData.isFlag();
    }

    @Override
    public int onEnd() {
        return 1;
    }
}
