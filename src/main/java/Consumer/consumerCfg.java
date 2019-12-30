package Consumer;

        import javax.xml.bind.annotation.*;
        import java.util.List;
        import java.util.Objects;

@XmlRootElement(name = "consumerCfg")
@XmlAccessorType(XmlAccessType.FIELD)
public class consumerCfg {
    @XmlElement
    private String consumerName;
    @XmlElement
    private String DistributorName;

    public int getInstalledPower() {
        return installedPower;
    }

    public void setInstalledPower(int installedPower) {
        this.installedPower = installedPower;
    }

    @XmlElement
    private int installedPower;
    @XmlElementWrapper(name = "graph")
    @XmlElement(name = "graphs")
    private List<graph> graphs;

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public void setDistributorName(String distributorName) {
        DistributorName = distributorName;
    }

    public List<graph> getGraphs() {
        return graphs;
    }

    public void setGraphs(List<graph> graphs) {
        this.graphs = graphs;
    }
}
