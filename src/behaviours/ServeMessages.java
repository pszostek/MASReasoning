package behaviours;

import agent.ReasoningAgent;
import jade.core.Agent;
import messaging.Message;
import messaging.BackMessage;
import messaging.FinalMessage;
import messaging.ForthMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ServeMessages extends CyclicBehaviour {
	static final long serialVersionUID = 1;
	private ReasoningAgent agent;
	public ServeMessages (ReasoningAgent a) {
		super(a);
		agent = a;
	}
	public void action() {
		ACLMessage msg  = this.myAgent.receive();
		Object content = new Object();
		if (msg != null) {
			try {
				content = msg.getContentObject();
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
			if(agent.getNeighboursDiscovered() == false) {
				this.agent.discoverNeighbours();
				System.out.println("TU!");
			}
			if(content instanceof String)  { //mamy wiadomosc od uzykownika
				String userMsg = (String)content;
				System.out.println("Got message from User:" + userMsg);
			} else if(content instanceof Message) {
				if(content instanceof ForthMessage) {
					this.myAgent.addBehaviour(new HandleForthMessage(this.agent ,msg));
				}
				else if(content instanceof BackMessage) {
					this.myAgent.addBehaviour(new HandleBackMessage(this.agent,msg));
				}
				else if(content instanceof FinalMessage) {
					this.myAgent.addBehaviour(new HandleFinalMessage(this.agent,msg));
				}
			} else {
				System.out.println("Unknown message type!");
			}
		}
	}
}