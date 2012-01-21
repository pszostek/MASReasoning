package behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import agent.HistEl;
import agent.History;
import knwoledge.Literal;
import agent.Message;
import agent.Message.MessageType;

public class HandleBackMessage extends OneShotBehaviour {
		private Agent agent;
		private ACLMessage message;
		public HandleBackMessage(Agent a, ACLMessage msg) {
			super(a);
			agent = a;
			message = msg;
		}
		public void action() {
			Message msg= (Message)message.getContentObject();
			History hist = msg.getHistory();
			HistEl prev_el = hist.getPreviousElement(-1);
			HistEl prevprev_ep = hist.getPreviousElement(-2);
			BOTTOM(l`, hist.pop()) = true;
			boolean all_true = true
			for(Literal l: prevprev_el.getClause())
				if(BOTTOM(l, hist.pop()) == false)
					all_true = false;
			if(all_true) {
				if((hist.pop().pop()).isEmpty()) {
					System.out.println("back");
					System.out.println("final");
				}
				else {
					ACLMessage r1 = new ACLMessage();
					r1.addReceiver(message.getSender());
					r1.setContentObject(new Message(MessageType.BACK, hist.pop()));
					myAgent.send(r1);
					ACLMessage r2 = new ACLMessage();
					r2.addReceiver(message.getSender());
					r2.setContentObject(new Message(FINAL, hist.pop()));
					myAgent.send(r2);
				}
			}
		}
	}
