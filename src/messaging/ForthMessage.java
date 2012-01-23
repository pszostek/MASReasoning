package messaging;

import logic.Clause;

public class ForthMessage extends Message{
	static final long serialVersionUID = 1;
	
	public ForthMessage(History h, Clause c) {
		super(h,c);
	}
}
