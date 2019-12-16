package Consumer;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

import java.util.Date;

public class everyHourStart extends WakerBehaviour {


    public everyHourStart(Agent a, long timeout) {
        super(a, timeout);
    }

    @Override
    protected void onWake() {
        consumerCfg cfg = WorkWithXml.unMarshalAny(consumerCfg.class, myAgent.getLocalName() + ".xml");
        int index = (int) Time_sync.getCurrentHour();
        myAgent.addBehaviour(new startBuying((int) cfg.getGraphs().get(index).getPower(),cfg));
    }
}
