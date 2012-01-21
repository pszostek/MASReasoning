package agent;

import agent.History;
import logic.Literal;

public class BottomEl {
    private Literal first;
    private History second;

    public BottomEl(Literal first, History second) {
    	super();
    	this.first = first;
    	this.second = second;
    }

    public int hashCode() {
    	int hashFirst = first != null ? first.hashCode() : 0;
    	int hashSecond = second != null ? second.hashCode() : 0;

    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public boolean equals(Object other) {
    	if (other instanceof BottomEl) {
    		BottomEl otherPair = (BottomEl) other;
    		return 
    		((  this.first == otherPair.first ||
    			( this.first != null && otherPair.first != null &&
    			  this.first.equals(otherPair.first))) &&
    		 (	this.second == otherPair.second ||
    			( this.second != null && otherPair.second != null &&
    			  this.second.equals(otherPair.second))) );
    	}

    	return false;
    }

    public String toString()
    { 
           return "(" + first + ", " + second + ")"; 
    }

    public Literal getLiteral() {
    	return first;
    }

    public void setLiteral(Literal first) {
    	this.first = first;
    }

    public History getSecond() {
    	return second;
    }

    public void setHistory(History second) {
    	this.second = second;
    }
}