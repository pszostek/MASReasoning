package messaging;

import java.io.Serializable;
import logic.Literal;
import java.util.HashSet;

public class KnowledgeDiscoveryMessage implements Serializable {
	private HashSet<Literal> literals;
	private boolean isAnswer;
	public boolean isAnswer() {
		return isAnswer;
	}

	public void setAnswer(boolean isAnswer) {
		this.isAnswer = isAnswer;
	}

	public HashSet<Literal> getLiterals() {
		return literals;
	}

	public void setLiterals(HashSet<Literal> literals) {
		this.literals = literals;
	}

	public static final long serialVersionUID = 1;

	public KnowledgeDiscoveryMessage(HashSet<Literal> literals, boolean isAnswer) {
		this.literals = literals;
		this.isAnswer = isAnswer;
	}
}
