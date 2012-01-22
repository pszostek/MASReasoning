package agent;
import java.util.List;
public class History {
	private List<HistEl> lista;
	
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
}
