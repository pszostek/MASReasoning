package agent;

import behaviours.*;

import logic.*;
import jade.core.AID;

import java.io.IOException;
import java.util.Map;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import messaging.KnowledgeDiscoveryMessage;

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
	public void updateNeighbour(AID agent, HashSet<Literal> literals ) {

	}
	public void discoverNeighbours() {
		for(AID i: this.neighbours.getAgenci()) {
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			KnowledgeDiscoveryMessage msg = new KnowledgeDiscoveryMessage(knowledge.getAllLiterals());
			try {
				message.setContentObject(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			message.addReceiver(i);
			send(message);
		}
	}
	/*
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
	}*/
	protected void setup() {
		BOTTOM = new HashMap<BottomEl, Boolean>();
		Object[] args = getArguments();
		String knowledgeString;
		String reasoningIDString;
		String neighboursString;

		this.neighboursDiscovered = false;
		System.out.println(args.length);
		if (args != null && args.length == 2) {
			knowledgeString = (String)args[0];
			neighboursString = (String)args[1];
			System.out.println("Knowledge String:" + knowledgeString);
			System.out.println("Neigbours: " + neighboursString);
			System.out.println("Local name: " + this.getName());
			this.setKnowledge(new Knowledge(knowledgeString));
			this.setNeighbours(new Acquaintance(neighboursString));

		} else {
			throw new RuntimeException("Agent " + getName() + ": bad number of arguments. Two are expected.");
		}
		this.addBehaviour(new ServeMessages(this));
	}
}

