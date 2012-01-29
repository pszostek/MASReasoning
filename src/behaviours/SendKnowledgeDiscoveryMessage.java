package behaviours;
import java.io.IOException;

import agent.ReasoningAgent;
import agent.BottomEl;
import messaging.KnowledgeDiscoveryMessage;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


public class SendKnowledgeDiscoveryMessage extends OneShotBehaviour{
	public static final long serialVersionUID = 1;
	private KnowledgeDiscoveryMessage msg;
	private AID receiverAID;
	public SendKnowledgeDiscoveryMessage(ReasoningAgent myAgent, KnowledgeDiscoveryMessage msg, AID receiverAID) {
		super(myAgent);
		this.msg = msg;
		this.receiverAID = receiverAID;
	}
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM); 
		try {
			message.setContentObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		message.addReceiver(this.receiverAID);
		myAgent.send(message);
	}

}
