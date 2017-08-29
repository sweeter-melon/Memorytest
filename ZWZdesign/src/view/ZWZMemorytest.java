package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.StyledEditorKit.ForegroundAction;
import javax.xml.namespace.QName;

public class ZWZMemorytest extends JFrame implements ActionListener {
 
	public static ZWZMemorytest zwzMemorytest;
	public static int starttime;
	public static int begintime;
	public static String rank;
	int click=0;
	ZWZStartpanel startpanel;  
	ZWZGamepanel gamepanel;
	Recorderpanel rpanel;

	JMenu jb1,jb2,jb3;
	JMenuItem jt1,jt2,jt3,jt4,jt5,jt6;
	JMenuBar jmb;
	public static boolean isend=false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 zwzMemorytest=new ZWZMemorytest();
		
	}

	public ZWZMemorytest ()
	{
		jmb=new JMenuBar();
		jb1=new JMenu("游戏");
		jb2=new JMenu("查看");
		jb3=new JMenu("游戏设置");
		
		
		jt1=new JMenuItem("开始游戏");
		jt1.addActionListener(this);
		jt2=new JMenuItem("退出游戏");
		jt2.addActionListener(this);
		jt3=new JMenuItem("游戏记录");
		jt3.addActionListener(this);
		jt4=new JMenuItem("普通");
		jt4.addActionListener(this);
		jt5=new JMenuItem("中等");
		jt5.addActionListener(this);
		jt6=new JMenuItem("困难");
		jt6.addActionListener(this);
		
		jb1.add(jt1);
		jb1.add(jt2);
		jb2.add(jt3);
		
		jb3.add(jt4);
		jb3.add(jt5);
		jb3.add(jt6);
		
		jmb.add(jb1);
		jmb.add(jb2);
		jmb.add(jb3);
		
		startpanel=new ZWZStartpanel();
		Thread t1=new Thread(startpanel);
		t1.start();
		this.add(startpanel);
		
        
		
		this.setJMenuBar(jmb);
		//ImageIcon icon=new ImageIcon("image\\qq2.jpg");
		//this.setIconImage(icon.getImage());
		this.setSize(620,670);
		this.setTitle("记忆测试小游戏");
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
	
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		if(arg0.getSource()==jt1)
		{
			if(click!=0)
			{
				this.remove(rpanel);
				this.add(startpanel);
				this.setVisible(true);
			}
			
			click=0;
			gamepanel=new ZWZGamepanel();
			
			
			this.remove(startpanel);
			this.add(gamepanel);
			this.addMouseListener(gamepanel);
			this.setVisible(true);
					
		}
		
		if(arg0.getSource()==jt2)
		{
			System.exit(0);
		}
		
		if(arg0.getSource()==jt3)
		{
			click++;
			rpanel=new Recorderpanel();
			this.remove(startpanel);
			this.add(rpanel);
			this.setVisible(true);
		}
		
		if(arg0.getSource()==jt4)
		{
			starttime=21;
			rank="普通";
			begintime=-22;
			JOptionPane.showMessageDialog(this, "设置成功");
		}
		if(arg0.getSource()==jt5)
		{
			starttime=11;
			rank="中等";
			begintime=-12;
			JOptionPane.showMessageDialog(this, "设置成功");
		}
		if(arg0.getSource()==jt6)
		{
			starttime=6;
			rank="困难";
			begintime=-5;
			JOptionPane.showMessageDialog(this, "设置成功");
		}
	}
	
}


class ZWZStartpanel extends JPanel implements  Runnable{
	
	
	int times=1;
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.cyan);
		g.fillRect(0, 0, 620, 670);
		if(times%2==0)
		{
		g.setColor(Color.blue); 
		g.setFont(new Font("华文彩云",Font.BOLD,40));
		g.drawString("记忆力测试", 200, 300);
		}
	}
	
	public void run() {
		
		while(true)
		{
			
			try {
				Thread.sleep(500);
			} catch (Exception e) {
			
				e.printStackTrace();
			}	
		times++;	
		this.repaint();
		}
	
		
	}
}



