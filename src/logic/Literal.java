package logic;

public class Literal {
	private String name;
	private boolean value;
	public Literal()
	{
		name = null;
		value = false;
	}
	public Literal(String n_name, boolean n_value)
	{
		name=n_name;
		value=n_value;
	}
	public boolean not ()
	{
		return value ? false  : true;
	}
	public boolean getValue()
	{
		return value;
	}
}
