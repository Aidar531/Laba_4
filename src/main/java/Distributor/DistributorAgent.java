package Distributor;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class DistributorAgent extends Agent {
    @Override
    protected void setup() {

        addBehaviour(new FSMBeh_Distro());
    }

}
