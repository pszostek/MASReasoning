package behaviours;

import jade.core.Agent;
import messaging.Message;
import messaging.BackMessage;
import messaging.FinalMessage;
import messaging.ForthMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class ServeMessages extends CyclicBehaviour {
	public ServeMessages (Agent a) {
		super(a);
		
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
			if(content instanceof String)  { //mamy wiadomosc od uzykownika
				String userMsg = (String)content;
				System.out.println("Got message from User:" + userMsg);
			} else if(content instanceof Message) {
				if(content instanceof ForthMessage) {
					this.myAgent.addBehaviour(new HandleForthMessage(this.myAgent ,msg));
				}
				else if(content instanceof BackMessage) {
					this.myAgent.addBehaviour(new HandleBackMessage(this.myAgent,msg));
				}
				else if(content instanceof FinalMessage) {
					this.myAgent.addBehaviour(new HandleFinalMessage(this.myAgent,msg));
				}
			} else {
				System.out.println("Unknown message type!");
			}
		}
	}
}