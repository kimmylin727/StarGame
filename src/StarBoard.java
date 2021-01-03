
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;

import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StarBoard extends Component
{
	private static BufferedImage img;
	static int[]xpos = {385,363,410,335,389,433,315,361,410,456,94,144,193,242,290,341,384,
			436,482,528,578,626,673,122,171,216,265,317,364,413,458,507,551,602,647,145,193,
			242,294,340,383,439,485,530,577,621,173,220,269,314,361,410,461,510,554,604,194,
			243,291,337,385,435,482,532,570,168,217,267,318,364,411,456,508,555,604,149,197,
			243,291,338,384,436,486,538,577,626,119,166,215,261,307,357,411,459,508,555,
			601,648,96,147,195,243,289,337,386,434,483,534,578,627,678,314,363,411,459,340,
			385,436,363,411,389};

	static int[]ypos = {53,96,96,138,138,138,180,180,180,180,219,219,219,219,219,219,219,219,
			219,219,219,219,219,257,261,261,261,261,261,261,261,261,261,261,261,302,
			302,302,302,302,302,302,302,302,302,302,343,343,343,343,343,343,343,343,
			343,343,388,388,388,388,388,388,388,388,388,429,429,429,429,429,429,429,
			429,429,429,470,470,470,470,470,470,470,470,470,470,470,510,510,510,510,
			510,510,510,510,510,510,510,510,553,553,553,553,553,553,553,553,553,553,
			553,553,553,593,593,593,593,634,634,634,677,677,720};
	
	public Position[] allPossible;//all possible positions on the board
	
	Player player;
	Player computer;
	
	BufferedImage black;
	BufferedImage pink;
	BufferedImage bHigh;
	BufferedImage pHigh;
	BufferedImage outline;
	BufferedImage[]num=new BufferedImage[10];
	
	static Position[]bpos = new Position[10];//black positions
	static Position[]ppos = new Position[10];//pink positions
	static int clickcount = 0;
	
	final Position[] home1= new Position[10];//computer home
	final Position[] home2= new Position[10];//player home

	Position inToBeMoved;
	int toBeMoved = -1;

	JButton b=new JButton("End Turn");
	JButton dir=new JButton("Directions");
	static boolean endTurn=false;
	
	static int adjCount=0;
	static int compCount;
	
	JFrame myFr = null;
	
	public StarBoard(JFrame fr)
	{
		compCount=0;
		myFr = fr;
		for (int i =0; i < 10; i++)
		{
			home1[i]=new Position(xpos[i],ypos[i]);
			home2[i]=new Position(xpos[120-i],ypos[120-i]);
		}
		allPossible = new Position[121];
		for(int i = 0; i < xpos.length; i++)
		{
			allPossible[i] = new Position(xpos[i], ypos[i]);
		}
		try
		{
			URL url = StarGame.class.getResource("/FinalStarBoard.jpg");
			img = ImageIO.read(url);
			//img = ImageIO.read(new File("FinalStarBoard.jpg"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			URL url = StarGame.class.getResource("/outline.png");
			outline = ImageIO.read(url);
			//img = ImageIO.read(new File("FinalStarBoard.jpg"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			URL url = StarGame.class.getResource("/zero.png");
			num[0] = ImageIO.read(url);
			//black = ImageIO.read(new File("Black Marble.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			URL url = StarGame.class.getResource("/one.png");
			num[1] = ImageIO.read(url);
			//black = ImageIO.read(new File("Black Marble.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			URL url = StarGame.class.getResource("/two.png");
			num[2] = ImageIO.read(url);
			//black = ImageIO.read(new File("Black Marble.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			URL url = StarGame.class.getResource("/three.png");
			num[3] = ImageIO.read(url);
			//black = ImageIO.read(new File("Black Marble.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			URL url = StarGame.class.getResource("/four.png");
			num[4] = ImageIO.read(url);
			//black = ImageIO.read(new File("Black Marble.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			URL url = StarGame.class.getResource("/five.png");
			num[5] = ImageIO.read(url);
			//black = ImageIO.read(new File("Black Marble.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			URL url = StarGame.class.getResource("/six.png");
			num[6] = ImageIO.read(url);
			//black = ImageIO.read(new File("Black Marble.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			URL url = StarGame.class.getResource("/seven.png");
			num[7] = ImageIO.read(url);
			//black = ImageIO.read(new File("Black Marble.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			URL url = StarGame.class.getResource("/eight.png");
			num[8] = ImageIO.read(url);
			//black = ImageIO.read(new File("Black Marble.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			URL url = StarGame.class.getResource("/nine.png");
			num[9] = ImageIO.read(url);
			//black = ImageIO.read(new File("Black Marble.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			URL url = StarGame.class.getResource("/Black Marble.png");
			black = ImageIO.read(url);
			//black = ImageIO.read(new File("Black Marble.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int x = 0; x < 10; x++)
		{
			bpos[x] = new Position(xpos[xpos.length-x-1]-16, ypos[ypos.length-x-1]-16);
		}
		try 
		{
			URL url = StarGame.class.getResource("/HBlack Marble.png");
			bHigh = ImageIO.read(url);
			//bHigh = ImageIO.read(new File("HBlack Marble.png"));
		}catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player = new Player(black, bHigh, bpos);
		for(int i = 0; i < 10; i++)
		{
			try
			{
				URL url = StarGame.class.getResource("/Pink Marble.png");
				pink = ImageIO.read(url);
				//pink = ImageIO.read(new File("Pink Marble.png"));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int x = 0; x < 10; x++)
		{
			ppos[x] = new Position(xpos[x]-16, ypos[x]-16);
		}
		try 
		{
			URL url = StarGame.class.getResource("/PPink Marble.png");
			pHigh = ImageIO.read(url);
			//pHigh = ImageIO.read(new File("PPink Marble.png"));
		}catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		computer = new Player(pink, pHigh, ppos);
		dir.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {					
				String description="To Play: \n1. You may only move the black marbles. The pink marbles are played by your opponent(the computer gods).\n2. "
						+ "Each turn, you may choose to either move to an adjacent empty location(referring to the six circles direcly surrounding a location) "
						+ " or hop over one or more adjacent \nmarbles, as long as your destination is not occupied by another"
						+ " marble.\n3. You may also hop over marbles further away but in the same direction as one of your adjacent locations as long as "
						+ "there are no other marbles between your current \nlocation, the marble you are hopping over, and your destination. In this case, the"
						+ " distance you move is about double the distance between your marble's current location\nand the marble(should be exactly in the middle "
						+ "of your current location and your intended destination) you are hopping over. For "
						+ "example, if the middle marble you are\nhopping over is 'x' circles away, your destination must be 'x' circles away from the middle "
						+ "marble. \n4. Press"
						+ " \"End Turn\" when you are done with your move. \n5. The"
						+ " numbers left by the opponent's marbles show you the route that an opponent's particular marble(bordered pink) has chosen to. "
						+ "take\n HAVE FUN!";
				JOptionPane.showMessageDialog(null, description,"Welcome!", JOptionPane.INFORMATION_MESSAGE,null);
				}
		});
		
		b.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
					if(checkWin()==null)
					{
						if(clickcount>0)
						{
							StarGame.botMove();
							myFr.repaint();
							clickcount=0;
							adjCount=0;
							endTurn=false;
							compCount++;
						}
						else
						{
							
						}
						if(checkWin() == computer)
						{
							lose();
							URL url = StarGame.class.getResource("/muck1.png");
							ImageIcon muck = new ImageIcon(url);
							JOptionPane.showMessageDialog(null, "You lose...","Message", JOptionPane.INFORMATION_MESSAGE,muck);
							System.exit(1); 
						}
						else if(checkWin() == player)
						{
							win();
							URL url = StarGame.class.getResource("/pikachu1.png");
							ImageIcon pika = new ImageIcon(url);
							JOptionPane.showMessageDialog(null, "You Win!","Message", JOptionPane.INFORMATION_MESSAGE,pika);
							System.exit(1);
						}
						//board
					}
				}
		});
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent me)//all movements happen here
			{	
				if(clickcount==0)//check if player clicked on one of its existing marbles
				{
					inToBeMoved = null;
					toBeMoved = -1;
					Position p_temp = assignToArray(new Position(me.getX(), me.getY()));
					 
					if(StarGame.checkEmpty(p_temp))
					{
						return;
					}
					for(int i = 0; i < player.positions.length; i++)
					{
						if(p_temp.getRow()==player.positions[i].getRow()+16&&p_temp.getColumn()==player.positions[i].getColumn()+16)
						{
							toBeMoved = i;
							clickcount++;
							inToBeMoved = p_temp;
							repaint();
							break;
						}	
					}
				}	
				else 
				{	
					Position y = assignToArray(new Position(me.getX(),me.getY()));
//					System.out.println(y.getRow()+" "+y.getColumn());
					if(y == null)
					{
 						return;
					}
					
					if(!StarGame.checkEmpty(y))
					{
						return;
					}
					
					if(adjCount>0)
						return;
					if(StarGame.isValidMove(inToBeMoved, y))
					{
						sound();
						bpos[toBeMoved] = calibrate(y); 
						clickcount++;
						repaint();
						inToBeMoved=y;
					}
					if(checkWin() == computer)
					{
						lose();
						URL url = StarGame.class.getResource("/muck1.png");
						ImageIcon muck = new ImageIcon(url);
						//ImageIcon muck = new ImageIcon("muck1.png");
						JOptionPane.showMessageDialog(null, "You lose...","Message", JOptionPane.INFORMATION_MESSAGE,muck);
						System.exit(1); 
					}
					else if(checkWin() == player)
					{
						win();
						URL url = StarGame.class.getResource("/pikachu1.png");
						ImageIcon pika = new ImageIcon(url);
						//ImageIcon pika = new ImageIcon("pikachu1.png");
						JOptionPane.showMessageDialog(null, "You Win!","Message", JOptionPane.INFORMATION_MESSAGE,pika);
						System.exit(1);
					}
					
				}
			}
		});
	}
	int count=0;
	public void paint(Graphics g)
	{
		g.drawImage(img, 0, 0, null);
		for(int i = 0; i < 10; i++)
		{
			g.drawImage(computer.img, ppos[i].getRow(), ppos[i].getColumn(), null);
		}
		for(int x = 0; x < 10; x++)
		{
			g.drawImage(player.img, bpos[x].getRow(), bpos[x].getColumn(), null);
		}
		if(inToBeMoved!=null&&clickcount>0&&toBeMoved!=-1)
		{
			g.drawImage(player.highlight, bpos[toBeMoved].getRow(), bpos[toBeMoved].getColumn(),null);
		}
		if(clickcount==0&&compCount>0)
		{
			for(int i=0; i<StarGame.path.size()-1; i++)
			{
				g.drawImage(num[count++], calibrate(StarGame.path.get(i)).getRow(), calibrate(StarGame.path.get(i)).getColumn(),null);
				if(count>=num.length)
					count=0;
			}
			count=0;//if path length longer than 9 reset counting scheme
			g.drawImage(computer.highlight, calibrate(StarGame.to).getRow(), calibrate(StarGame.to).getColumn(),null);
		}
	}
	
	/**
	 * Calibrates position of marble pictures
	 * @param x ppos, bpos location
	 * @return position in allPossible array
	 */
	public Position calibrate(Position x)
	{
		if(x!=null)
		{
			Position y = new Position(x.getRow()-16, x.getColumn()-16);
			return y;
		}
		return null;
	}
	
	/**
	 * Gets closest position from allPossible array
	 * @param x position to be assigned
	 * @return position in allPossible that corresponds to Position x
	 */
	public Position assignToArray(Position x)
	{
		for(int i = 0; i < allPossible.length; i++)
		{
			if(Math.abs(x.getRow()-allPossible[i].getRow())<17&&Math.abs(x.getColumn()-allPossible[i].getColumn())<17)
			{
//				System.out.print(i+" ");
				return allPossible[i];
			}
		}
		return null;
	}
	
	/**
	 * Basically assignToArray but smaller range
	 */
	public Position assignMiddle(Position x)
	{
		for(int i = 0; i < allPossible.length; i++)
		{
			if(Math.abs(x.getRow()-allPossible[i].getRow())<10&&Math.abs(x.getColumn()-allPossible[i].getColumn())<10)
			{
//				System.out.print(i+" ");
				return allPossible[i];
			}
		}
		return null;
	}
	
	/**
	 * @return index of @param x in @param array
	 */
	public int indexOf(Position x, Position[] array)
	{
		for(int i = 0; i < array.length; i++)
		{
			if(x!=null)
			{
				if(x.equals(array[i]))
					return i;
			}
		}
		return -1;
	}
	
	/**
	 * @return index of @param x in @param array
	 */
	public int indexOf(Position x, ArrayList<Position> array)
	{
		for(int i = 0; i < array.size(); i++)
		{
			if(x!=null)
			{
				if(x.getRow() == array.get(i).getRow()&&x.getColumn()==array.get(i).getColumn())
					return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns all the positions surrounding one particular location
	 * @param x center position
	 * @return arrayList containing all adjacent positions
	 */
	public ArrayList<Position> surroundPosition(Position x)//returns all the positions of surrounding marbles
	{
//		System.out.println("Position: "+x.getRow()+" "+x.getColumn());
		ArrayList<Position>smarbles = new ArrayList<Position>();
		Position temp = new Position(x.getRow()-48,x.getColumn());
//		System.out.print("1: ");
		smarbles.add(assignToArray(temp));
		temp = new Position(x.getRow()-24,x.getColumn()-40);
//		System.out.print("2: ");
		smarbles.add(assignToArray(temp));
		temp = new Position(x.getRow()+24,x.getColumn()-40);
//		System.out.print("3: ");
		smarbles.add(assignToArray(temp));
		temp = new Position(x.getRow()+48, x.getColumn());
//		System.out.print("4: ");
		smarbles.add(assignToArray(temp));
		temp = new Position(x.getRow()+24, x.getColumn()+40);
//		System.out.print("5: ");
		smarbles.add(assignToArray(temp));
		temp = new Position(x.getRow()-24,x.getColumn()+40);
//		System.out.print("6: ");
		smarbles.add(assignToArray(temp));
		for(int i = smarbles.size()-1; i>=0; i--)
		{
			if(smarbles.get(i)==null)
			{
				smarbles.remove(i);
			}
		}
		return smarbles;
	}

	public Player getPlayer()
	{
		return player;
	}
	public Player getComputer()
	{
		return computer;
	}
	
	/**
	 * Checks if a position is within the range of some valid location
	 * @param x position to be checked 
	 * @return true is x is in bounds, false otherwise
	 */
	public boolean inBounds(Position x)
	{
		if(indexOf(x, allPossible)!=0)
			return true;
		else
			return false;
	}
	
	/**
	 * @return the winning player, null if no winner
	 */
	public Player checkWin()//returns player that wins, else returns null
	{
//		return computer;
		boolean pl = true;
		boolean comp = true;
		for(int i=0; i<10; i++)
		{
			Position ptemp = new Position(player.positions[i].getRow()+16, player.positions[i].getColumn()+16);
			Position ctemp = new Position(computer.positions[i].getRow()+16, computer.positions[i].getColumn()+16);
			if(indexOf(ptemp, home1)==-1)
				pl = false;		
			if(indexOf(ctemp,home2)==-1)
			{
				comp = false;
			}
		}
		if(pl)
		{
			return player;
		}
		else if(comp)
		{
			return computer;
		}
		return null;
	}
	
	/**
	 * The really cool clicky sound at each move
	 */
	public static void sound()
	{
		try
		{
			String fp="C:\\Kimmy\\FStarGame\\resources\\MSound.wav";
			SimpleAudioPlayer music = new SimpleAudioPlayer(fp);
			music.play();

		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error");
		}
	}
	
	/**
	 * Happy pikachu denoting victory
	 */
	public static void win()
	{
		try
		{
			String fp="C:\\Kimmy\\FStarGame\\resources\\Yay.wav";
			SimpleAudioPlayer music = new SimpleAudioPlayer(fp);
			music.play();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error");
		}
	}
	
	/**
	 * Sad muck denoting loss
	 */
	public static void lose()
	{
		try
		{
			String fp="C:\\Kimmy\\FStarGame\\resources\\Booo.wav";
			SimpleAudioPlayer music = new SimpleAudioPlayer(fp);
			music.play();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error");
		}
	}
	
	/**
	 * Initializes window
	 */
	public void display(JFrame fr)
	{
		fr.getContentPane().add(new StarBoard(myFr));
		fr.setSize(1000,1000);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		StarBoard ic = new StarBoard(myFr);
		fr.add(ic);
		
		JPanel panel = new JPanel(); 
		panel.add(b);
		fr.add(panel,BorderLayout.NORTH);
		
		JPanel panel2 = new JPanel(); 
		panel2.add(dir);
		fr.add(panel2,BorderLayout.LINE_START);

		fr.setVisible(true);
	}
}
