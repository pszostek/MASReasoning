package agent;
import logic.*;
public class HistEl {
	private int agent_id;
	private Clause klauzura;
	public HistEl ()
	{
		agent_id=-1;
		klauzura=new Clause();
	}
	public HistEl(int n_agent_id, Clause n_clause)
	{
		agent_id=n_agent_id;
		klauzura=n_clause;
	}
	public int getAgentId()
	{
		return agent_id;
	}
	public Clause getClause()
	{
		return klauzura;
	}
	public void setAgentId(int n_agent_id)
	{
		agent_id=n_agent_id;
	}
	public void setClause(Clause n_clause)
	{
		klauzura =n_clause;
	}
}
