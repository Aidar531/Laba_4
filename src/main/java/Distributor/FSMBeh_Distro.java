package Distributor;

import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;

public class FSMBeh_Distro extends FSMBehaviour {

    public DataStore dataStore = new DataStore();
    @Override
    public void onStart() {

//        первое поведение, простой бехавиоур, ожидающий ответа от генерации
        registerFirstState(new waitingFromConsumer(dataStore),"1");
        registerState(new TopicCreation(dataStore),"additional");
        registerState(new waitingForRespond(dataStore),"2");
        registerState(new waitingForEnergy(dataStore),"3");
//        поведение в зависимости от ответа генерации
        registerState(new coupleMinWaiting(),"3.1");
        registerState(new DoneOfDistro(dataStore),"3.2");

        registerDefaultTransition("1","additional");
        registerDefaultTransition("additional","2");
        registerDefaultTransition("2","3");
        registerTransition("3","3.1",0);
        registerTransition("3","3.2",1);
        registerDefaultTransition("3.1","2");
//        registerTransition("3.2","1", 1);

        registerDefaultTransition("3.2","1");
    }

}
