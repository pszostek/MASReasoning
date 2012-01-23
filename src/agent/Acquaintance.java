package agent;

import java.util.ArrayList;
import java.util.List;
import logic.Literal;

/* klasa do trzymania informacji o sąsiadach */
public class Acquaintance {
	List<Integer> agenci;
	List<List<Literal>> literaly;

	public List<Integer> getAgenci() {
		return agenci;
	}

	public List<Integer> getSasiedzi(Literal lit) {
		List<Integer> sasiedzi = new ArrayList<Integer>();
		for (int i = 0; i < agenci.size(); i++) {
			for (int j = 0; j < literaly.get(j).size(); j++) {
				if (literaly.get(i).get(j).getName() == lit.getName()) {
					sasiedzi.add(agenci.get(i));
					j = literaly.get(i).size();
				}
			}
		}
		return sasiedzi;
	}

	public void update(int agentId, Literal lit) {
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