class Recorderpanel extends JPanel  
{
	
	Vector<String> vector;
	
	public void paint(Graphics g)
	{
	

		super.paint(g);
		try {
			Image recorder=ImageIO.read(new File("image\\recoder.jpg"));
			g.drawImage(recorder, 0, 0, 620, 670 , this);
		} catch (Exception e) {
			// TODO: handle exception
		}
		g.setColor(new Color(249, 191, 100));
		g.setFont(new Font("楷体",Font.BOLD, 32));
		g.drawString("玩家记录", 220, 40);
		
		

	    vector=ZWZRecoder.Getcorder();
		for(int i=0;i<vector.size();i++)
		{
			
			g.setColor(new Color(240, 200, 100));
			g.setFont(new Font("方正舒体",Font.BOLD, 20));
			String str=vector.get(i);
			g.drawString(i+1+ ":"+str,40,80+i*20);
		}
		vector.clear();
	
	}
	
	
}

class ZWZGamepanel extends JPanel implements MouseListener, Runnable {
	
	Thread gtThread;
	
	int starttime;
	int timebegin;
	String rank;
	Image im=null;
	Image bg[][]=new Image[5][5];
	int rand[][]=new int[5][5];
	int data[]=new int[25];
	int stepnum;
	int totalnum;
	int index;
	int indey;
	boolean isalive;
	
