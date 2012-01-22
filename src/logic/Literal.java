package logic;

public class Literal {
	private char name;
	private boolean value;
	public Literal(char n)
	{
		name = n;
		value = true;
	}
	public Literal(Clause c) {
		 this.value = c.getLiterals().get(0).getValue();
		 this.name = c.getLiterals().get(0).getName();
	}
	public Literal(char n_name, boolean n_value)
	{
		name=n_name;
		value=n_value;
	}
	public Literal not()
	{
		this.value = (this.value == true ? false : true);
		return this;
	}
	public boolean getValue()
	{
		return value;
	}
	public char getName()
	{
		return name;
	}
	
}
