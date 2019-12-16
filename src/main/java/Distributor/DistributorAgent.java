package Distributor;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class DistributorAgent extends Agent {
    @Override
    protected void setup() {

        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("Generation");
        dfd.addServices(sd);

        DFAgentDescription[] foundAgents = new DFAgentDescription[0];
        try {
            foundAgents = DFService.search(this,dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        addBehaviour(new FSMBeh_Distro(foundAgents));
    }

}
