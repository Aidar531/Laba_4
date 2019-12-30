package Distributor;

import Consumer.Time_sync;
import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class TopicCreation extends OneShotBehaviour {

    public TopicCreation(DataStore dataStore) {
        this.dataStore = dataStore;
    }

   public DataStore dataStore;

    public int numberOfParticipants;

    @Override
    public void action() {

        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("Generation");
        dfd.addServices(sd);

        DFAgentDescription[] foundAgents = new DFAgentDescription[0];
        try {
            foundAgents = DFService.search(myAgent,dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        numberOfParticipants = foundAgents.length;
        dataStore.setNumberOfGenerators(numberOfParticipants);;
//        System.out.println(numberOfParticipants);
//        System.out.println("Количество участников"+(foundAgents.length-1));

        String nameOfTopic = System.currentTimeMillis() +" "+ getAgent().getLocalName();
//        AID bet = createTopic(nameOfTopic);
//        System.out.println("Название аукциона: " + nameOfTopic);
        AID topic = subscribeTopic(nameOfTopic);
        dataStore.setTopic(topic);

        for (DFAgentDescription foundAgent : foundAgents) {
            if (!foundAgent.getName().getLocalName().equals(getAgent().getLocalName())) {
//                System.out.println(foundAgent.getName().getLocalName());
//                System.out.println("Отправил приглашение -> " + foundAgent.getName().getLocalName());
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.setProtocol("topic");
                msg.setContent(nameOfTopic);
                msg.addReceiver(foundAgent.getName());
                myAgent.send(msg);
            }
        }
    }

    @Override
    public int onEnd() {
        return super.onEnd();
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
