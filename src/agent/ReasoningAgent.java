package agent;

import behaviours.*;

import logic.*;
import jade.core.AID;
import java.util.Map;
import jade.core.Agent;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ReasoningAgent extends Agent {
	public static final long serialVersionUID = 1;

	private Knowledge knowledge;
	private Acquaintance neighbours;
	private Map<BottomEl, Boolean> BOTTOM;

	public Knowledge getKnowledge() {
		return this.knowledge;
	}

	public List<AID> getNeighbours(Literal l) {
		return this.neighbours.getSasiedzi(l);
	}

	public void setBOTTOM(BottomEl el, Boolean value) {
		BOTTOM.put(el, value);
	}

	public Boolean getBOTTOM(BottomEl el) {
		return BOTTOM.get(el);
	}

	protected void setup() {
		BOTTOM = new HashMap<BottomEl, Boolean>();
		Object[] args = getArguments();
		String knowledgeString;
		String neighboursString;
		if (args != null) {
			if(args.length != 2) {
				System.out.println("Strange number of agent run parameters!");
			}
			knowledgeString = (String)args[0];
			this.knowledge = new Knowledge(knowledgeString);
			neighboursString = (String)args[1];
			//TODO: Potraktowac jakos drugi argument: agentów
		}
		this.addBehaviour(new ServeMessages(this));
	}
}

