package agent;

import agent.Message.MessageType;
import behaviours.*;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReasoningAgent extends Agent {

	protected void setup() {
		addBehaviour(new ServeMessages(this));
	}
}

