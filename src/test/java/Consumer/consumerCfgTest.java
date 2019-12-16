package Consumer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class consumerCfgTest {
    private List<graph> ListOfGraphs = new ArrayList<graph>();;
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
        cfg.setGraphs(ListOfGraphs);

        WorkWithXml.marshalAny(consumerCfg.class ,cfg,"Hospital.xml");
    }

}