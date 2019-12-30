package Consumer;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class OneEnergyRequest extends OneShotBehaviour {
    consumerCfg cfg;
    Energy energy;

    public OneEnergyRequest(consumerCfg cfg, Energy energy) {
        this.cfg = cfg;
        this.energy = energy;
    }

    @Override
    public void action() {
        System.out.println("---------------------------------");
        System.out.println("Осталось купить " + energy.getEnergy() + " " + getAgent().getLocalName());
        System.out.println("---------------------------------");
//        System.out.println("вычел энергию");
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID(cfg.getDistributorName(), false));
        msg.setProtocol("start");
        msg.setContent("1");
//        System.out.println("Начал процесс закупки по одному Вт " +
//                energy.getEnergy() +
//                " Вт "+ getAgent().getLocalName());
//        System.out.println(energy.getEnergy());
        myAgent.send(msg);
    }
}
