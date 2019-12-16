package Generation;

import Consumer.Time_sync;
import jade.core.Agent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WindGeneration extends Agent {
    @Override
    protected void setup() {
        ScheduledExecutorService first = Executors.newScheduledThreadPool(2);
        Runnable thread1 = () -> {
//        выработка энергии
        };
        first.scheduleAtFixedRate(thread1,1,10, TimeUnit.SECONDS);

    }



}

