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

	public Clause()
	{
		lista= new ArrayList<Literal>();
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
	public void add(Literal literal)
	{
		lista.add(literal);
	}
	/*
	public void check()
	{
		boolean check=false;
		for (int i=0;i<lista.size();i++)
		{
			check=(check||lista.get(i).getValue());
		}
		if (check)
		{
			isTrue=true;
			isFalse=false;
		}
		else
		{
			isTrue=false;
			isFalse=true;
		}
	}*/
}
