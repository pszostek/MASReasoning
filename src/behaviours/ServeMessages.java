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
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class ServeMessages extends SimpleBehaviour {
	static final long serialVersionUID = 1;
	private ReasoningAgent agent;

	public ServeMessages(ReasoningAgent a) {
		super(a);
		agent = a;
	}

	public void action() {
		this.block(12);
		ACLMessage receivedMsg = this.myAgent.receive();
		Object content = new Object();
		boolean gotMsgFromUser = false;
		if (receivedMsg != null) {
			try {
				content = receivedMsg.getContentObject();
			} catch (UnreadableException e) {
				gotMsgFromUser = true;

			}
			if (gotMsgFromUser) {
				// mamy wiadomosc od uzykownika
				String userMsg = receivedMsg.getContent();
				System.out.println("Got message from User:" + userMsg);
				if (agent.getNeighboursDiscovered() == false) {
					this.agent.discoverNeighbours();
					block(1000);
				}
				// teraz trzeba wziąć klauzulę od użytkownika, zaprzeczyć ją i
				// wysłać wiadomość do samego siebie..
				ACLMessage queryMsg = new ACLMessage(ACLMessage.INFORM);
				queryMsg.addReceiver(this.myAgent.getAID());
				// bierzemy stringa, z niego literal, negujemy go, z tego
				// klauzule
				Clause payloadClause = new Clause(new Literal(userMsg).not());
				ForthMessage payloadMessage = new ForthMessage(new History(),payloadClause);
				try {
					queryMsg.setContentObject(payloadMessage);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				myAgent.send(queryMsg);
			} else if (content instanceof KnowledgeDiscoveryMessage) {
				System.out.println("Agent " + myAgent.getName()
						+ " got KnowledgeDiscoveryMessage from "
						+ receivedMsg.getSender());
				this.myAgent.addBehaviour(new HandleKnowledgeDiscoveryMessage(
						this.agent, receivedMsg));

			} else if (content instanceof Message) {
				if (agent.getNeighboursDiscovered() == false) {
					this.agent.discoverNeighbours();
					block(1000);
				}
				// check the the kind of the message
				if (content instanceof ForthMessage) {
					System.out.println("Agent " + myAgent.getName()
							+ " got ForthMessage from "
							+ receivedMsg.getSender().getName());
					this.myAgent.addBehaviour(new HandleForthMessage(
							this.agent, receivedMsg));
				} else if (content instanceof BackMessage) {
					System.out.println("Agent " + myAgent.getName()
							+ " got BackMessage from "
							+ receivedMsg.getSender().getName());
					this.myAgent.addBehaviour(new HandleBackMessage(this.agent,
							receivedMsg));
				} else if (content instanceof FinalMessage) {
					System.out.println("Agent " + myAgent.getName()
							+ " got FinalMessage from "
							+ receivedMsg.getSender().getName());
					this.myAgent.addBehaviour(new HandleFinalMessage(
							this.agent, receivedMsg));
				}
			} else {
				System.out.println("Agent " + myAgent.getName()
						+ " got Unknown Message from "
						+ receivedMsg.getSender().getName());
			}
		}
	}
	public boolean done() {
		return false;
	}
}