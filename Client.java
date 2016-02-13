import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
class Client extends JFrame implements KeyListener
{
	private static final long serialVersionUID = 1;
	Socket socket;
	InetAddress addr;
	DataInputStream dis;
	DataOutputStream dos;
	InputStream is;
	OutputStream os;
	JPanel p1;
	JRadioButton[][] lb = new JRadioButton[15][30];
	JButton food;
	int[] lbx = new int[30];
	int[] lby = new int[30];
	int[] lsnake=new int[30];
	Point[][] lbp = new Point[15][30];
	int dir_x=20,dir_y=0,x=500,y=500,color,length,noc,foodx,foody,foodeat=1;
	boolean rl=false,ru=true,rr=true,rd=true;
	Client(InetAddress addr1)
	{
		super("Snake");
		length=3;
		addr=addr1;
		screate();
		lsnake[color]=3;
		setSize(x,y);
		lbx[0] = 100;
        	lby[0] = 20+color*20;
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		p1=new JPanel();
		p1.setLayout(null);
		p1.setBounds(0, 0,x,y);
		p1.setBackground(Color.black);
		getContentPane().setLayout(null);
	        getContentPane().add(p1);
	        setFocusable( true );
	        addKeyListener(this);
	        createFirstSnake();
	        receiveFood();
	        setVisible(true);
	}
	void screate()
	{
		try
		{
			socket = new Socket(addr,8210);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			dis=new DataInputStream(is);
			dos=new DataOutputStream(os);
			color=dis.readInt();
			//System.out.println("color "+color);
		}
		catch(IOException e){
			System.out.println(e+" "+56);}
	}
	void createFirstSnake()
	{
		for (int i = 0; i < 3; i++)
		{
			lb[color][i] = new JRadioButton("lb" +color+ i);
            		lb[color][i].setEnabled(false);
            		if(color==0)
	            		lb[color][i].setBackground(Color.blue);
	            	else if(color==1)
	            		lb[color][i].setBackground(Color.cyan);
	            	else if(color==2)
	            		lb[color][i].setBackground(Color.darkGray);
	            	else if(color==3)
	            		lb[color][i].setBackground(Color.gray);
	            	else if(color==4)
	            		lb[color][i].setBackground(Color.green);
	            	else if(color==5)
	            		lb[color][i].setBackground(Color.lightGray);
	            	else if(color==6)
	            		lb[color][i].setBackground(Color.magenta);
	            	else if(color==7)
	            		lb[color][i].setBackground(Color.orange);
	            	else if(color==8)
	            		lb[color][i].setBackground(Color.pink);
	            	else if(color==9)
	            		lb[color][i].setBackground(Color.red);
	            	else if(color==10)
	            		lb[color][i].setBackground(Color.white);
	            	else if(color==11)
	            		lb[color][i].setBackground(Color.yellow);
            		p1.add(lb[color][i]);
            		lb[color][i].setBounds(lbx[i], lby[i], 20, 20);
            		lbp[color][i]=new Point(lb[color][i].getLocation());
            		lbx[i + 1] = lbx[i] - 20;
            		lby[i + 1] = lby[i];
		}
	}
	void showOtherSnakes(int i)
	{
		//System.out.println("other snake");
		for(int j=0;j<lsnake[i];j++)
		{
			lb[i][j] = new JRadioButton("lb" + i+j);
			lb[i][j].setEnabled(false);
			if(i==0)
		    		lb[i][j].setBackground(Color.blue);
		    	else if(i==1)
		    		lb[i][j].setBackground(Color.cyan);
		    	else if(i==2)
		    		lb[i][j].setBackground(Color.darkGray);
		    	else if(i==3)
		    		lb[i][j].setBackground(Color.gray);
		    	else if(i==4)
		    		lb[i][j].setBackground(Color.green);
		    	else if(i==5)
		    		lb[i][j].setBackground(Color.lightGray);
		    	else if(i==6)
		    		lb[i][j].setBackground(Color.magenta);
		    	else if(i==7)
		    		lb[i][j].setBackground(Color.orange);
		    	else if(i==8)
		    		lb[i][j].setBackground(Color.pink);
		    	else if(i==9)
		    		lb[i][j].setBackground(Color.red);
		    	else if(i==10)
		    		lb[i][j].setBackground(Color.white);
		    	else if(i==11)
		    		lb[i][j].setBackground(Color.yellow);
		    	p1.add(lb[i][j]);
			lb[i][j].setBounds((int)(lbp[i][j].getX()),(int)(lbp[i][j].getY()), 20, 20);
		}
		p1.repaint();
		setVisible(true);
	}
	void moveForward()
	{
		System.out.println("moving");
		if(foodx==(int)(lbp[color][0].getX()) && foody==(int)(lbp[color][0].getY()))
		{
			int i;
			System.out.println("moving");
			for (i = 0; i < length; i++)
				lbp[color][i] = new Point(lb[color][i].getLocation());
			lbp[color][i] = new Point(lb[color][i-1].getX(),lb[color][i-1].getY());
		    	lsnake[color]=++length;
		        lbx[0] += dir_x;
			lby[0] += dir_y;
			lb[color][0].setBounds(lbx[0], lby[0], 20, 20);
			for (i = 1; i < length-1; i++)
			       	lb[color][i].setLocation(lbp[color][i - 1]);
			lb[color][i]=new JRadioButton();
			lb[color][i].setEnabled(false);
			if(color==0)
		    		lb[color][i].setBackground(Color.blue);
		    	else if(color==1)
		    		lb[color][i].setBackground(Color.cyan);
		    	else if(color==2)
		    		lb[color][i].setBackground(Color.darkGray);
		    	else if(color==3)
		    		lb[color][i].setBackground(Color.gray);
		    	else if(color==4)
		    		lb[color][i].setBackground(Color.green);
		    	else if(color==5)
		    		lb[color][i].setBackground(Color.lightGray);
		    	else if(color==6)
		    		lb[color][i].setBackground(Color.magenta);
		    	else if(color==7)
		    		lb[color][i].setBackground(Color.orange);
		    	else if(color==8)
		    		lb[color][i].setBackground(Color.pink);
		    	else if(color==9)
		    		lb[color][i].setBackground(Color.red);
		    	else if(color==10)
		    		lb[color][i].setBackground(Color.white);
		    	else if(color==11)
		    		lb[color][i].setBackground(Color.yellow);
			p1.add(lb[color][i]);
			lb[color][i].setBounds((int)(lbp[color][i-1].getX()),(int)( lbp[color][i-1].getY()), 20, 20);
			lb[color][i].setLocation(lbp[color][i]);
			System.out.println("move "+foodeat);
			if (lbx[0] == x)
				lbx[0] = 0;
			else if (lbx[0] == 0)
		    		lbx[0] =x ;
			else if (lby[0] >= y)
		    		lby[0] = 0;
			else if (lby[0] == 0)
		    		lby[0] = y ;
		    	foodeat=2;
		   // 	p1.repaint();
        	//setVisible(true);
		}
		else
		{
		System.out.println("moving");
			for (int i = 0; i < length; i++)
				lbp[color][i] = new Point(lb[color][i].getLocation());
		        lbx[0] += dir_x;
			lby[0] += dir_y;
			lb[color][0].setBounds(lbx[0], lby[0], 20, 20);
			for (int i = 1; i < length; i++)
			       	lb[color][i].setLocation(lbp[color][i - 1]);
			if (lbx[0] == x)
				lbx[0] = 0;
			else if (lbx[0] == 0)
		    		lbx[0] =x ;
			else if (lby[0] >= y)
		    		lby[0] = 0;
			else if (lby[0] == 0)
		    		lby[0] = y ;
		    	foodeat=1;
		}
        	/*p1.repaint();
        	setVisible(true);*/
        }
        public void keyPressed(KeyEvent e)
        {
        	if(e.getKeyCode()==37 && rl==true)
        	{
        		dir_x=-20;
        		dir_y=0;
        		ru=true;
        		rd=true;
        		rr=false;
        	}
        	if(e.getKeyCode()==38 && ru==true)
        	{
        		dir_x=0;
        		dir_y=-20;
        		rl=true;
        		rd=false;
        		rr=true;
        	}
		if(e.getKeyCode()==39 && rr==true)
        	{
        		dir_x=20;
        		dir_y=0;
        		rl=false;
        		ru=true;
        		rd=true;
        	}
		if(e.getKeyCode()==40 && rd==true)
        	{
        		dir_x=0;
        		dir_y=20;
        		ru=false;
        		rl=true;
        		rr=true;
        	}
        }
        public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	void sendSnakeLength()
	{
		try
		{
			dos.flush();
			dos.writeInt(length);
			//System.out.println("snake length send "+length);
		}
		catch(IOException e){
		System.out.println(e+" "+196);
		}
	}
	void sendCoordinates()
	{
		try
		{
			dos.flush();
			for(int i=0;i<length;i++)
			{
				System.out.println(lbp[color][i]);
				dos.writeInt((int)(lbp[color][i].getX()));
				dos.writeInt((int)(lbp[color][i].getY()));
			}
			System.out.println("Coord sent");
		}
		catch(IOException e){
		System.out.println(e+" "+211);
		}
	}
	void receiveInfoAllSnakes()
	{
		try
		{
			int xc,yc;
			noc=dis.readInt();
			System.out.println("noc is "+noc);
			for(int i=0;i<noc;i++)
			{
				if(i!=color)
				{
					lsnake[i]=dis.readInt();
					System.out.println("length of other snake "+lsnake[i]);
				}
				else
					System.out.println("my snake length "+dis.readInt());
				for(int j=0;j<lsnake[i];j++)
				{
					xc=dis.readInt();
					yc=dis.readInt();
					System.out.println(xc+" "+yc);
					if(i!=color)
						lbp[i][j]=new Point(xc,yc);
				}
				if(i!=color)
					showOtherSnakes(i);
			}
			System.out.println("received");
		}
		catch(IOException e){
			System.out.println(e+" "+239);}
	}
	void removeOld()
	{
		for(int i=0;i<noc;i++)
			for(int j=0;j<lsnake[i];j++)
				if(i!=color)
					p1.remove(lb[i][j]);
	}
	void receiveFood()
	{
		try
		{
			//System.out.println("food");
			foodx=dis.readInt();
			foody=dis.readInt();
			//System.out.println(foodx+" "+foody);
			food=new JButton();
			food.setBackground(Color.yellow);
			food.setBounds(foodx,foody,20,20);
			p1.add(food);
			/*p1.repaint();
			setVisible(true);*/
		}
		catch(IOException e){System.out.println(e+" "+272);}
	}
	void receiveFood2()
	{
		try
		{
			p1.remove(food);
			//System.out.println("food2");
			foodx=dis.readInt();
			foody=dis.readInt();
			//System.out.println(foodx+" "+foody);
			food=new JButton();
			food.setBackground(Color.yellow);
			food.setBounds(foodx,foody,20,20);
			p1.add(food);
		}
		catch(IOException e){System.out.println(e+" "+272);}
	}
	void sendFoodEat()
	{
		try
		{
			dos.flush();
			dos.writeInt(foodeat);
			//System.out.println("sendfoodeat "+foodeat);
		}
		catch(IOException e){System.out.println(e+" "+301);}
	}
	public static void main(String args[])
	{
		try
		{
			InetAddress addr = InetAddress.getByName("192.168.43.154");
			Client c=new Client(addr);
			while(true)
			{
				c.moveForward();
				c.sendFoodEat();
				c.receiveFood2();
				c.sendSnakeLength();
				c.sendCoordinates();
				c.receiveInfoAllSnakes();
				if(c.length<=5)
					Thread.sleep(200);
				else if(c.length<=7)
					Thread.sleep(100);
				else
					Thread.sleep(50);
				if(c.length>=10)
					JOptionPane.showMessageDialog(c.p1, "You win");
				c.removeOld();
			}
		}
		catch(Exception e){System.out.println(e+" "+352);}
	}
}
