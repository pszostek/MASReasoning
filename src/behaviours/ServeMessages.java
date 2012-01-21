package behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import agent.Message;

public class ServeMessages extends CyclicBehaviour {
	public ServeMessages (Agent a) {
		super(a);
		
	}
	public void action() {
		ACLMessage msg  = this.myAgent.receive();
		if (msg != null) {
			Message content = new Message();
			try {
				content= (Message)msg.getContentObject();;
			} catch (UnreadableException e) {
				System.out.println("Got unreadable message! Too bad!");
			}
			Message.MessageType type = content.getType();
			switch(type) {
			case FORTH:
				this.myAgent.addBehaviour(new HandleForthMessage(this.myAgent ,msg));
				break;
			case BACK:
				this.myAgent.addBehaviour(new HandleBackMessage(this.myAgent,msg));
				break;
			case FINAL:
				this.myAgent.addBehaviour(new HandleFinalMessage(this.myAgent,msg));
				break;
			default:
				System.out.println("Unknown message typ!");
			}
		}
	}
}