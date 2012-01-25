package behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import messaging.History;
import messaging.HistEl;
import messaging.Message;

public class HandleFinalMessage extends OneShotBehaviour {
	public static final long serialVersionUID = 1;
	private ACLMessage acl_message;
	public HandleFinalMessage(Agent a, ACLMessage msg) {
		super(a);
		acl_message = msg;
	}
	public void action() {
		Message msg= (Message)acl_message.getContentObject();
		History hist = msg.getHistory();
		HistEl prev_el = hist.getPreviousElement(0);
		hist.pop();
	/*	FINAL(prev_el.getLiteral(), hist, msg.getSender()) = true; */
		HistEl prevprev_el = hist.getPreviousElement(0);
		hist.pop();
		boolean all_true = true;
		for(Clause c: LOCAL)
			for(Literal l: c) {
				if(FINAL(l,hist.append(new HistEl(l, myAgent.getAID(), c)), null) == false ) {
					all_true = false;
					break;
				}
			}
	}
}
