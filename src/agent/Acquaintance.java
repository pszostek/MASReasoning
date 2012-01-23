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
    {
        boolean flaga=true;
        for(int i=0;i<agenci.size();i++)
        {
                if(agenci.get(i).getAgentId()==agentId)
                {
                        for(int j=0;j<literaly.get(i).size();j++)
                        {
                                if(literaly.get(i).get(j).getName()==lit.getName())
                                {
                                        
                                        literaly.get(i).add(lit);
                                }
                        }
                        flaga=false;
                }
        }
        if(flaga)
        {
                agenci.add(new ReasoningAgent (agentId));
                List<Literal> tmp =new ArrayList();
                tmp.add(lit);
                literaly.add(tmp);
        }
}
}