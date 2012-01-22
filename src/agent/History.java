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
	public boolean contains(HistEl el) {
		//ma sprawdzić, czy historia zawiera dany element, przy czym zarówno agent,jak i klauzula mogą być równe null
		//wtedy sprawdza tylko, czy jest element, w którym niezerowe pola się pokrywają
	}

}
