package Distributor;

import Consumer.Time_sync;
import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class TopicInit extends OneShotBehaviour {
    public  DFAgentDescription[] Generators;

    public TopicInit(DFAgentDescription[] generators) {
        Generators = generators;
    }
    @Override
    public void action() {
        String nameOfTopic = Time_sync.getCurrentHour()+ getAgent().getLocalName();
        AID bet = createTopic(nameOfTopic);
        System.out.println("Название аукциона: " + nameOfTopic);
        AID topic = subscribeTopic(nameOfTopic);

        for (DFAgentDescription foundAgent : Generators) {
            if (!foundAgent.getName().getLocalName().equals(getAgent().getLocalName())) {
//                System.out.println("Отправил приглашение -> " + foundAgent.getName().getLocalName());
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.setContent(nameOfTopic);
                msg.addReceiver(foundAgent.getName());
                myAgent.send(msg);
            }
        }
    }
    private AID createTopic(String topicName) {
        TopicManagementHelper topicHelper;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper) myAgent.getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(topicName);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
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
}
