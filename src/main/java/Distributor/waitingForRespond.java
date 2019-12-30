package Distributor;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;

public class waitingForRespond extends Behaviour {
    public DataStore dataStore;

    public AID topic;

    public ACLMessage msg;
    public waitingForRespond(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public int numberOfParticipants;
    public int numberOfGenerators;
    private ArrayList<Double> listOfPrices = new ArrayList<>();
    public ArrayList<String> listOfParticipants = new ArrayList<>();
    private boolean StopAuction=false;
    public double min = Double.MAX_VALUE;

    @Override
    public void onStart() {
        numberOfParticipants = dataStore.getNumberOfGenerators();
        numberOfGenerators = numberOfParticipants;
        topic = dataStore.topic;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchTopic(dataStore.getTopic()),
                MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));

        msg = getAgent().receive(mt);
        if (msg != null) {
//            System.out.println("Писльмо пришло от:"+ msg.getSender().getLocalName() + " " + msg.getContent());
            if (msg.getContent().equals("I'm out")) {
                numberOfParticipants = numberOfParticipants - 1 ;
                dataStore.setNumberOfGenerators(numberOfParticipants);
            }
            // Проверка сколько участников осталось, если больше одного, то ведем расчет минимумма
            if (dataStore.getNumberOfGenerators() > 1) {
                // Формирование массива значений ставок и массива из участников
                if (!msg.getContent().equals("I'm out")) {
                    if (!msg.getContent().equals("I'll pass")) {
                        listOfPrices.add(Double.parseDouble(msg.getContent()));
                    } else  {
                        listOfPrices.add(100000.0);
                    }
                    listOfParticipants.add(msg.getSender().getLocalName());
                }
                //Проверка если массив из необходимого количества участников наполненн
                if (listOfPrices.size() == dataStore.getNumberOfGenerators()) {
                    for (Double i : listOfPrices) {
                        if (i < min) {
                            min = i;
                            dataStore.setWinner(listOfParticipants.get(listOfPrices.indexOf(min)));
                        }
                    }
                    //Отправка предварительного миниумму и победителя
                    System.out.println("Предварительный минимумм " + min);
                    ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
                    msg1.setContent(Double.toString(min));
                    msg1.setProtocol("minBet");
                    msg1.addReceiver(dataStore.getTopic());
                    myAgent.send(msg1);

                    System.out.println("Предварительный победитель " + dataStore.getWinner());
                    msg1.setProtocol("Winner");
                    msg1.setContent(dataStore.getWinner());
                    myAgent.send(msg1);
                    listOfPrices.clear();
                    listOfParticipants.clear();
                }
            }
            else {
                StopAuction = true;
            }
        }
        else block();

    }
    @Override
    public int onEnd() {
        StopAuction = false;
        listOfPrices.clear();
        listOfParticipants.clear();
        min = Double.MAX_VALUE;
        numberOfParticipants = numberOfGenerators;
        ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
        msg1.addReceiver(dataStore.getTopic());
        msg1.setProtocol("Winner");
        msg1.setContent("Stop");
        myAgent.send(msg1);
        return super.onEnd();
    }

    @Override
    public boolean done() {
        return StopAuction;
    }

}
