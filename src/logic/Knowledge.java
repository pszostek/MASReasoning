package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Knowledge {
	private List<Clause> lista;

	public List<Clause> getList() {
		return lista;
	}

	public Knowledge() {
		this.lista = new ArrayList<Clause>();
	}

	public Knowledge(String knowledgeString) {
		this.setContent(knowledgeString);
	}

	public void setClause(Clause n_clause) {
		lista.add(n_clause);
	}

	public boolean contains(Literal l) {
		Clause literalAsClause = new Clause(l);
		if(lista.contains(literalAsClause))
			return true;
		else
			return false;
	}
	public HashSet<Literal> getAllLiterals() {
		HashSet<Literal> ret = new HashSet<Literal>();
		for(Clause c: lista) {
			List<Literal> literals = c.asLiterals();
			for(Literal l: literals) {
				ret.add(new Literal(l.getName()));
			}
		}
		return ret;
	}
	public List<Clause> getResult(Literal lit) {
		List<Clause> wnioski = new ArrayList<Clause>();
		for (int i = 0; i < lista.size(); i++) {
			Clause tmp=lista.get(i).getClause(lit);
			if(tmp !=null)
				wnioski.add(tmp);
		}
		if (wnioski.size() == 0) {
			wnioski.add(Clause.emptyClause());
		}
		return wnioski;
	}

	public void setContent(String in) {
		if(lista==null)
			lista=new ArrayList<Clause>();
		int i = 0;
		int j = 0;
		while (i > -1 && in.length() > i) {
			j = in.indexOf(',', i);
			if (j >= 0) {
				lista.add(new Clause(in.substring(i, j)));
				i = j + 1;
			} else {
				lista.add(new Clause(in.substring(i)));
				i = -1;
			}
		}
	}
}
