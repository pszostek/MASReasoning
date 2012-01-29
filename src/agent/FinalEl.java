package agent;

import messaging.History;
import logic.Literal;
import jade.core.AID;

public class FinalEl {
    private Literal first;
    private History second;
    private AID agent;

    public FinalEl(Literal first, History second, AID nAgent) {
    	super();
    	this.first = first;
    	this.second = new History(second);
    	this.agent=nAgent;
    }

    public int hashCode() {
    	int hashFirst = first != null ? first.hashCode() : 0;
    	int hashSecond = second != null ? second.hashCode() : 0;
    	int hashAgent = agent !=null ? agent.hashCode() : 0;
    	return (hashFirst + hashSecond) * hashSecond + hashFirst +hashAgent;
    }

    public boolean equals(Object other) {
    	if (other instanceof FinalEl) {
    		FinalEl otherPair = (FinalEl) other;
    		return 
    		((  this.first == otherPair.first ||
    			( this.first != null && otherPair.first != null &&
    			  this.first.equals(otherPair.first))) &&
    		 (	this.second == otherPair.second ||
    			( this.second != null && otherPair.second != null &&
    			  this.second.equals(otherPair.second)))&&
    	    (	this.agent == otherPair.agent ||
    	   		( this.agent != null && otherPair.agent != null &&
    			  this.agent.equals(otherPair.agent))) );
    	}

    	return false;
    }

    public String toString()
    { 
           return "(" + first + ", " + second + ", "+agent+")"; 
    }

    public Literal getLiteral() {
    	return first;
    }

    public void setLiteral(Literal first) {
    	this.first = first;
    }

    public History getHistory() {
    	return second;
    }

    public void setHistory(History second) {
    	this.second = second;
    }
    public AID getAgent()
    {
    	return agent;
    }
    public void setAgent(AID nAgent)
    {
    	agent=nAgent;
    }
}