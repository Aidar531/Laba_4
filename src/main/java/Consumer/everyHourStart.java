package Consumer;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;



public class everyHourStart extends WakerBehaviour {
    consumerCfg cfg;
    public Energy energy;

    public everyHourStart(Agent a, long timeout, consumerCfg cfg, Energy energy) {
        super(a, timeout);
        this.cfg = cfg;
        this.energy = energy;
    }

    @Override
    protected void onWake() {
        int index = (int) Time_sync.getCurrentHour();
        energy.setEnergy((int) (energy.getInstalledPower()*cfg.getGraphs().get(index).getPower()));
        myAgent.addBehaviour(new startBuying(energy,cfg));
        System.out.println("Начлао нового часа" + Time_sync.getCurrentHour());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
