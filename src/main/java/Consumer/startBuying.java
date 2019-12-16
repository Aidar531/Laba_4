package Consumer;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class startBuying extends OneShotBehaviour {
    public int energyToBuyLeft = 0;
    public consumerCfg cfg;
    public startBuying(int energyToBuyLeft, consumerCfg cfg) {
        this.energyToBuyLeft = energyToBuyLeft;
        this.cfg = cfg;
    }

    @Override
    public void action() {

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID(cfg.getDistributorName(), false));
        msg.setProtocol("start");
        msg.setContent("1");
        System.out.println("Начал процесс закупки по одному Вт " +
                energyToBuyLeft +
                " Вт "+ getAgent().getLocalName());
        energyToBuyLeft = energyToBuyLeft - 1;
        }
    @Override
    public int onEnd() {
        myAgent.addBehaviour(new waitingForEnergy(energyToBuyLeft));
        return 1;
    }
}
