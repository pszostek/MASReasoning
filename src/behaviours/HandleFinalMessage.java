package behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messaging.History;
import messaging.HistEl;
import messaging.Message;
import agent.ReasoningAgent;
import logic.Clause;
import logic.Literal;

public class HandleFinalMessage extends OneShotBehaviour {
	public static final long serialVersionUID = 1;
	private ACLMessage acl_message;
	private ReasoningAgent agent;
	public HandleFinalMessage(ReasoningAgent a, ACLMessage msg) {
		super(a);
		acl_message = msg;
		agent = a;
	}
	public void action() {
		Message msg = null;
		try {
			msg= (Message)acl_message.getContentObject();
		}catch(UnreadableException e) {
			e.printStackTrace();
		}
		History hist = msg.getHistory();
		HistEl prev_el = hist.getPreviousElement(0);
		hist.pop();
	/*	FINAL(prev_el.getLiteral(), hist, msg.getSender()) = true; */
		HistEl prevprev_el = hist.getPreviousElement(0);
		hist.pop();
		boolean all_true = true;
		for(Clause c: agent.getLOCAL())
			for(Literal l: c.asLiterals()) {
			//	if(FINAL(l,hist.append(new HistEl(l, myAgent.getAID(), c)), null) == false ) {
			//		all_true = false;
			//		break;
			//	}
			}
	}
}
