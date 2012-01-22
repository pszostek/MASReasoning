package agent;
import java.util.ArrayList;
import java.util.List;
import logic.Literal;

/* klasa do trzymania informacji o sąsiadach */
public class Acquaintance {
	List<ReasoningAgent> agenci;
	List<List<Literal>>literaly;
	public List<ReasoningAgent> getAgenci()
	{
		return agenci;
	}
	public List<ReasoningAgent> getSasiedzi(Literal lit)
	{
		List<ReasoningAgent> sasiedzi=new ArrayList();
		for(int i=0;i<agenci.size();i++)
		{
			for(int j=0;j<literaly.get(j).size();j++)
			{
				if (literaly.get(i).get(j).getName()== lit.getName())
				{
					sasiedzi.add(agenci.get(i));
					j=literaly.get(i).size();
				}
			}
		}
		return sasiedzi;
	}

}