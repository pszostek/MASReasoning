package messaging;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
public class History implements Serializable{
	private List<HistEl> lista;
	
	public History()
	{
		lista = new ArrayList<HistEl>();
	}
	public History(History his)
	{
		if(his.lista.size() == 0)
			lista=new ArrayList<HistEl>();
		else {
			try{	
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(his);
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				ObjectInputStream ois = new ObjectInputStream(bais);
				History deepCopy = (History)ois.readObject();
				lista=deepCopy.getList();
			}catch(Exception e)
			{
				System.out.println("Blad przy kopiowaniu historii "+e);
			}
		}
	}
	public boolean equals(Object other) {
		if(!(other instanceof History))
			return false;
		History o = (History)other;
		if(o.lista.size() != lista.size())
			return false;
		for(int i=0; i<lista.size(); ++i)
			if(!(lista.get(i).equals(o.lista.get(i))))
				return false;
		return true;
	}
	public boolean isEmpty() {
		return lista.size() == 0;
	}
	public List<HistEl> getList()
	{
		return lista;
	}
	public HistEl getPreviousElement(int stepsBack) {
		return lista.get(lista.size()-(stepsBack+1));
	}
	public History pop() {
		lista.remove(lista.size()-1);
		return this;
	}
	public int size() {
		return lista.size();
	}
	public History pop(int howMany) {
		for(int i=0; i<howMany; ++i)
			lista.remove(lista.size()-1);
		return this;
	}
	public History push(HistEl n_HistEl)
	{
		lista.add(n_HistEl);
		return this;
	}
	public HistEl peekLast() {
		return lista.get(lista.size()-1);
	}
	public boolean contains(HistEl el) {
		for(HistEl elem: lista) {
			if(el.getClause() != null)
				if(! el.getClause().equals(elem.getClause())) {
					continue;
				}
			if(el.getLiteral() != null)
				if(! el.getLiteral().equals(elem.getLiteral())) {
					continue;
				}
			if(el.getClause() != null)
				if(! el.getAgentId().equals(elem.getAgentId())) {
					continue;
				}
			return true;
		}
		return false;
	}
}
