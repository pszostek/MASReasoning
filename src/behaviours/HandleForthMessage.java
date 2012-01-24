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
import logic.Knowledge;
import jade.core.Agent;
import jade.core.AID:
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import agent.ReasoningAgent;

public class HandleForthMessage extends OneShotBehaviour {
	private ACLMessage acl_message;
	private ForthMessage forth_message;
	private ReasoningAgent agent;

	public HandleForthMessage(ReasoningAgent a, ACLMessage msg) {
		super(a);
		agent = a;
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
			r1.setContentObject(new BackMessage(hist.push(new HistEl(p,	myAgent.getAID(), Clause.emptyClause())), Clause.emptyClause()));
			ACLMessage r2 = acl_message.createReply();
			r2.setContentObject(new FinalMessage(hist.push(new HistEl(p,myAgent.getAID(), Clause.trueClause())), Clause.trueClause()));
			myAgent.send(r1);
			myAgent.send(r2);
		} else if (agent.getKnowledge().contains(p) || hist.contains(new HistEl(p, myAgent.getAID(), null))) {
			ACLMessage r1 = acl_message.createReply();
			r1.setContentObject(new FinalMessage(hist.push(new HistEl(p, myAgent.getAID(), Clause.trueClause())), Clause.trueClause()));
			myAgent.send(r1);
		} else {
			/* LOCAL(SELF) <- {p} u myAgent.getKnowledge().resolvent(p); */
			if (LOCAL(SELF).isEmpty()) {
				ACLMessage r1 = acl_message.createReply();
				r1.setContentObject(new BackMessage(hist.push(new HistEl(p,	myAgent.getAID(), Clause.emptyClause())), Clause.emptyClause()));
				ACLMessage r2 = acl_message.createReply();
				r2.setContentObject(new FinalMessage(hist.push(new HistEl(p, myAgent.getAID(), Clause.trueClause())), Clause.trueClause()));
				myAgent.send(r1);
				myAgent.send(r2);
			} else {
				/*
				 * LOCAL(SELF) <- {c in LOCAL(SELF) | all literals in c are
				 * shared};
				 */
				if (LOCAL.isEmpty()) {
					ACLMessage r1 = acl_message.createReply();
					r1.setContentObject(new BackMessage(hist.push(new HistEl(p, myAgent.getAID(), Clause.trueClause())),Clause.trueClause()));
				}
				for (Clause c : LOCAL(SELF))
					for (Literal l : c.getLiterals()) {
						/*
						 * BOTTOM(l, hist.append(new HistEl(p, agent,c))) <-
						 * false
						 */
						for (AID a : agent.getNeighbours(l)) {
							/*
							 * FINAL(l, hist.append(new HistEl(p, agent,c)), a)
							 * <- false
							 */
							ACLMessage r1 = new ACLMessage(ACLMessage.INFORM);
							r1.addReceiver(a);
							r1.setContentObject(new ForthMessage(hist.push(new HistEl(p, myAgent.getAID(), c)), new Clause(l)));
						}
					}
			}
		}
	} // public void action
} // class