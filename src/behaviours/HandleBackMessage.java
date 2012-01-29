package behaviours;

import jade.lang.acl.UnreadableException;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import messaging.HistEl;
import messaging.History;
import logic.Clause;
import logic.Literal;
import agent.BottomEl;
import agent.ReasoningAgent;
import messaging.Message;
import messaging.BackMessage;
import messaging.FinalMessage;
import messaging.ForthMessage;
import java.io.IOException;

public class HandleBackMessage extends OneShotBehaviour {
		public static final long serialVersionUID = 1;
		private ReasoningAgent agent;
		private ACLMessage message;
		public HandleBackMessage(ReasoningAgent a, ACLMessage msg) {
			super(a);
			message = msg;
		}
		public void action() {
			Message msg = null;
			try{
				msg= (Message)message.getContentObject();
			}catch(UnreadableException ioe) {
				ioe.printStackTrace();
			}
			History hist = msg.getHistory();
			HistEl prevEl = hist.getPreviousElement(0);
			HistEl prevprev_el = hist.getPreviousElement(1);
			agent.setBOTTOM(new BottomEl(prevEl.getLiteral(), new History(hist).pop(2)), true);
			boolean all_true = true;
			for(Literal l: prevprev_el.getClause().asLiterals())
				if(agent.getBOTTOM(new BottomEl(l, hist.pop())) == false)
					all_true = false;
			if(all_true) {
				if((hist.pop().pop()).isEmpty()) {
					System.out.println("back");
					System.out.println("final");
				} else {
					ACLMessage r1 = new ACLMessage(ACLMessage.INFORM);
					r1.addReceiver(message.getSender());
					try{
						r1.setContentObject(new BackMessage(hist.pop(), Clause.trueClause()));
					} catch (IOException e) {
						e.printStackTrace();
					}
					myAgent.send(r1);
					ACLMessage r2 = new ACLMessage(ACLMessage.INFORM);
					r2.addReceiver(message.getSender());
					try{
					r2.setContentObject(new FinalMessage(hist.pop(), Clause.trueClause()));
					} catch (IOException e) {
						e.printStackTrace();
					}
					myAgent.send(r2);
				}
			}
		}
	}
