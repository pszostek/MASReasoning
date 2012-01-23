package behaviours;

import agent.ReasoningAgent;
import messaging.History;
import messaging.HistEl;
import messaging.Message;
import messaging.ForthMessage;
import messaging.BackMessage;
import messaging.FinalMessage;
import logic.Clause;
import logic.Literal;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class HandleForthMessage extends OneShotBehaviour {
	private ACLMessage acl_message;
	private ForthMessage forth_message;

	public HandleForthMessage(Agent a, ACLMessage msg) {
		super(a);
		acl_message = msg;
		try {
			forth_message = (ForthMessage)msg.getContentObject();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void action() {
		Message msg = (Message) acl_message.getContentObject();
		Literal p = new Literal(msg.getClause());
		History hist = msg.getHistory();
		if (hist.contains(new HistEl(p.not(), null, null))) {
			ACLMessage r1 = acl_message.createReply();
			r1.setContentObject(new BackMessage(hist.push(new HistEl(p,	myAgent, Clause.emptyClause())), Clause.emptyClause()));
			ACLMessage r2 = acl_message.createReply();
			r2.setContentObject(new FinalMessage(hist.push(new HistEl(p,myAgent, Clause.trueClause())), Clause.trueClause()));
			myAgent.send(r1);
			myAgent.send(r2);
		} else if (myAgent.getKnowledge().contains(p)
				|| hist.contains(new HistEl(new Clause(p), myAgent, null))) {
			ACLMessage r1 = acl_message.createReply();
			r1.setContentObject(new FinalMessage(hist.push(new HistEl(p,myAgent, Clause.trueClause())), Clause.trueClause()));
			myAgent.send(r1);
		} else {
			/* LOCAL(SELF) <- {p} u myAgent.getKnowledge().resolvent(p); */
			if (LOCAL(SELF).isEmpty()) {
				ACLMessage r1 = acl_message.createReply();
				r1.setContentObject(new BackMessage(hist.push(new HistEl(p,	myAgent, Clause.emptyClause())), Clause.emptyClause()));
				ACLMessage r2 = acl_message.createReply();
				r2.setContentObject(new FinalMessage(hist.push(new HistEl(p, myAgent, Clause.trueClause())), Clause.trueClause()));
				myAgent.send(r1);
				myAgent.send(r2);
			} else {
				/*
				 * LOCAL(SELF) <- {c in LOCAL(SELF) | all literals in c are
				 * shared};
				 */
				if (LOCAL.isEmpty()) {
					ACLMessage r1 = acl_message.createReply();
					r1.setContentObject(new backMessage(hist.push(new HistEl(p, myAgent, Clause.trueClause())),Clause.trueClause()));
				}
				for (Clause c : LOCAL(SELF))
					for (Literal l : c) {
						/*
						 * BOTTOM(l, hist.append(new HistEl(p, agent,c))) <-
						 * false
						 */
						for (Agent a : myAgent.getNeighbours(l)) {
							/*
							 * FINAL(l, hist.append(new HistEl(p, agent,c)), a)
							 * <- false
							 */
							ACLMessage r1 = new ACLMessage();
							r1.addReceiver(a.getAID());
							r1.setContentObject(new Message(hist.push(new HistEl(p, myAgent, c)), l));
						}
					}
			}
		}
	} // public void action
} // class