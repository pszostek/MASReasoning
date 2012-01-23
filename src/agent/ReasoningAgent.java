package agent;

import behaviours.*;

import logic.*;
import jade.core.Agent;
import java.util.List;

public class ReasoningAgent extends Agent {
	public static final long serialVersionUID = 1;

	private Knowledge knowledge;
	private int agent_id;
	private List<Integer> neighbours;

	public Knowledge getKnowledge() {
		return this.knowledge;
	}
	public List<Integer> getNeighbours() {
		return this.neighbours;
	}
	protected void setup() {
		Object[] args = getArguments();
		String s;
		if (args != null) {

		}
		else {

		}
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

