package behaviours;

import java.io.IOException;

import agent.ReasoningAgent;
import agent.BottomEl;
import agent.FinalEl;
import messaging.History;
import messaging.HistEl;
import messaging.Message;
import messaging.ForthMessage;
import messaging.BackMessage;
import messaging.FinalMessage;
import logic.Clause;
import logic.Literal;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.List;
import java.util.ArrayList;

public class HandleForthMessage extends OneShotBehaviour {
	static final long serialVersionUID = 1;
	private ACLMessage acl_message;
	private ReasoningAgent agent;

	public HandleForthMessage(ReasoningAgent myAgent, ACLMessage msg) {
		super(myAgent);
		agent = myAgent;
		acl_message = msg;
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
		if (hist.contains(new HistEl(new Literal(p).not(), null, null))) {
			ACLMessage r1 = acl_message.createReply();

			try{
				r1.setContentObject(new BackMessage(new History(hist).push(new HistEl(p, myAgent.getAID(), Clause.emptyClause())), Clause.emptyClause()));
			}catch(IOException ie) {
				ie.printStackTrace();
			}

			ACLMessage r2 = acl_message.createReply();
			try{
				r2.setContentObject(new FinalMessage(new History(hist).push(new HistEl(p, myAgent.getAID(), Clause.trueClause())), Clause.trueClause()));
			}catch(IOException ie) {
				ie.printStackTrace();
			}

			myAgent.send(r1);
			myAgent.send(r2);
		} else if (agent.getKnowledge().contains(p) || hist.contains(new HistEl(p, myAgent.getAID(), null))) {
			ACLMessage r1 = acl_message.createReply();
			try{
				r1.setContentObject(new FinalMessage(new History(hist).push(new HistEl(p, myAgent.getAID(), Clause.trueClause())), Clause.trueClause()));
			} catch(IOException ie) {
				ie.printStackTrace();
			}
			myAgent.send(r1);
		} else {
			agent.setLOCAL( new Clause(p), agent.getKnowledge().getResult(p));
			System.out.println(agent.getLOCAL().size());
			if (agent.inLOCAL(Clause.emptyClause())) {
				ACLMessage r1 = acl_message.createReply();
				try {
					History newHistory = new History(hist).push(new HistEl(p, myAgent.getAID(), Clause.emptyClause()));
					BackMessage ret = new BackMessage(newHistory, Clause.emptyClause());
					r1.setContentObject(ret);;
				} catch(IOException ie) {
					ie.printStackTrace();
				}

				ACLMessage r2 = acl_message.createReply();
				try{
					r2.setContentObject(new FinalMessage(new History(hist).push(new HistEl(p, myAgent.getAID(), Clause.trueClause())), Clause.trueClause()));
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
					System.out.println("TU :((");
					ACLMessage r1 = acl_message.createReply();
					try{
						r1.setContentObject(new FinalMessage(new History(hist).push(new HistEl(p, myAgent.getAID(), Clause.trueClause())),Clause.trueClause()));
					}catch(IOException ie) {
						ie.printStackTrace();
					}
					myAgent.send(r1);
					return;
				}
				for (Clause c : agent.getLOCAL()) {
					for (Literal l : c.getLiterals()) {
						agent.setBOTTOM(new BottomEl(l, new History(hist).push(new HistEl(p, myAgent.getAID() ,c))), false);
				   		for (AID a : agent.getNeighbours(l)) {
							agent.setFINAL(new FinalEl(l, new History(hist).push(new HistEl(p, myAgent.getAID(), c)), a), false);
							ACLMessage r1 = new ACLMessage(ACLMessage.INFORM);
							r1.addReceiver(a);
							try{
								History newHist = new History(hist).push(new HistEl(p, myAgent.getAID(), c));
								r1.setContentObject(new ForthMessage(newHist, new Clause(l)));
							} catch(IOException ie) {
								ie.printStackTrace();
							}
							myAgent.send(r1);//catch
						} //for AID a
					} //for Literal l
				} //for Clause c
			} //else
		} //else
	} // public void action
} // class