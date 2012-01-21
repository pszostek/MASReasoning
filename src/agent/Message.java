package agent;

import java.io.Serializable;
import jade.lang.acl.ACLMessage;

public class Message implements Serializable {
	public enum  MessageType {FORTH, BACK, FINAL};
	
	private MessageType type;
	private History hist;
	
	public History getHistory(){
		return new History();
	}
	public MessageType getType() {
		return MessageType.FORTH;
	}
	public class UnknownMessageTypeException extends Exception {
		public UnknownMessageTypeException(String a) {
			super(a);
		}
	}
}

