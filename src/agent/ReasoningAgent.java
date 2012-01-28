package agent;

import behaviours.*;

import logic.*;
import jade.core.AID;
import java.util.Map;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class ReasoningAgent extends Agent {
	public static final long serialVersionUID = 1;

	private Boolean neighboursDiscovered;
	public Boolean getNeighboursDiscovered() {
		return neighboursDiscovered;
	}
	public void setNeighboursDiscovered(Boolean neighboursDiscovered) {
		this.neighboursDiscovered = neighboursDiscovered;
	}

	private Integer reasoningID;
	public Integer getReasoningID() {
		return reasoningID;
	}
	public void setReasoningID(Integer reasoningID) {
		this.reasoningID = reasoningID;
	}

	private Knowledge knowledge;
	public Knowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	private Acquaintance neighbours;
	public void setNeighbours(Acquaintance neighbours) {
		this.neighbours = neighbours;
	}
	public Acquaintance getNeighbours() {
		return this.neighbours;
	}
	public List<AID> getNeighbours(Literal l) {
		return this.neighbours.getSasiedzi(l);
	}

	private Map<BottomEl, Boolean> BOTTOM;
	public void setBOTTOM(BottomEl el, Boolean value) {
		BOTTOM.put(el, value);
	}
	public Boolean getBOTTOM(BottomEl el) {
		return BOTTOM.get(el);
	}

	private List<Clause> LOCAL;
	public Boolean inLOCAL(Clause c) {
		return LOCAL.contains(c);
	}
	public void setLOCAL(Clause c, List<Clause> resolvent) {
		LOCAL = new ArrayList<Clause>();
		LOCAL.add(c);
		LOCAL.addAll(resolvent);
	}
	public void setLOCAL(List<Clause> list) {
		LOCAL = list;
	}
	public List<Clause> getLOCAL() {
		return this.LOCAL;
	}
	public void addToLOCAL(Clause c) {
		this.LOCAL.add(c);
	}

	public void discoverNeighbours() {
		List<AID> neighbours = new ArrayList<AID>();
		for(Integer i: this.neighbours.getReasoningIDs()) {
			DFAgentDescription dfd = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setName(Integer.toString(i));
			dfd.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(this, dfd);
				AID[] agents = new AID[result.length];
				if(agents.length != 1) {
					System.out.println("More than one agent has a name?");
				} else {
					neighbours.add(agents[0]);
				}
			} catch (FIPAException fe) {fe.printStackTrace();}
		}
		this.setNeighboursDiscovered(true);
	}
	private void DFRegister() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(Integer.toString(this.reasoningID));
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	protected void setup() {
		BOTTOM = new HashMap<BottomEl, Boolean>();
		Object[] args = getArguments();
		String knowledgeString;
		String reasoningIDString;
		String neighboursString;

		this.neighboursDiscovered = false;
		System.out.println(args.length);
		if (args != null && args.length == 3) {
			reasoningIDString = (String)args[0];
			knowledgeString = (String)args[1];
			neighboursString = (String)args[2];
			System.out.println("Knowledge String:" + knowledgeString);
			this.setKnowledge(new Knowledge(knowledgeString));
			this.setReasoningID(Integer.parseInt(reasoningIDString));
			this.setNeighbours(new Acquaintance(neighboursString));
			this.DFRegister();

		} else {
			throw new RuntimeException("Agent " + getName() + ": bad number of arguments. Three are expected.");
		}
		this.addBehaviour(new ServeMessages(this));
	}
}

