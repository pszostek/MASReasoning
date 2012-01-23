package messaging;

import logic.Clause;

public class BackMessage extends Message {
	static final long serialVersionUID = 1;
	
	public BackMessage(History h, Clause c) {
		super(h,c);
	}
}
