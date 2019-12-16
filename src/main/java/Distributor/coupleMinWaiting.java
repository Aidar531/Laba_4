package Distributor;

import jade.core.behaviours.OneShotBehaviour;

public class coupleMinWaiting extends OneShotBehaviour {
    @Override
    public void action() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
