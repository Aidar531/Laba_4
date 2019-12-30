package Consumer;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class startBuying extends OneShotBehaviour {
    public Energy energy;
    public consumerCfg cfg;

    public startBuying(Energy energy, consumerCfg cfg) {
        this.energy = energy;
        this.cfg = cfg;
    }

    @Override
    public void action() {

           myAgent.addBehaviour(new OneEnergyRequest(cfg,energy));

        }
    @Override
    public int onEnd() {
//
        myAgent.addBehaviour(new waitingForEnergy(energy,cfg));
        return 1;
    }
}
