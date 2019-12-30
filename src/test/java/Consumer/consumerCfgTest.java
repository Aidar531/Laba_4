package Consumer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class consumerCfgTest {
    private List<graph> ListOfGraphs = new ArrayList<graph>();;

    public static long initTime = System.currentTimeMillis();
    public static long hourDuration = 12000;

    @Test
    public void cfgTest(){

        graph G1 = new graph(1,12),
                G2 = new graph(2,13),
                G3 = new graph(3,5);
        ListOfGraphs.add(G1);
        ListOfGraphs.add(G2);
        ListOfGraphs.add(G3);

        consumerCfg cfg = new consumerCfg();
        cfg.setConsumerName("Hospital");
        cfg.setDistributorName("Distro1");
        cfg.setInstalledPower(10);
        cfg.setGraphs(ListOfGraphs);
        WorkWithXml.marshalAny(consumerCfg.class ,cfg,"School.xml");

        consumerCfg Cfg = WorkWithXml.unMarshalAny(consumerCfg.class, "School.xml");

        System.out.println(cfg.getGraphs().get(2).getHour());
    }


    public static long getCurrentHour() {
        return (System.currentTimeMillis()-initTime)/hourDuration;
    }

    public static long calcMillisTillNextHour() {
        return hourDuration - (System.currentTimeMillis()-initTime)%hourDuration;
    }

}