	Map<Integer, Integer> map=new HashMap<Integer, Integer>();
	
	
	public void Init()
	{
		 starttime=ZWZMemorytest.starttime;
		 timebegin=ZWZMemorytest.begintime;
		 rank=ZWZMemorytest.rank;
		 stepnum=1;
		 totalnum=0;
		 isalive=true;
		int h=0;
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<5;j++)
			{
			rand[i][j]=(int)((Math.random())*5);
			data[h]=rand[i][j];
			h++;

     	   try {
				bg[i][j]=ImageIO.read(new File("image\\poker.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     	  
     	  
			}
		}
	}
	
	
	public void Initdata()
	{
		for(int i=0;i<25;i++)
		{
			
				if(map.get(data[i])!=null)
		     	   {
		     		   map.put(data[i], map.get(data[i])+1);
		     		  
		     	   }
		     	   else {
		     		
		     		   map.put(data[i], 1);
				}
				
			
			
		}
	}
	
	
	public ZWZGamepanel()
	{
		gtThread=new Thread(this);
		gtThread.start();
		this.Init();
		this.Initdata();
		
	}
	
	
	
	public void paint(Graphics g)
	{
		super.paint(g);
		try {
			Image bg=ImageIO.read(new File("image\\bg.jpg"));
			g.drawImage(bg,0, 0, 620, 670,this);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		 g.setColor(Color.black);
		 g.setFont(new Font("楷体", Font.BOLD, 25));
	     g.drawString("难度: "+rank, 30, 42);
		
		if(starttime<0)
		{
			g.setColor(Color.orange);
			g.drawString("游戏时间: "+timebegin+" S", 260, 40);
			
	           for(int i=0;i<5;i++)
	           {
	        	   for(int j=0;j<5;j++)
	        	   {
	        	
	        	   g.drawImage(bg[i][j],60+i*100 ,60+j*100 ,100 ,100,this);
	               }
				
	           }
		}
		if(starttime>=0)
		{
           for(int i=0;i<5;i++)
           {
        	   for(int j=0;j<5;j++)
        	   {
        	
        	   try {
				im=ImageIO.read(new File("image\\timg"+rand[i][j]+".jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	   g.drawImage(im,60+i*100 ,60+j*100 ,100 ,100,this); 
               }
			
           }
           g.setColor(Color.orange);
		g.drawString("记忆时间: "+starttime+" S", 260, 40);
          }
	}
	
	
	
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(isalive)
			{
			starttime--;
			timebegin++;
			}
			this.repaint();
			
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		int x=arg0.getX();
		int y=arg0.getY();
		
		for(int i=0;i<5;i++)
		{
			
			for(int j=0;j<5;j++)
			{
				
				if(x>60+i*100&&x<60+(i+1)*100&&y>90+j*100&&y<90+(j+1)*100)
				{
					
					try {
						bg[i][j]=ImageIO.read(new File("image\\timg"+rand[i][j]+".jpg"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(totalnum>0)
					{
					if(rand[i][j]==rand[index][indey])
					{
						stepnum++;
						
					}
					
					if(rand[i][j]!=rand[index][indey])
					{
						int s=map.get(rand[index][indey]);
						
						if(s==stepnum)
						{
							stepnum=1;
						}
						else {
							
							isalive=false;
							int n=JOptionPane.showConfirmDialog(null, "很遗憾，您失败了，是否重新开始","记忆测试", JOptionPane.YES_NO_OPTION);
							
							if(n==JOptionPane.YES_OPTION)
							{
								map.clear();
								this.Init();
								this.Initdata();
							
								break;
								
							}
							
							if(n==JOptionPane.NO_OPTION)
							{
								ZWZMemorytest.zwzMemorytest.setVisible(false);
								ZWZMemorytest.zwzMemorytest=new ZWZMemorytest();
							
							}
						}
					}
					}
					
					 index=i;
					 indey=j;
					 totalnum++;
					 if(totalnum==25)
					 {
						 
						 String recorder=timebegin+" s";
						 
						 String data=Calendar.getInstance().getTime().toLocaleString();
						 int m=JOptionPane.showConfirmDialog(null, "恭喜你胜利，是否保存记录","记忆测试", JOptionPane.YES_NO_OPTION);
						 isalive=false;
					  if(m==JOptionPane.YES_OPTION)
						{
						   String sname=JOptionPane.showInputDialog(this,"输入玩家姓名 ： ","游戏记录",JOptionPane.PLAIN_MESSAGE);
						   ZWZRecoder.setName(sname);
						   ZWZRecoder.setRecorder(recorder);
						   ZWZRecoder.setData(data);
						   ZWZRecoder.setRank(rank);
						   ZWZRecoder.KeepRecorder();
						  
						 
						   ZWZMemorytest.zwzMemorytest.setVisible(false);
						   ZWZMemorytest.zwzMemorytest=new ZWZMemorytest();
						 
						 }
						 
					 }
					 
				}
			}
			
		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
}



class ZWZRecoder
{
	   
	   public static int isclear=0;
   
	   private static FileWriter fw=null;
	   private static BufferedWriter bw=null;
	   private static FileReader fr=null;
	   private static BufferedReader br=null;
	
	   static  String name;
	   static  String data;
	   static  String recorder;
	   static String rank;
	   
	   public  static Vector<String> vStrings=new Vector<String>();
	   
	

	public static void setRank(String rank) {
		ZWZRecoder.rank = rank;
	}

  
	public static void setName(String name) {
		ZWZRecoder.name = name;
	}


	public static void setData(String data) {
		ZWZRecoder.data = data;
	}



	public static void setRecorder(String recorder) {
		ZWZRecoder.recorder = recorder;
	}

	
	public static void KeepRecorder()
	{
		try {
			
			if(isclear<=100)
			{
			fw=new FileWriter("d:\\recorder.txt",true);
			}
			if(isclear>100)
			{
				fw=new FileWriter("d:\\recorder.txt",false);
				isclear=0;
			}
			bw=new BufferedWriter(fw);
			
			fw.write(name+"   "+data+"   "+recorder+"    "+rank+"\r\n");
			
			isclear++;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		finally {
			try {
				bw.close();
				fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
	}
	
	public static Vector<String> Getcorder()
	{
		
		try {
			
			fr=new FileReader("d:\\recorder.txt");
			br=new BufferedReader(fr);
			
			String str="";
			while((str=br.readLine())!=null)
			{		
				vStrings.add(str);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				br.close();
				fr.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return vStrings;
		
	}
	
	
}



