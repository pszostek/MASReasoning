package behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import agent.History;
import agent.HistEl;
import agent.Message;

public class HandleFinalMessage extends OneShotBehaviour {
	private Agent agent;
	private ACLMessage message;
	public HandleFinalMessage(Agent a, ACLMessage msg) {
		super(a);
		message = msg;
	}
	public void action() {
		Message msg= (Message)message.getContentObject();
		History hist = msg.getHistory();
		HistEl prev_el = hist.getPreviousElement(-1);
		FINAL(prev_el.getLiteral(), hist.pop(), msg.getSender()) = true;
		
	}
}