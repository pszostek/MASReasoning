package messaging;

import java.io.Serializable;
import logic.Clause;

public class Message implements Serializable {
	static final long serialVersionUID = 1;

	private History hist;
	private Clause clause;
	
	public History getHistory(){
		return this.hist;
	}
	public Clause getClause() {
		return this.clause;
	}
	
	public void setHistory(History h) {
		this.hist = h;
	}
	public void setClause(Clause c) {
		this.clause = c;
	}
	
	public class UnknownMessageTypeException extends Exception {
		static final long serialVersionUID = 1;
		public UnknownMessageTypeException(String a) {
			super(a);
		}
	}
}

