import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
class Server extends Thread
{
	Socket socket;
	static int noc=0,xr,yr,cri=1;
	static int[] lsnake=new int[15];
	static Point[][] lbp=new Point[15][30];
	static Random r=new Random();

	public Server(Socket s) throws IOException
	{
		socket=s;
		start();
	}
	void getCoordinates(int sav,DataInputStream dis)
	{
		int x,y;
		try
		{
			for(int i=0;i<lsnake[sav];i++)
			{
				x=dis.readInt();
				y=dis.readInt();
				System.out.println(x+" "+y);
				lbp[sav][i]=new Point(x,y);
			}
			System.out.println("getcoord");
		}
		catch(IOException e){System.out.println(e+" "+28);}
	}
	void readSnakeLength(int sav,DataInputStream dis)
	{
		try
		{
			lsnake[sav]=dis.readInt();
			//System.out.println("snake length receive "+lsnake[sav]+" of noc "+sav);
			//for(int i=0;i<noc;i++)
				///	System.out.println("length of snake "+i+" is "+lsnake[i]);
		}
		catch(IOException e){System.out.println(e+" "+36);}
	}
	void sendFood(DataOutputStream dos)
	{
		try
		{
			dos.flush();
			dos.writeInt(xr-(xr%20));
			dos.writeInt(yr-(yr%20));
			//System.out.println("Food send "+(xr-xr%20)+" "+(yr-yr%20));
		}
		catch(IOException e){System.out.println(e+" "+49);}
	}
	void sendInfoAllSnakes(DataOutputStream dos)
	{
		try
		{
			dos.flush();
			dos.writeInt(noc);
			System.out.println("sent noc is "+noc);
			for(int i=0;i<noc;i++)
			{
				dos.writeInt(lsnake[i]);
				System.out.println("Snake length send "+lsnake[i]+" of snake "+i);
				for(int j=0;j<lsnake[i];j++)
				{
					System.out.println(lbp[i][j]);
					dos.writeInt((int)(lbp[i][j].getX()));
					dos.writeInt((int)(lbp[i][j].getY()));
				}
			}
			System.out.println("send coord");
		}
		catch(IOException e){System.out.println(e+" "+57);}
	}
	void foodEaten(DataInputStream dis)
	{
		try
		{
			int temp;
			temp=dis.readInt();
			//System.out.println("food "+temp);
			if(temp==2)
			{
				xr=r.nextInt(500);
				yr=r.nextInt(500);
			}
		}
		catch(IOException e){System.out.println(e+" "+57);}
	}
	public void run()
	{
		int sav,x,y;
		InputStream is;
		OutputStream os;
		DataInputStream dis;
		DataOutputStream dos;
		try
		{
			is = socket.getInputStream();
			os = socket.getOutputStream();
			dis=new DataInputStream(is);
			dos=new DataOutputStream(os); 
			cri=0;
			dos.writeInt(noc);
			lsnake[noc]=3;
			if(noc==0)
			{
				xr=r.nextInt(480);
				yr=r.nextInt(480);
			}
			sendFood(dos);
			sav=noc++;
			cri=1;
			while(true)
			{
				foodEaten(dis);
				sendFood(dos);
				readSnakeLength(sav,dis);
				getCoordinates(sav,dis);
				if(cri==0)
					sleep(5);
				sendInfoAllSnakes(dos);
				//sendFood(dos);
			}
		}
		catch(Exception e){System.out.println(e+" "+130);}
		finally
		{
			try
			{
				socket.close();
			}
			catch(IOException e){}
		}
	}
	public static void main(String args[]) throws IOException
	{
		ServerSocket s = new ServerSocket(8210);
		System.out.println("Server Started");
		try
		{
			while(true)
			{
				Socket socket = s.accept();
				try
				{
					new Server(socket);
				}
				catch(IOException e)
				{
					System.out.println(e+" "+109);
					socket.close();
				}
			}
		}
		catch(IOException e){				System.out.println(e+" "+114);}
		finally
		{
			s.close();
		}
	}
}
