package Distributor;

import jade.core.behaviours.FSMBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;

public class FSMBeh_Distro extends FSMBehaviour {
    public  DFAgentDescription[] Generators;

    public FSMBeh_Distro(DFAgentDescription[] generators) {
        Generators = generators;
    }

    @Override
    public void onStart() {
//        первое поведение, простой бехавиоур, ожидающий ответа от генерации
        registerFirstState(new waitingFromConsumer(),"1");
        registerState(new TopicInit(Generators),"2");
        registerState(new waitingForRespond(),"3");
//        поведение в зависимости от ответа генерации
        registerState(new coupleMinWaiting(),"3.1");
        registerState(new SigningRequest(),"3.2");
        registerLastState(new DoneOfDistro(),"4");

        registerDefaultTransition("1","2");
        registerDefaultTransition("2","3");
        registerTransition("3","3.1",0);
        registerTransition("3","3.2",1);
        registerTransition("3.2","3.1",0);
        registerTransition("3.2","4",1);
        registerDefaultTransition("4","1");
    }
}
