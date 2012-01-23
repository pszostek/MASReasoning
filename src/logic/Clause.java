package logic;

import java.util.ArrayList;
import java.util.List;

public class Clause {
	private boolean isEmpty;
	private boolean isFalse;
	private boolean isTrue;
	private List<Literal> lista;

	public static Clause emptyClause() {
		Clause c = new Clause();
		c.setEmpty();
		return c;
	}

	public Clause(String in) {
		int i = 0;
		int j = 0;
		while (i > -1 && in.length() > i) {
			j = in.indexOf('+');
			if (j >= 0) {
				lista.add(new Literal(in.substring(i, j)));
				i = j + 1;
			} else {
				lista.add(new Literal(in.substring(i)));
				i = -1;
			}
		}
	}

	public static Clause trueClause() {
		Clause c = new Clause();
		c.setTrue();
		return c;
	}

	public static Clause falseClause() {
		Clause c = new Clause();
		c.setFalse();
		return c;
	}

	public Clause(Literal l) {
		lista = new ArrayList<Literal>();
		lista.add(l);
	}

	public Clause() {
		lista = new ArrayList<Literal>();
	}

	public List<Literal> getLiterals() {
		return lista;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	private void setEmpty() {
		isEmpty = true;
	}

	public boolean isTrue() {
		return isTrue;
	}

	private void setTrue() {
		isTrue = true;
	}

	public boolean isFalse() {
		return isFalse;
	}

	private void setFalse() {
		isFalse = true;
	}

	public void add(Literal literal) {
		lista.add(literal);
	}

	public boolean equals(Object other) {
		if( !(other instanceof Clause))
			return false;
		Clause c = (Clause)other;
		if(this.getLiterals().size() != c.getLiterals().size())
			return false;
		for(Literal i:lista) {
			if(!c.getLiterals().contains(i))
				return false;
		}
		return true;
	}
	
	public Clause getClause(Literal lit) {

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getName() == lit.getName()
					&& lista.get(i).getValue() != lit.getValue()) {
				lista.remove(i);
			}
		}
		return this;
	}
	/*
	 * public void check() { boolean check=false; for (int
	 * i=0;i<lista.size();i++) { check=(check||lista.get(i).getValue()); } if
	 * (check) { isTrue=true; isFalse=false; } else { isTrue=false;
	 * isFalse=true; } }
	 */
}
