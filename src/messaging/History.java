package messaging;
import java.util.List;
import java.util.ArrayList;
public class History implements Cloneable{
	private List<HistEl> lista;
	
	public History()
	{
		lista = new ArrayList<HistEl>();
	}
	public History(History his)
	{
		try{
		History tmp=(History)his.clone();
		lista=tmp.getList();
		}
		catch(CloneNotSupportedException e)
		{
			System.out.println("wyjatek przy klonowaniu: "+e);
		}
		/*
		lista = new ArrayList<HistEl>();
		
		for(int i=0;i<his.getList().size();i++)
		{
			lista.add(new HistEl(his.getList().get(i)));
		}
		*/
	}
	public List<HistEl> getList()
	{
		return lista;
	}
	public HistEl getPreviousElement(int stepsBack) {
		return lista.get(lista.size()-(stepsBack+1));
	}
	public HistEl pop() {
		return lista.remove(lista.size()-1);
	}
	public History push(HistEl n_HistEl)
	{
		lista.add(n_HistEl);
		return this;
	}

	public boolean contains(HistEl el) {
		for(HistEl elem: lista) {
			if(el.getClause() != null)
				if(el.getClause() != elem.getClause()) {
					continue;
				}
			if(el.getLiteral() != null)
				if(el.getLiteral() != elem.getLiteral()) {
					continue;
				}
			if(el.getClause() != null)
				if(el.getAgentId() != elem.getAgentId()) {
					continue;
				}
			return true;
		}
		return false;
	}
}
