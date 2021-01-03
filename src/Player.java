
import java.awt.image.BufferedImage;

public class Player 
{
	BufferedImage img;
	BufferedImage highlight;
	Position[] positions;//each index of positions represents the marble itself
	/*Each player will have its own image and array of positions. The image will change according to solely the position array*/
	public Player(BufferedImage x, BufferedImage highlight2, Position[] pos)
	{
		img = x;
		positions = pos;
		highlight = highlight2;
	}
	public Position[] getPosition()
	{
		return positions; 
	}
}