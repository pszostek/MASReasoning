package agent;
import logic.*;

public class HistEl {
	private Literal literal;
	private int agent_id;
	private Clause clause;
	public HistEl ()
	{
		agent_id=-1;
		clause=new Clause();
	}
	public HistEl(Clause n_clause, int n_agent_id, Literal l)
	{
		agent_id=n_agent_id;
		clause=n_clause;
		literal=l;
	}

	public int getAgentId()
	{
		return agent_id;
	}
	public Clause getClause()
	{
		return clause;
	}
	public void setAgentId(int n_agent_id)
	{
		agent_id=n_agent_id;
	}
	public void setClause(Clause n_clause)
	{
		clause =n_clause;
	}
	public void setLiteral(Literal l)
	{
		literal = l;
	}
	
}
