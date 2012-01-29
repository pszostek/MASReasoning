package messaging;

import java.io.Serializable;
import logic.Literal;
import java.util.HashSet;

public class KnowledgeDiscoveryMessage implements Serializable {
	private HashSet<Literal> literals;
	public HashSet<Literal> getLiterals() {
		return literals;
	}

	public void setLiterals(HashSet<Literal> literals) {
		this.literals = literals;
	}

	public static final long serialVersionUID = 1;

	public KnowledgeDiscoveryMessage(HashSet<Literal> literals) {
		this.literals = literals;
	}
}
