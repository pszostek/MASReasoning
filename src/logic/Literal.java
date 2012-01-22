package logic;

public class Literal {
	private char name;
	private boolean value;
	public Literal(char n)
	{
		name = n;
		value = true;
	}
	public Literal(char n_name, boolean n_value)
	{
		name=n_name;
		value=n_value;
	}
	public boolean not()
	{
		return value = (value == true ? false : true);
	}
	public char getValue()
	{
		return name;
	}
}
