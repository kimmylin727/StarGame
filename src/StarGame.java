import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StarGame 
{
	static StarBoard board;
	int count = 0;
	JFrame fr = new JFrame("StarBoard Game");
	@SuppressWarnings("unchecked")
	static LinkedList<Position>[] alist=new LinkedList[121];//adjacency list
	@SuppressWarnings("unchecked")
	static LinkedList<Position>[] list=new LinkedList[121];//immediate version of alist
	final static Position DEFAULT=new Position(0,0);
	static Position origPos=DEFAULT;
	public StarGame()
	{
		board = new StarBoard(fr);

		for(int i=0; i<list.length; i++)
		{
			list[i]=new LinkedList<Position>();
		}
		for(int i=0; i<alist.length; i++)
		{
			alist[i]=new LinkedList<Position>();
		}
	}
	public void play() {
		board.display(fr);
	}
	/**
	 * @return index from allPossible given a position
	 */
	public static int getIndex(Position pos)
	{
		for(int i=0; i<board.allPossible.length; i++)
		{
			if(board.allPossible[i].equals(pos))
			{
				return i;
			}
		}
		return -1;
	}
	/**
	 * @return true if position empty
	 */
	public static boolean checkEmpty(Position x)
	{
		if(board.indexOf(board.calibrate(x), StarBoard.bpos)!=-1)
		{
			return false;
		}
		if(board.indexOf(board.calibrate(x), StarBoard.ppos)!=-1)
		{
			return false;
		}
		return true;
	}
	
	public static double getSlope(Position x, Position y)
	{
		return -((double)x.getColumn()-y.getColumn())/((double)x.getRow()-y.getRow());
	}
	
	public static boolean slopeRange(double slope)
	{
		double slslope = (double)1.732;
		double nslope = -(double)1.732;
		if(Math.abs(slope-slslope)<0.32||Math.abs(slope-nslope)<0.32||slope==0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a move is valid
	 * @param orig original position
	 * @param newd destination position
	 * @return true if move is valid, false otherwise
	 */
	public static boolean isValidMove(Position orig, Position newd)
	{
		if(!checkEmpty(newd))
		{
			return false;
		}
		boolean middleExists = false;
		boolean clearInBetween = true;
		
		double cSlope = getSlope(orig,newd);
		Position middle = new Position((orig.getRow()+newd.getRow())/2,(orig.getColumn()+newd.getColumn())/2);
		middle = board.assignToArray(middle);
		if(board.indexOf(newd,board.surroundPosition(orig))!=-1)
		{
			if(StarBoard.clickcount<=1)
			{
//				System.out.print("This ran");
				StarBoard.adjCount=1;
				return true;
			}
			else
				return false;
		}
		if(middle == null || orig == null || newd == null)
		{
			return false;
		}
		else if(slopeRange(cSlope) && (board.indexOf(board.calibrate(middle),board.player.positions)!=-1||board.indexOf(board.calibrate(middle),board.computer.positions)!=-1))
		{
			middleExists = true;
		}
//		System.out.println(" middle is: "+ getIndex(middle));
		Position currPos = new Position(orig.getRow(),orig.getColumn());
//		System.out.println(" current position is: "+ getIndex(currPos));
//		System.out.println(" new position is: "+ getIndex(newd));
//		System.out.println();
		while(currPos!=null&&newd!=null&&!currPos.equals(newd))//checks whether marbles in between start, middle, and end marbles
		{
			if(cSlope==0)
			{
				if(newd.getRow()>orig.getRow())
				{
//					System.out.print("This ran1 ");
					currPos = new Position(currPos.getRow()+48,currPos.getColumn());
					currPos = board.assignToArray(currPos);
//					System.out.println(getIndex(currPos)+" ");
				}
				else
				{
//					System.out.print("This ran2");
					currPos = new Position(currPos.getRow()-48,currPos.getColumn());
					currPos = board.assignToArray(currPos);
				}
				if(currPos!=null&&newd!=null&&!currPos.equals(newd)&&!currPos.equals(middle)&&(board.indexOf(board.calibrate(currPos),board.player.positions)!=-1||board.indexOf(board.calibrate(currPos),board.computer.positions)!=-1))
				{
					clearInBetween = false;
					return false;
				}
			}
			else if(cSlope>0)
			{
				if(newd.getColumn()>orig.getColumn())
				{
//					System.out.print("This ran3");
					currPos = new Position(currPos.getRow()-24,currPos.getColumn()+40);
					currPos = board.assignToArray(currPos);
				}
				else
				{
//					System.out.print("This ran4");
					currPos = new Position(currPos.getRow()+24,currPos.getColumn()-40);
					currPos = board.assignToArray(currPos);
				}
				if(currPos!=null&&newd!=null&&!currPos.equals(newd)&&!currPos.equals(middle)&&(board.indexOf(board.calibrate(currPos),board.player.positions)!=-1||board.indexOf(board.calibrate(currPos),board.computer.positions)!=-1))
				{
					clearInBetween = false;
					return false;
				}
			}
			else if(cSlope<0)
			{
				if(newd.getColumn()>orig.getColumn())
				{
//					System.out.print("This ran5");
					currPos = new Position(currPos.getRow()+24,currPos.getColumn()+40);
					currPos = board.assignToArray(currPos);
				}
				else
				{
//					System.out.print("This ran6");
					currPos = new Position(currPos.getRow()-24,currPos.getColumn()-40);
					currPos = board.assignToArray(currPos);
				}
				if(currPos!=null&&newd!=null&&!currPos.equals(newd)&&!currPos.equals(middle)&&(board.indexOf(board.calibrate(currPos),board.player.positions)!=-1||board.indexOf(board.calibrate(currPos),board.computer.positions)!=-1))
				{
					clearInBetween = false;
					return false;
				}
			}
		}
		if(middleExists && clearInBetween)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a move is valid after the first step (for computer)
	 * @param orig original position
	 * @param newd destination position
	 * @return true if move is valid, false otherwise
	 */
	public static boolean isPValidMove(Position orig, Position newd)
	{
		if(!checkEmpty(newd)||StarBoard.adjCount>0)
		{
			return false;
		}
		boolean middleExists = false;
		boolean clearInBetween = true;
		
		double cSlope = getSlope(orig,newd);
		Position middle = new Position((orig.getRow()+newd.getRow())/2,(orig.getColumn()+newd.getColumn())/2);
		middle = board.assignToArray(middle);
		if(board.indexOf(newd,board.surroundPosition(orig))!=-1)
		{
			return false;
		}
		if(middle == null || orig == null || newd == null||middle.equals(origPos))
		{
			return false;
		}
		else if(slopeRange(cSlope) && (board.indexOf(board.calibrate(middle),board.player.positions)!=-1||board.indexOf(board.calibrate(middle),board.computer.positions)!=-1))
		{
			middleExists = true;
		}
		Position currPos = new Position(orig.getRow(),orig.getColumn());
		while(currPos!=null&&newd!=null&&!currPos.equals(newd))
		{
			if(cSlope==0)
			{
				if(newd.getRow()>orig.getRow())
				{
					currPos = new Position(currPos.getRow()+48,currPos.getColumn());
					currPos = board.assignToArray(currPos);
				}
				else
				{
					currPos = new Position(currPos.getRow()-48,currPos.getColumn());
					currPos = board.assignToArray(currPos);
				}
				if(currPos!=null&&newd!=null&&!currPos.equals(newd)&&!currPos.equals(middle)&&(board.indexOf(board.calibrate(currPos),board.player.positions)!=-1||board.indexOf(board.calibrate(currPos),board.computer.positions)!=-1))
				{
					clearInBetween = false;
					return false;
				}
			}
			else if(cSlope>0)
			{
				if(newd.getColumn()>orig.getColumn())
				{
					currPos = new Position(currPos.getRow()-24,currPos.getColumn()+40);
					currPos = board.assignToArray(currPos);
				}
				else
				{
					currPos = new Position(currPos.getRow()+24,currPos.getColumn()-40);
					currPos = board.assignToArray(currPos);
				}
				if(currPos!=null&&newd!=null&&!currPos.equals(newd)&&!currPos.equals(middle)&&(board.indexOf(board.calibrate(currPos),board.player.positions)!=-1||board.indexOf(board.calibrate(currPos),board.computer.positions)!=-1))
				{
					clearInBetween = false;
					return false;
				}
			}
			else if(cSlope<0)
			{
				if(newd.getColumn()>orig.getColumn())
				{
					currPos = new Position(currPos.getRow()+24,currPos.getColumn()+40);
					currPos = board.assignToArray(currPos);
				}
				else
				{
					currPos = new Position(currPos.getRow()-24,currPos.getColumn()-40);
					currPos = board.assignToArray(currPos);
				}
				if(currPos!=null&&newd!=null&&!currPos.equals(newd)&&!currPos.equals(middle)&&(board.indexOf(board.calibrate(currPos),board.player.positions)!=-1||board.indexOf(board.calibrate(currPos),board.computer.positions)!=-1))
				{
					clearInBetween = false;
					return false;
				}
			}
		}
		if(middleExists && clearInBetween)
		{
			return true;
		}
		return false;
	}

	/**
	 * Gets one possible move
	 * @param starting position
	 * @return index of possible destination point
	 */
	public static Position getMove(Position pos)
	{
		if(pos.equals(origPos))//only allows adjacent positions if original position
		{
			StarBoard.adjCount=0;
			for(int i=0; i<121; i++)
			{
				if(!board.allPossible[i].visited&&isValidMove(pos,board.allPossible[i]))
				{
				//	System.out.print(" getMove1: "+i+" ");
					return board.allPossible[i];
				}
			}
		}
		else if(StarBoard.adjCount!=1)
		{
			for(int i=0; i<121; i++)
			{
				if(!board.allPossible[i].visited&&isPValidMove(pos,board.allPossible[i]))
				{
				//	System.out.print(" getMove2: "+i+" ");
					return board.allPossible[i];
				}
			}
		}
		return null;
	}
	
	static Stack<Position> stack=new Stack<Position>();
	/**
	 * Adds every possible move of specified marble to list
	 */
	public static void updateList(Position marble)
	{
		while(!stack.isEmpty())
		{
			int index=getIndex(stack.peek());
//			System.out.print(index+" ");
			Position temp=getMove(stack.peek());
			if(temp!=null)
			{
				stack.push(temp);
				temp.visited=true;
				if (alist[index].isEmpty()||!alist[index].contains(temp))
				{
//					System.out.print(contains(list[index],temp)); 
					
					list[getIndex(origPos)].add(temp);
					alist[index].add(temp);
//					System.out.print(" (added"+ getIndex(temp)+"to"+index+") ");
//					for(int i=0; i<alist[index].size(); i++)
//						System.out.print(getIndex(alist[index].get(i))+",");
				}
				updateList(temp);
			}
			else
			{
				stack.pop();
//				System.out.println("return from: "+index);
				break;
			}	
		}
	}
	public static Position getUnvisited(Position pos)
	{
		for(int i=0; i<alist[getIndex(pos)].size(); i++)
		{
			if(!alist[getIndex(pos)].get(i).visited)
				return alist[getIndex(pos)].get(i);
		}
		return null;
	}
	static Stack<Position>pos=new Stack<Position>();
	static LinkedList<Position>path=new LinkedList<Position>();
	static boolean test=false;
	public static void shPathHelper(Position from, Position to)
	{
		int counter=0;
		from.visited=true;
		pos.push(from);
		while(!pos.isEmpty())
		{
			Position temp=getUnvisited(pos.peek());
			if(temp==null)
				pos.pop();
			else
			{
				if(temp.equals(to))
				{
					if(counter>1)
					{
						temp.visited=true;
						pos.pop();
						continue;
					}
					if(path.isEmpty()||stack.size()<path.size())
					{
						pos.push(temp);
						path.clear();
						//copy stack to linked list
						Iterator<Position> temp2 = pos.iterator();
				        while (temp2.hasNext()) 
				        {
				            Position p = temp2.next();
				            path.add(p);
				        }
					}
					counter++;
					pos.pop();
				}
				else
				{
					temp.visited=true;
					pos.push(temp);
					counter=0;
				}
			}
		}
//		pos.push(from);
//		from.visited=true;
//		while(!pos.isEmpty())
//		{
//			int index=getIndex(pos.peek());
//			for(int i=0; i<alist[index].size();i++)
//			{
//				if(alist[index].get(i).equals(to))
//				{
//					if(path.isEmpty()||stack.size()<path.size())
//					{
//						pos.push(alist[index].get(i));
//						path.clear();
//						//copy stack to linkedlist
//						Iterator<Position> temp = pos.iterator();
//				        while (temp.hasNext()) 
//				        {
//				            Position p = temp.next();
//				            path.add(p);
//				        }
//					}
//
//			        return;
//				}
//				else if(alist[index].get(i).visited)
//				{
//					continue;
//				}
//				else
//				{
//					shPathHelper(alist[index].get(i), to);
//				}
//			}
//			pos.pop();
//			break;
//		}		
	}
	/**
	 * Finds most efficient route to move computer marble between two points for the purpose of updating graphics
	 */
	public static void shortestPath(Position from, Position to)
	{
		path.clear();
		pos.clear();
		shPathHelper(from, to);
	}
	public static int numInHome()
	{
		int count=0;
		for(int i=0; i<10; i++)
		{
			Position ctemp = new Position(board.computer.positions[i].getRow()+16, board.computer.positions[i].getColumn()+16);	
			if(board.indexOf(ctemp,board.home2)!=-1)
			{
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Used to evaluate best move relative to opponent's home
	 * @return
	 */
	public static int getTarget()
	{
		for(int i=0; i<board.home2.length; i++)
		{
			if(checkEmpty(board.home2[i]))
			{
				return getIndex(board.home2[i]);
			}
		}
		return -1;
	}
	public static Position prev=new Position(0,0);
	public static Position to=new Position(0,0);
	public static void botMove()
	{
		//update list
		for(int i=0; i<10; i++)
		{
			StarBoard.adjCount=0;
			StarBoard.clickcount=0;
			Position pos=board.assignToArray(StarBoard.ppos[i]);
			origPos=pos;
			int index=getIndex(pos);
			board.allPossible[index].visited=true;
			stack.push(pos);
			updateList(pos);
			//reset visited
			for(int y=0;y<121;y++)
			{
				board.allPossible[y].visited=false;
			}
		}
		int target=getTarget();
		//choose how to move from list
		Position[]bestMoves=new Position[10];//best moves for each marble
		for(int i=0; i<10; i++)
		{
			if(list[getIndex(board.assignToArray(StarBoard.ppos[i]))].isEmpty())
				continue;
			int max=board.computer.positions[i].distance(board.allPossible[target])-list[getIndex(board.assignToArray(StarBoard.ppos[i]))].get(0).distance(board.allPossible[target]);
			int ind=0;
			for(int y=1; y<list[getIndex(board.assignToArray(StarBoard.ppos[i]))].size(); y++)
			{
				if(max<board.computer.positions[i].distance(board.allPossible[target])-list[getIndex(board.assignToArray(StarBoard.ppos[i]))].get(y).distance(board.allPossible[target]))
				{
					max=board.computer.positions[i].distance(board.allPossible[target])-list[getIndex(board.assignToArray(StarBoard.ppos[i]))].get(y).distance(board.allPossible[target]);
					ind=y;
				}
			}
			bestMoves[i]=list[getIndex(board.assignToArray(StarBoard.ppos[i]))].get(ind);
		}
		//trying to find first position thats not empty
		int index;
		for(index=0; index<10; index++)
		{
			if(bestMoves[index]!=null&&!(bestMoves[index].equals(prev)&&board.assignToArray(board.computer.positions[index]).equals(to)))
			{
				break;
			}
		}
		int max=board.computer.positions[index].distance(board.allPossible[target])-bestMoves[index].distance(board.allPossible[target]);
			for(int i=0; i<bestMoves.length; i++)
			{	
				if(list[getIndex(board.assignToArray(StarBoard.ppos[i]))].isEmpty()||(bestMoves[i].equals(prev)&&board.assignToArray(board.computer.positions[i]).equals(to)))//empty or repeating moves
				{	
					continue;
				}
				//VARIETY is cool
				else if(StarBoard.compCount<1&&board.computer.positions[i].distance(board.allPossible[target])-bestMoves[i].distance(board.allPossible[target])-max<150)
				{
					int rand=(int) (Math.random()*2);
					if(rand==1)
					{
						max=board.computer.positions[i].distance(board.allPossible[target])-bestMoves[i].distance(board.allPossible[target]);
						index=i;
					}
					continue;
				}
				
				else if(!checkEmpty(board.allPossible[120])&&!checkEmpty(board.allPossible[119])&&!checkEmpty(board.allPossible[118])&&board.computer.positions[i].distance(board.allPossible[target])-bestMoves[i].distance(board.allPossible[target])-max<100)
				{
//					System.out.print("priorities");
					if(board.indexOf(board.assignToArray(board.computer.positions[index]), board.home2)!=-1&&board.indexOf(board.assignToArray(board.computer.positions[i]), board.home2)==-1)
					{
//						System.out.println("PRCHANGE");
						max=board.computer.positions[i].distance(board.allPossible[target])-bestMoves[i].distance(board.allPossible[target]);
						index=i;
						continue;
					}
					else if(board.indexOf(board.assignToArray(board.computer.positions[index]), board.home2)==-1&&board.indexOf(board.assignToArray(board.computer.positions[i]), board.home2)!=-1)
					{
						continue;
					}
				}
				if(max<board.computer.positions[i].distance(board.allPossible[target])-bestMoves[i].distance(board.allPossible[target]))
				{
//						System.out.println("CHANGE");
						max=board.computer.positions[i].distance(board.allPossible[target])-bestMoves[i].distance(board.allPossible[target]);
						index=i;
				}
//				System.out.println("now: "+getIndex(board.assignToArray(board.computer.positions[index]))+" "+getIndex(bestMoves[index]));
			}

//			for(int i=0; i<bestMoves.length; i++)
//			{
//				if(bestMoves[i]==null)
//					System.out.println(i+": "+null);
//				else
//				{
//					System.out.println(i+": "+getIndex(board.assignToArray(board.computer.positions[i]))+ " to "+getIndex(bestMoves[i]));
//					System.out.println(" d: "+(board.computer.positions[i].distance(board.allPossible[target])-bestMoves[i].distance(board.allPossible[target])));
//				}
//			}
//			System.out.println();
//		System.out.println(getIndex(prev)+" to "+getIndex(to));
		//shortestPath method
		
		to=bestMoves[index];
		prev=board.assignToArray(board.computer.positions[index]);
		if(board.surroundPosition(prev).contains(to))
		{
			path.clear();
			path.add(prev);
			path.add(to);
		}
		else
			shortestPath(prev, to);
		board.computer.positions[index]=board.calibrate(to);
		StarBoard.sound();
//		System.out.print("The Path: ");
//		for(int i=0; i<path.size(); i++)
//		{
//			System.out.print(getIndex(path.get(i))+" ");
//		}
//		System.out.println();
//		displays list
		for(int i=0; i<alist.length;i++)
		{
			if(alist[i].isEmpty())
				System.out.println(i+": ");
			else
			{
				System.out.print(i+": ");
				for(int y=0;y<alist[i].size();y++)
					System.out.print(getIndex(alist[i].get(y))+" ");
				System.out.println();
			}
		}
		System.out.println();
		//reset visited
		for(int y=0;y<121;y++)
		{
			board.allPossible[y].visited=false;
		}
	
		
		//clear list to prepare for next turn
		for(int i=0; i<list.length; i++)
		{
			list[i].clear();
		}
		for(int i=0; i<alist.length; i++)
		{
			alist[i].clear();
		}

		origPos=DEFAULT;
		
//		final int fIndex = index;
//		new Thread(
//			new Runnable() {
//				public void run() {
//					for(int i=1; i<path.size(); i++)
//					{
//						System.out.print(" Going to: "+getIndex(path.get(i))+" ");
//						board.computer.positions[fIndex]=board.calibrate(path.get(i));
//						System.out.print(" Testing: "+getIndex(board.assignToArray(board.computer.positions[fIndex]))+" ");
//						board.myFr.repaint();
////						System.out.print(i+" out of "+path.size());
//						try
//						{
//							System.out.println(" yes this ran ");
//							Thread.sleep(500);
//						} catch (InterruptedException e) {
////							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		).start();
		
//		for(int i=0; i<list.length;i++)
//		{
//			if(list[i].isEmpty())
//				System.out.println(i+": ");
//			else
//			{
//				System.out.print(i+": ");
//				for(int y=0;y<list[i].size();y++)
//					System.out.print(getIndex(list[i].get(y))+" ");
//				System.out.println();
//			}
//		}
	}
	
//	public static void compMove() //simplified version of botMove()
//	{
//		int[] arr1= new int[10]; //keeps track indexes
//		ArrayList<Position> arr2= new ArrayList<Position>();//keeps track possible pos
//		int count = 0;
//		do {
//			int temp = (int)(Math.random()*121);
//			for(int i = 0; i<10; i++)
//			{
//				if(board.allPossible[temp]!=null&&isValidMove(board.assignToArray(board.computer.getPosition()[i]),board.allPossible[temp])&&checkEmpty(board.allPossible[temp]))
//				{
//					System.out.println("Possible: "+board.assignToArray(board.computer.getPosition()[i])+ "," + board.allPossible[temp]);
//					arr1[count]=i;
//					arr2.add(board.allPossible[temp]);
//					count++;
//				}
//				if(count==10)
//					break;
//			}
//		} while(count<10);
//		int max = board.assignToArray(board.computer.positions[arr1[0]]).distance(board.allPossible[120])-arr2.get(0).distance(board.allPossible[120]);
//		int index = 0;
//		for(int i = 1; i< arr1.length;i++)
//		{
//			if(board.assignToArray(board.computer.positions[arr1[i]]).distance(board.allPossible[120])-arr2.get(i).distance(board.allPossible[120])>max)
//			{
//				max = board.assignToArray(board.computer.positions[arr1[i]]).distance(board.allPossible[120])-arr2.get(i).distance(board.allPossible[120]);
//				index = i;
//			}
//		}
//		board.computer.positions[arr1[index]]=board.calibrate(arr2.get(index));
//		StarBoard.sound();
//	}

}

