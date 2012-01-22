package logic;
import java.util.ArrayList;
import java.util.List;
public class Knowledge {
	private List<Clause> lista;
	public List<Clause> getList()
	{
		return lista;
	}
	public void setClause(Clause n_clause)
	{
		lista.add(n_clause);
	}

}
