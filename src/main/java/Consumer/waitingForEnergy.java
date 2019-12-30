package Consumer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class waitingForEnergy extends Behaviour {
    public boolean flag = false;
    ACLMessage msg;
    public waitingForEnergy(Energy energy, consumerCfg cfg) {
        this.energy = energy;
        this.cfg = cfg;
    }

    public Energy energy;
    public consumerCfg cfg;
    @Override
    public void action() {
            MessageTemplate template = MessageTemplate.and(
                    MessageTemplate.MatchPerformative(ACLMessage.CONFIRM),
                            MessageTemplate.MatchProtocol("Done"));
            msg = myAgent.receive(template);
            if (msg != null) {
                int energyToBuyLeft = energy.getEnergy();
                energy.setEnergy(energyToBuyLeft - 1);
                System.out.println("Энергию получил от " + msg.getContent() + " " + myAgent.getLocalName());
                if (energy.getEnergy() >= 1) {
                    myAgent.addBehaviour(new OneEnergyRequest(cfg, energy));
                } else flag = true;
            } else block();
//        System.out.println("пошел процесс");
    }

    @Override
    public int onEnd() {
        msg = null;
        energy.setEnergy(0);
        myAgent.addBehaviour(new everyHourStart(myAgent, Time_sync.calcMillisTillNextHour(),cfg,energy));
        return 1;
    }

    @Override
    public boolean done() {
//        System.out.println(Time_sync.calcMillisTillNextHour());
        return flag;
    }
}
