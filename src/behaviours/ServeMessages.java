package behaviours;

import java.io.IOException;

import agent.ReasoningAgent;
import jade.core.Agent;
import logic.Clause;
import logic.Literal;
import messaging.History;
import messaging.Message;
import messaging.BackMessage;
import messaging.FinalMessage;
import messaging.ForthMessage;
import messaging.KnowledgeDiscoveryMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class ServeMessages extends CyclicBehaviour {
	static final long serialVersionUID = 1;
	private ReasoningAgent agent;
	public ServeMessages (ReasoningAgent a) {
		super(a);
		agent = a;
	}
	public void action() {
		ACLMessage receivedMsg  = this.myAgent.receive();
		Object content = new Object();
		if (receivedMsg != null) {
			try {
				content = receivedMsg.getContentObject();
			} catch(UnreadableException e) {
				//mamy wiadomosc od uzykownika
					String userMsg = receivedMsg.getContent();
					System.out.println("Got message from User:" + userMsg);
					if(agent.getNeighboursDiscovered() == false) {
						this.agent.discoverNeighbours();
					}
					//teraz trzeba wziąć klauzulę od użytkownika, zaprzeczyć ją i wysłać wiadomość do samego siebie.. 
					ACLMessage queryMsg = new ACLMessage(ACLMessage.INFORM);
					queryMsg.addReceiver(this.myAgent.getAID());
					//bierzemy stringa, z niego literal, negujemy go, z tego klauzule
					Clause payloadClause = new Clause(new Literal(userMsg).not());
					ForthMessage payloadMessage = new ForthMessage(new History(), payloadClause);
					try {
						queryMsg.setContentObject(payloadMessage);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					myAgent.send(queryMsg);
			}
				
			if(content instanceof KnowledgeDiscoveryMessage) {
				System.out.println("Agent " + myAgent.getName() + " got KnowledgeDiscoveryMessage from " + receivedMsg.getSender() );
				this.myAgent.addBehaviour(new HandleKnowledgeDiscoveryMessage(this.agent, receivedMsg));

			} else if(content instanceof Message) {
				if(agent.getNeighboursDiscovered() == false) {
					this.agent.discoverNeighbours();
				}
				//check the the kind of the message
				if(content instanceof ForthMessage) {
					System.out.println("Agent " + myAgent.getName() + " got ForthMessage from " + receivedMsg.getSender() );
					this.myAgent.addBehaviour(new HandleForthMessage(this.agent , receivedMsg));
				}
				else if(content instanceof BackMessage) {
					System.out.println("Agent " + myAgent.getName() + " got BackMessage from " + receivedMsg.getSender() );
					this.myAgent.addBehaviour(new HandleBackMessage(this.agent, receivedMsg));
				}
				else if(content instanceof FinalMessage) {
					System.out.println("Agent " + myAgent.getName() + " got FinalMessage from " + receivedMsg.getSender() );
					this.myAgent.addBehaviour(new HandleFinalMessage(this.agent, receivedMsg));
				}
			} else {
				System.out.println("Unknown message type!");
			}
		}
	}
}