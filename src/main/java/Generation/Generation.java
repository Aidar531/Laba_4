package Generation;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Generation extends Agent {

    public AtomicInteger battery = new AtomicInteger(10);
    public Data myPrice = new Data(0);
    public int batteryLimit;

    @Override
    protected void setup() {
        ScheduledExecutorService first = Executors.newScheduledThreadPool(2);
        Runnable thread1 = () -> {
            int t = (int) Time_sync.getCurrentHour();
            if (battery.get() > batteryLimit) {
//                System.out.println("прибавляю");
                switch (getLocalName()) {
                    case "Wind":
                        batteryLimit = 15;
                        battery.addAndGet((int) Math.round(10*(1 / (5 * Math.sqrt(2 * Math.PI))) * Math.pow(Math.E, -(t - 5) / (72.0))) +1);
                        System.out.println();
                        break;
                    case "Solar":
                        batteryLimit = 15;
                        if (t > 5 && t < 19) {
                            battery.addAndGet((int) (0.0005 * (-0.0002 * Math.pow(t, 6)
                                    + 0.0129 * Math.pow(t, 5) - 0.3359 * Math.pow(t, 4)
                                    + 3.7926 * Math.pow(t, 3) - 13.057 * Math.pow(t, 2)
                                    + 25.199 * t) + 5));
                        }
                        else battery.addAndGet(0);
                            break;
                    case "Coal":
                        batteryLimit = 25;
                        battery.addAndGet(5);
                        break;
                }
            } else battery.set(batteryLimit);
            System.out.println("=========================");
            System.out.println(getLocalName() + " Моя текущая цена " + myPrice.getMyPrice());
            System.out.println(getLocalName() + " Состояние батареи " + battery.get());
            System.out.println("==========================");
        };
        first.scheduleAtFixedRate(thread1, 0, 10, TimeUnit.SECONDS);
        registerAgent();
        addBehaviour(new waitingForRequest(battery,myPrice));
    }

    private void registerAgent() {

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Generation");
        sd.setName("Generation" + getLocalName());
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }


}