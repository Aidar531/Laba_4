package Consumer;

import jade.core.Agent;

public class ConsumerAgent extends Agent {
    Energy energy = new Energy();
    @Override
    protected void setup() {
        consumerCfg cfg = WorkWithXml.unMarshalAny(consumerCfg.class, getLocalName() + ".xml");
        energy.setInstalledPower(cfg.getInstalledPower());
        addBehaviour(new everyHourStart(this,0,cfg,energy));
    }
}
