package Consumer;

import jade.core.Agent;

public class ConsumerAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new everyHourStart(this,0));
    }
}
