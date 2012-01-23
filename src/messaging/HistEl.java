package messaging;
import logic.*;
import jade.core.AID;

public class HistEl {
	private Literal literal;
	private AID agent_id;
	private Clause clause;
	public HistEl ()
	{
		agent_id=new AID();
		clause=new Clause();
	}
	public HistEl(Literal l, AID n_agent_id, Clause n_clause)
	{
		agent_id=n_agent_id;
		clause=n_clause;
		literal=l;
	}

	public AID getAgentId()
	{
		return agent_id;
	}
	public Clause getClause()
	{
		return clause;
	}
	public void setAgentId(AID n_agent_id)
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
	public Literal getLiteral() {
		return literal;
	}
}
