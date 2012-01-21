package behaviours;

import agent.ReasoningAgent;
import agent.History;
import agent.Message;
import agent.Message.MessageType;
import logic.Clause;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class HandleForthMessage extends OneShotBehaviour {
		private ACLMessage acl_message;
		public HandleForthMessage(Agent a, ACLMessage msg) {
			super(a);
			acl_message = msg;
		}
		public void action() {
			Message msg = (Message)acl_message.getContentObject();
			Clause p = msg.getClause();
			History hist = msg.getHistory();
			if(hist.contains(p.not())) {
				ACLMessage r1 = acl_message.createReply();
				r1.setContentObject(new Message(BACK, hist.append(new HistEl(p, myAgent, EMPTY_CLAUSE)), EMPTY_CLAUSE));
				ACLMessage r2 = acl_message.createReply();
				r2.setContentObject(new Message(FINAL, hist.append(new HistEl(p, myAgent,TRUE_CLAUSE)), TRUE_CLAUSE));
				myAgent.send(r1);
				myAgent.send(r2);
			}
			else if myAgent.getKnowledge().contains(p) || hist.contains(new HistEl(p, myAgent, null)) {
				ACLMessage r1 = acl_message.createReply();
				r1.setContentObject(new Message(FINAL, hist.append(new HistEl(p, myAgent, TRUE_CLAUSE)), TURE_CLAUSE));
				myAgent.send(r1);
			}
			else {
				LOCAL(SELF) <- {p} u myAgent.getKnowledge().resolvent(p)
				if(LOCAL(SELF).contains(EMPTY_CLAUSE)) {
					ACLMessage r1 = acl_message.createReply();
					r1.setContentObject(new Message(BACK, hist.append(new HistEl(p, myAgent, EMPTY_CLAUSE)), EMPTY_CLAUSE));
					ACLMessage r2 = acl_message.createReply();
					r2.setContentObject(new Message(FINAL, hist.append(new HistEl(p, myAgent,TRUE_CLAUSE)), TRUE_CLAUSE));
					myAgent.send(r1);
					myAgent.send(r2);
				}
			}
				else {
					LOCAL(SELF) <- {c in LOCAL(SELF) | all literals in c are shared}
					if(LOCAL.equals(EMPTY_SET)) {
						ACLMessage r1 = message.createReply();
						r1.setContentObject(new Message(BACK, hist.append(new HistEl(p, myAgent, TRUE_CLAUSE)), TRUE_CLAUSE));
					}
					for(Clause c: LOCAL(SELF))
						for(Literal l: c) {
							BOTTOM(l, hist.append(new HistEl(p, agent,c))) <- false
							for(Agent a: agent.getNeighbours(l)) {
								FINAL(l, hist.append(new HistEl(p, agent,c)), a) <- false
								ACLMessage r1 = new ACLMessage();
								r1.addReceiver(a);
								r1.setContentObject(new Message(FORTH, hist.append(new HistEl(p, myAgent, c)), l));
							}
						}
				}
		}
	}