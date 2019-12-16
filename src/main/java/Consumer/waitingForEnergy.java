package Consumer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class waitingForEnergy extends Behaviour {
    public boolean flag = false;

    public waitingForEnergy(int energyToBuyLeft) {
        this.energyToBuyLeft = energyToBuyLeft;
    }

    public int energyToBuyLeft;
    @Override
    public void action() {
        if (energyToBuyLeft >= 0) {
            MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.CONFIRM);
            ACLMessage msg = myAgent.receive(template);
            if (msg != null) {
                System.out.println("Энергию получил" + msg.getContent() + myAgent.getLocalName());
                flag = true;
            } else block();
        }
        else flag = true;
    }
    @Override
    public boolean done() {
        myAgent.addBehaviour(new everyHourStart(myAgent, Time_sync.calcMillisTillNextHour()));
        return flag;
    }
}
