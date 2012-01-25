package behaviours;

import java.io.IOException;

import agent.ReasoningAgent;
import agent.BottomEl;
import messaging.History;
import messaging.HistEl;
import messaging.Message;
import messaging.ForthMessage;
import messaging.BackMessage;
import messaging.FinalMessage;
import logic.Clause;
import logic.Literal;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import agent.ReasoningAgent;
import java.util.List;
import java.util.ArrayList;

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
		Message msg = null;
		try{
			msg = (Message) acl_message.getContentObject();
		}catch(UnreadableException ie) {
			ie.printStackTrace();
		}
		Literal p = new Literal(msg.getClause());
		History hist = msg.getHistory();
		if (hist.contains(new HistEl(p.not(), null, null))) {
			ACLMessage r1 = acl_message.createReply();

			try{
				r1.setContentObject(new BackMessage(hist.push(new HistEl(p,	myAgent.getAID(), Clause.emptyClause())), Clause.emptyClause()));
			}catch(IOException ie) {
				ie.printStackTrace();
			}

			ACLMessage r2 = acl_message.createReply();
			try{
				r2.setContentObject(new FinalMessage(hist.push(new HistEl(p,myAgent.getAID(), Clause.trueClause())), Clause.trueClause()));
			}catch(IOException ie) {
				ie.printStackTrace();
			}

			myAgent.send(r1);
			myAgent.send(r2);
		} else if (agent.getKnowledge().contains(p) || hist.contains(new HistEl(p, myAgent.getAID(), null))) {
			ACLMessage r1 = acl_message.createReply();
			try{
				r1.setContentObject(new FinalMessage(hist.push(new HistEl(p, myAgent.getAID(), Clause.trueClause())), Clause.trueClause()));
			} catch(IOException ie) {
				ie.printStackTrace();
			}
			myAgent.send(r1);
		} else {
			agent.setLOCAL( new Clause(p), agent.getKnowledge().getResult(p));
			if (agent.inLOCAL(Clause.emptyClause())) {
				ACLMessage r1 = acl_message.createReply();
				try {
					r1.setContentObject(new BackMessage(hist.push(new HistEl(p,	myAgent.getAID(), Clause.emptyClause())), Clause.emptyClause()));
				} catch(IOException ie) {
					ie.printStackTrace();
				}

				ACLMessage r2 = acl_message.createReply();
				try{
					r2.setContentObject(new FinalMessage(hist.push(new HistEl(p, myAgent.getAID(), Clause.trueClause())), Clause.trueClause()));
				} catch(IOException ie) {
					ie.printStackTrace();
				}

				myAgent.send(r1);
				myAgent.send(r2);
			} else {
				List<Clause> newLocal = new ArrayList<Clause>();
				for(Clause c:agent.getLOCAL()) {
					if(agent.getNeighbours().isWholeClauseShared(c))
						newLocal.add(c);
				}
				agent.setLOCAL(newLocal);

				if (agent.getLOCAL().size() == 0) {
					ACLMessage r1 = acl_message.createReply();
					try{
						r1.setContentObject(new BackMessage(hist.push(new HistEl(p, myAgent.getAID(), Clause.trueClause())),Clause.trueClause()));
					}catch(IOException ie) {
						ie.printStackTrace();
					}
				}
				for (Clause c : agent.getLOCAL()) {
					for (Literal l : c.getLiterals()) {
						agent.setBOTTOM(new BottomEl(l, hist.push(new HistEl(p, myAgent.getAID() ,c))), false);
				   		for (AID a : agent.getNeighbours(l)) {
							/*
							 * FINAL(l, hist.append(new HistEl(p, agent,c)), a)
							 * <- false
							 */
							ACLMessage r1 = new ACLMessage(ACLMessage.INFORM);
							r1.addReceiver(a);
							try{
								r1.setContentObject(new ForthMessage(hist.push(new HistEl(p, myAgent.getAID(), c)), new Clause(l)));
							} catch(IOException ie) {
								ie.printStackTrace();
							} //catch
						} //for AID a
					} //for Literal l
				} //for Clause c
			} //else
		} //else
	} // public void action
} // class