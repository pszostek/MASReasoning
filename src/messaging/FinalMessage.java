package messaging;

import logic.Clause;

public class FinalMessage extends Message {
	static final long serialVersionUID = 1;
	
	public FinalMessage(History h, Clause c) {
		super(h,c);
	}
}
