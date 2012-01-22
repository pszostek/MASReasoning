package logic;
import java.util.ArrayList;
import java.util.List;

public class Clause {
	private boolean isEmpty;
	private boolean isFalse;
	private boolean isTrue;
	private List<Literal> lista;

	public static Clause emptyClause() {
		return new Clause()
	}
	public Clause(boolean isEmpty = false, boolean isTrue = false, boolean isFalse = false)
	{
		lista= new ArrayList<Literal>();
		check();
	}
	public boolean isEmpty() {
		return isEmpty;
	}
	public boolean isTrue() {
		return isTrue;
	}
	public boolean isFalse() {
		return isFalse;
	}
	public void add(Literal literal)
	{
		lista.add(literal);
		isEmpty=true;
	}
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
	}
}
