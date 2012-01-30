package behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import agent.ReasoningAgent;
import jade.core.Agent;
import messaging.KnowledgeDiscoveryMessage;

public class HandleKnowledgeDiscoveryMessage extends OneShotBehaviour {
	public static final long serialVersionUID = 1;
	private ACLMessage msg;
	private ReasoningAgent agent;
	
	public HandleKnowledgeDiscoveryMessage(ReasoningAgent a, ACLMessage msg) {
		super(a);
		this.msg = msg;
		this.agent = a;
	}
	public void action() {
		KnowledgeDiscoveryMessage kdm = null;
		try {
			kdm = (KnowledgeDiscoveryMessage)(msg.getContentObject());
			agent.updateNeighbour(msg.getSender(), kdm.getLiterals());
		} catch (UnreadableException ie) {
			ie.printStackTrace();
		}
		if(kdm.isAnswer() == false) {
			KnowledgeDiscoveryMessage response = new KnowledgeDiscoveryMessage(agent.getKnowledge().getAllLiterals(), true);
		}
		if(agent.getNeighbours().size() == agent.neigboursKnown())
			agent.setNeighboursDiscovered(true);
		System.out.print("MESSAGE ");
		System.out.println("ID: " + myAgent.getName());
	}
}
