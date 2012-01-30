package messaging;
import logic.*;
import jade.core.AID;
import java.io.Serializable;

public class HistEl implements Serializable{
	private Literal literal;
	private AID agent_id;
	private Clause clause;
	public HistEl ()
	{
		agent_id=new AID();
		clause=new Clause();
	}
	public HistEl(Literal l,AID n_agent_id, Clause n_clause)
	{
		literal=l;
		agent_id=n_agent_id;
		clause=n_clause;

	}
	public HistEl(HistEl his)
	{
		agent_id=his.getAgentId();
		clause=new Clause(his.getClause());
		literal=new Literal(his.getLiteral());
	}
	public boolean equals(Object other) {
		if(!(other instanceof HistEl))
			return false;
		HistEl o = (HistEl)other;
		return (literal.equals(o.literal) &&
		   agent_id.equals(o.agent_id) &&
		   clause.equals(o.clause)
			);
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
