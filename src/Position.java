
public class Position
{
	private int xcoord, ycoord; //uses pixels
	boolean visited;
	public Position(int x, int y)
	{
		xcoord = x;
		ycoord = y;
		visited =false;
	}
	public int getRow()
	{
		return xcoord;
	}
	public int getColumn()
	{
		return ycoord;
	}
	public int distance(Position x)
	{
		return (int)Math.sqrt(Math.pow(x.xcoord-this.xcoord, 2)+Math.pow(x.ycoord-this.ycoord, 2));
	}
	public boolean equals(Position x)
	{
		if(x.getRow()==xcoord&&x.getColumn()==ycoord)
			return true;
		else
			return false;
	}

}
