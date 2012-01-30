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
			agent = a;
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
			hist.pop();
			HistEl prevprev_el = hist.getPreviousElement(0);
			hist.pop();

			History newHist = new History(hist).push(prevprev_el);
			BottomEl newBottom = new BottomEl(prevEl.getLiteral(), newHist);

			agent.setBOTTOM(newBottom, true);
			boolean all_true = true;
			for(Literal l: prevprev_el.getClause().asLiterals()) {
				BottomEl check = new BottomEl(l, new History(hist));
				if(agent.getBOTTOM(check) == false)
					all_true = false;
			}
			if(all_true) {
				if(hist.isEmpty()) {
					System.out.println("back!!!!");
				} else {
					ACLMessage r1 = new ACLMessage(ACLMessage.INFORM);
					r1.addReceiver(hist.peekLast().getAgentId());
					try{
						r1.setContentObject(new BackMessage(new History(hist).push(prevprev_el), Clause.trueClause()));
					} catch (IOException e) {
						e.printStackTrace();
					}
					myAgent.send(r1);
					ACLMessage r2 = new ACLMessage(ACLMessage.INFORM);
					r2.addReceiver(hist.peekLast().getAgentId());
					try{
						r2.setContentObject(new FinalMessage(new History(hist).push(prevprev_el), Clause.trueClause()));
					} catch (IOException e) {
						e.printStackTrace();
					}
					myAgent.send(r2);
				}
			}
		}
	}
