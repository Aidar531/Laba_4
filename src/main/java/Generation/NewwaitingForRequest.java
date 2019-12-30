package Generation;

import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.concurrent.atomic.AtomicInteger;

public class NewwaitingForRequest extends Behaviour {
    public AtomicInteger battery;
    public Data myData;

    public NewwaitingForRequest(AtomicInteger battery, Data myData) {
        this.battery = battery;
        this.myData = myData;
    }

    AID topic;
    public boolean stop=false;

    @Override
    public void onStart() {
        myData.setFlag(false);
        switch (getAgent().getLocalName()) {
            case "Wind":
                myData.setMyPrice(getMyPrice(10,5.1));
                break;
            case "Solar":
                myData.setMyPrice(getMyPrice(8,4));
                break;
            case "Coal":
                myData.setMyPrice(getMyPrice(20,9));
                break;
        }
        System.out.println(getAgent().getLocalName() + " Моя текущая цена " + myData.getMyPrice());
        System.out.println(getAgent().getLocalName() + " Состояние батареи " + battery.get());
    }

    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                MessageTemplate.MatchProtocol("topic"));
        ACLMessage msg = myAgent.receive(mt);

        if (msg != null) {
//            System.out.println("Получил след. топик " + msg .getContent()+ " " + getAgent().getLocalName());
//            System.out.println("Отправил ставкку " + myAgent.getLocalName() + " " +myPrice*2);
            topic = subscribeTopic(msg.getContent());
            myData.setTopic(topic);
//            System.out.println(topic.getLocalName());
            ACLMessage msg1 = new ACLMessage(ACLMessage.PROPOSE);
            msg1.setProtocol("bet");
            msg1.setContent(2*myData.getMyPrice() +","+ "I'm in");
            msg1.setProtocol("myBet");
            msg1.addReceiver(myData.getTopic());
            myAgent.send(msg1);
            Behaviour beh = new NewlistenToTopic(myData.getTopic(),2*myData.getMyPrice(),myData);
            myAgent.addBehaviour((new NewwaitingForEnding(myData,battery,myData.getTopic(),beh,myData)));
            System.out.println("I'm in" + " -> " + myAgent.getLocalName());
            myAgent.addBehaviour(beh);
            stop = true;
        }
        else block();
    }

    @Override
    public boolean done() {
        return stop;
    }

    private AID subscribeTopic(String topicName) {
        TopicManagementHelper topicHelper;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper)
                    myAgent.getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(topicName);
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }

    private double getMyPrice(int A1, double C) {
        try {
            myData.setMyPrice(A1/((double) battery.get())+C);
        }
        catch (ArithmeticException e) {
            System.out.println("Ошибка");
            myData.setMyPrice(100);
        }
        return myData.getMyPrice();
    }
}
