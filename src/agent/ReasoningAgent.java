package agent;

import behaviours.*;
import logic.*;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReasoningAgent extends Agent {
	private Knowledge knowledge;
	private int agent_id;
	public Knowledge getKnowledge() {
		return this.knowledge;
	}
	protected void setup() {
		addBehaviour(new ServeMessages(this));
	}
	public int getAgentId()
	{
		return agent_id;
	}
	public void setAgentId(int nAgentId)
	{
		agent_id=nAgentId;
	}
}

