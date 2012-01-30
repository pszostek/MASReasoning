package agent;

import java.util.ArrayList;
import java.util.List;
import jade.core.AID;
import logic.Clause;
import logic.Literal;

/* klasa do trzymania informacji o sąsiadach */
public class Acquaintance {
	List<AID> agenci;
	List<List<Literal>> literaly;

	public Acquaintance(String s) {
		this.agenci = new ArrayList<AID>();
		String[] ints = s.split(".");
		//dodaj identyfikatory do listy sasiadow
		for(String i: ints)
			agenci.add(new AID(i, AID.ISLOCALNAME));
		//zainicjalizuj literaly agentow pustymi tablicami
		for(int i=0; i<agenci.size(); ++i)
			literaly.add(new ArrayList<Literal>());
	}
	public List<AID> getAgenci() {
		return agenci;
	}
	public int size() {
		return agenci.size();
	}
	public Boolean isWholeClauseShared(Clause c) {
		for(Literal inputLiteral: c.asLiterals()) {
			for(List<Literal> agentLiterals: literaly)
				for(Literal carriedLiteral: agentLiterals) {
				if(!carriedLiteral.equalLetter(inputLiteral))
					return false;
			}
		}
		return true;
	}

	public int neighboursKnown() {
		int ret = 0;
		for(int i=0; i<literaly.size(); ++i)
			if(literaly.get(i).size() != 0)
				++ret;
		return ret;
	}

	public List<AID> getSasiedzi(Literal lit) {
		List<AID> sasiedzi = new ArrayList<AID>();
		for (int i = 0; i < agenci.size(); i++) {
			for (int j = 0; j < literaly.get(i).size(); j++) {
				if (literaly.get(i).get(j).getName() == lit.getName()) {
					sasiedzi.add(agenci.get(i));
					j = literaly.get(i).size();
				}
			}
		}
		return sasiedzi;
	}

	public void update(AID agentId, Literal lit) {
		boolean flaga = true;
		for (int i = 0; i < agenci.size(); i++) {
			if (agenci.get(i) == agentId) {
				for (int j = 0; j < literaly.get(i).size(); j++) {
					if (literaly.get(i).get(j).getName() == lit.getName()) {

						literaly.get(i).add(lit);
					}
				}
				flaga = false;
			}
		}
		if (flaga) {
			agenci.add(agentId);
			List<Literal> tmp = new ArrayList<Literal>();
			tmp.add(lit);
			literaly.add(tmp);
		}
	}
}