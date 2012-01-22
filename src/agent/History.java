package agent;
import java.util.ArrayList;
import java.util.List;
public class History {
	private List<HistEl> lista;

	public HistEl getPreviousElement(int stepsBack) {
		return lista.get(lista.size()-stepsBack);
	}
	public History pop() {
		lista.remove(lista.size()-1);
		return this;
	}
	public History push(HistEl n_HistEl)
	{
		lista.add(n_HistEl);
		return this;
	}
}
