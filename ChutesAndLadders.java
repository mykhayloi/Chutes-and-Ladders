/**
*@authors Mykhaylo Ivchenko, Michael Hallowell
*@version Chutes and Ladders Board Game
*@date April 29th, 2019
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import java.net.*;


/*
*Constructor for the Chutes and Ladders game
*/
public class ChutesAndLadders extends JFrame implements ActionListener, Runnable
{
   ArrayList<JButton> buttonList = new ArrayList<JButton>();
   private JButton roll;
   private JMenu menu;
   private JMenuItem rules;
   private JMenu gameOpt;
   private JMenuItem restart;
   private JMenuItem scramble;
   private JTextArea die;
   private int marioPos = 63;
   private int luigiPos = 63;
   private int mariowin = 0;
   private int luigiwin = 0;
   public JTextField tx;
    public JTextArea ta;
    public String name = "";
   ImageIcon both = new ImageIcon(((new ImageIcon("both.jpg")).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
   
   ImageIcon luigi = new ImageIcon(((new ImageIcon("luigi.jpg")).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
   ImageIcon mario = new ImageIcon(((new ImageIcon("mario.jpg")).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
   private JButton button1;
   private JButton button2;
   private JButton button3;
   private JButton button4;
   
   private JButton mWin;
   private JButton lWin;
   
   private static String msg;
   
   BufferedWriter writer;
   BufferedReader reader;
   PrintWriter out = null;
   BufferedReader in = null;
   
   private ArrayList<Integer> o = new ArrayList<Integer>();
   
   int n = 0;
   String num;
   
   int n1 = 0;
   int n2 = 0;
   
   BufferedWriter writer1;
   BufferedReader reader1;
   
   /*
   *Creates the GUI that holds the game
   */
   public ChutesAndLadders(String n)
   {
      name  = n;
      JMenuBar mbar = new JMenuBar();
     setJMenuBar(mbar);
      
     
     menu = new JMenu("Menu");
     rules = new JMenuItem("How-To Play");

     mbar.add(menu);
     menu.add(rules);

    
     rules.addActionListener(this);
     
     setLayout(new BorderLayout(5,10));
      
     JPanel jpCenter = new JPanel();
     jpCenter.setLayout(new GridLayout(0,8));    
     
     /*
     *Creates all the buttons
     */ 
     for(int i = 64; i > 0; i--) 
     {
        JButton button = new JButton(Integer.toString(i));
        button.setPreferredSize(new Dimension(80,80));
        button.setEnabled(false);      
        buttonList.add(button);
        jpCenter.add(button);
     }
     
     /*
     *Creates where all the red and green spots go
     */
     buttonList.get(3).setBackground(Color.RED);
     buttonList.get(6).setBackground(Color.GREEN);
     buttonList.get(7).setBackground(Color.RED);
     buttonList.get(12).setBackground(Color.GREEN);
     buttonList.get(15).setBackground(Color.GREEN);
     buttonList.get(18).setBackground(Color.RED);
     buttonList.get(19).setBackground(Color.GREEN);
     buttonList.get(20).setBackground(Color.RED);
     buttonList.get(22).setBackground(Color.GREEN);
     buttonList.get(29).setBackground(Color.RED);
     buttonList.get(35).setBackground(Color.RED);
     buttonList.get(36).setBackground(Color.GREEN);
     buttonList.get(42).setBackground(Color.RED);
     buttonList.get(48).setBackground(Color.GREEN);
     buttonList.get(51).setBackground(Color.RED);
     buttonList.get(56).setBackground(Color.GREEN);
     buttonList.get(60).setBackground(Color.RED);

     
     /*
     *Creates all the panels and everything that goes around the sides
     */
     JPanel jpSouth = new JPanel();
     jpSouth.setLayout(new FlowLayout());
      
     roll = new JButton("Roll");
     jpSouth.add(roll);    

     roll.addActionListener(this);
     
     JPanel jpNorth2 = new JPanel();
     JPanel jpNorth1 = new JPanel();
     JPanel jpNorth = new JPanel();
     jpNorth2.setLayout(new FlowLayout());
     jpNorth1.setLayout(new FlowLayout());
     jpNorth.setLayout(new GridLayout(0,1));
     
     JLabel mario1 = new JLabel("Mario: ");
     JLabel luigi1 = new JLabel("Luigi: ");
     button1 = new JButton("Move Forward");
     button2 = new JButton("Move Backward");
     button3 = new JButton("Move Forward");
     button4 = new JButton("Move Backward");  
     
     jpNorth2.add(luigi1);    
     jpNorth2.add(button1);
     jpNorth2.add(button2);
     
     jpNorth1.add(mario1);
     jpNorth1.add(button3);
     jpNorth1.add(button4);
     jpNorth.add(jpNorth1);
     jpNorth.add(jpNorth2);
     
     button1.addActionListener(this);
     button2.addActionListener(this);
     button3.addActionListener(this);
     button4.addActionListener(this);
     
     JPanel jpWest = new JPanel();
     jpWest.setLayout(new GridLayout(0,2));
     
     /*
     *Creates the wins column for both Mario and Luigi
     */
     mWin = new JButton();
     lWin = new JButton();
     JLabel mName = new JLabel("Mario: ");
     JLabel lName = new JLabel("Luigi: ");
     
     jpWest.add(mName);
     jpWest.add(mWin);
     jpWest.add(lName);
     jpWest.add(lWin);
     
     mWin.setEnabled(false);
     lWin.setEnabled(false);
     
     mWin.setText("Wins:\n "+mariowin);
     lWin.setText("Wins:\n "+luigiwin);

     add(jpNorth, BorderLayout.NORTH);
     add(jpCenter, BorderLayout.CENTER);
     add(jpSouth, BorderLayout.SOUTH);
     add(jpWest, BorderLayout.WEST);
     pack();
     
     buttonList.get(63).setEnabled(true);
     buttonList.get(63).setIcon(both);
     
     /*
     *Begins forming the chat in the GUI
     */
     JPanel jpEast  =new JPanel();
     jpEast.setLayout(new GridLayout(0,1));
     
     JPanel jp1 =new JPanel();
     jp1.setLayout(new BorderLayout());
            
     JPanel jp2=new JPanel();
     jp2.setLayout(new BorderLayout());        
        
     tx=new JTextField(10);
     jp1.add(tx, BorderLayout.CENTER);
        
     JButton jbSend =new JButton("Send");
     jp1.add(jbSend, BorderLayout.EAST); 
     
    
     
        
     ta=new JTextArea(20,20);
     
      JScrollPane scroll = new JScrollPane(ta);
     scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
     
     jp2.add(scroll, BorderLayout.CENTER);
     jp2.add(jp1, BorderLayout.SOUTH);
  
      jpEast.add(jp2);
      add(jpEast,BorderLayout.EAST);
      
      /*
      *Connects the entire gui to the server
      */
      try
      {
         Socket socketClient1= new Socket("localhost",16789);
         writer= new BufferedWriter(new OutputStreamWriter(socketClient1.getOutputStream()));
         reader =new BufferedReader(new InputStreamReader(socketClient1.getInputStream())); 
         	
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      
      /*
      *Is the action listener to the send button
      */
      jbSend.addActionListener (new ActionListener(){
            public void actionPerformed(ActionEvent ev){            
                
                String s=name+" : "+tx.getText();  
                tx.setText("");
                try
                {
                    writer.write("Chats"+s);
                    writer.write("\r\n");
                    writer.flush(); 
                }
                catch(Exception e)
                {
                  e.printStackTrace();
                }
            }
          }
        );
        

      
      /*
      *Button that send the mario character forward
      */
      button3.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ev){
            try
            {
               n1++;
               writer.write("MariF"+Integer.toString(n1));
               writer.write("\r\n");
               writer.flush();
            }
            catch(Exception e)
            {
               e.printStackTrace();
            }
         }
      });
      /*
      *Button that send the mario character backwards
      */
      button4.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ev){
            try
            {
               n1--;
               writer.write("MariB"+Integer.toString(n1));
               writer.write("\r\n");
               writer.flush();
            }
            catch(Exception e)
            {
               e.printStackTrace();
            }
         }
      });
      /*
      *Button that send the luigi character forward
      */
      button1.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ev){
            try
            {
               n2++;
               writer.write("LuigF"+Integer.toString(n2));
               writer.write("\r\n");
               writer.flush();
            }
            catch(Exception e)
            {
               e.printStackTrace();
            }
         }
      });
      /*
      *Button that send the luigi character backwards
      */
      button2.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ev){
            try
            {
               n2--;
               writer.write("LuigB"+Integer.toString(n2));
               writer.write("\r\n");
               writer.flush();
            }
            catch(Exception e)
            {
               e.printStackTrace();
            }
         }
      });


  }
  
  
  /*
  *Runs the different things your GUI has to do
  */
  public void run()
    {
  

      try
      {       
          /*
          *Checks if the information is for the chat or the pieces.
          */ 
                  String serverMsg=""; 
                  while((serverMsg = reader.readLine()) != null)
                  {
                  /*
                  *appends the chat to the textarea to the right of the board
                  */
                  if(serverMsg.substring(0,5).equals("Chats"))
                  {
                     System.out.println("from server: " + serverMsg);
                     ta.append(serverMsg.substring(5)+"\n");
                  }
                  /*
                  *Checks what the server said and moves mario forward
                  */
                  else if(serverMsg.substring(0,5).equals("MariF"))
                  {
                     System.out.println("from server: " + serverMsg + "Yay");
                     
                     n1 = Integer.parseInt(serverMsg.substring(5));
                     marioPos = 63 - n1;
                     
                     buttonList.get(marioPos + 1).setIcon(null);
                     buttonList.get(marioPos + 1).setEnabled(false);
                    

                     if(marioPos < 1)
                     {
                        marioPos = 0;
                        buttonList.get(0).setEnabled(true);
                        buttonList.get(0).setIcon(mario);
                        JOptionPane.showMessageDialog(null,"Congradulations! Mario is the winner!");
                        mariowin++;
                        mWin.setText("Wins:\n "+mariowin);
                     }
                     buttonList.get(marioPos).setEnabled(true);
                     buttonList.get(marioPos).setIcon(mario);
                     buttonList.get(luigiPos).setEnabled(true);
                     buttonList.get(luigiPos).setIcon(luigi);
                     if(marioPos == luigiPos)
                     {
                        buttonList.get(marioPos).setIcon(both);
                     }
                  }
                  /*
                  *Checks what the server said and moves mario backwards
                  */
                  else if(serverMsg.substring(0,5).equals("MariB"))
                  {
                     System.out.println("from server: " + serverMsg + "YayB");
                     n1 = Integer.parseInt(serverMsg.substring(5));
                     marioPos = 63 - n1;
                     
                     buttonList.get(marioPos-1).setIcon(null);
                     buttonList.get(marioPos-1).setEnabled(false);
                     
                     if(marioPos>63)
                     {
                        marioPos=63;
                        JOptionPane.showMessageDialog(null,"That is the beginning of the board, you can not move back any further");
                     }
                     buttonList.get(marioPos).setEnabled(true);
                     buttonList.get(marioPos).setIcon(mario);
                     buttonList.get(luigiPos).setEnabled(true);
                     buttonList.get(luigiPos).setIcon(luigi);
                     if(marioPos == luigiPos)
                     {
                        buttonList.get(marioPos).setIcon(both);
                     }
                  }
                  /*
                  *Checks what the server said and moves luigi forward
                  */
                  else if(serverMsg.substring(0,5).equals("LuigF"))
                  {
                     System.out.println("from server: " + serverMsg + "YayB");
                     n2 = Integer.parseInt(serverMsg.substring(5));
                     luigiPos = 63 - n2;
                     
                     buttonList.get(luigiPos + 1).setIcon(null);
                     buttonList.get(luigiPos + 1).setEnabled(false);

                     if(luigiPos < 1)
                     {
                        luigiPos = 0;
                        buttonList.get(0).setEnabled(true);
                        buttonList.get(0).setIcon(luigi);
                        JOptionPane.showMessageDialog(null,"Congradulations! Luigi is the winner!");
                        luigiwin++;
                        lWin.setText("Wins:\n "+luigiwin);
                     }
                     buttonList.get(luigiPos).setEnabled(true);
                     buttonList.get(luigiPos).setIcon(luigi);
                     buttonList.get(marioPos).setEnabled(true);
                     buttonList.get(marioPos).setIcon(mario);
                     if(marioPos == luigiPos)
                     {
                        buttonList.get(marioPos).setIcon(both);
                     }
                  }
                  /*
                  *Checks what the server said and moves luigi backwards
                  */
                  else if(serverMsg.substring(0,5).equals("LuigB"))
                  {
                     System.out.println("from server: " + serverMsg + "YayB");
                     n2 = Integer.parseInt(serverMsg.substring(5));
                     luigiPos = 63 - n2;
                     
                     buttonList.get(luigiPos - 1).setIcon(null);
                     buttonList.get(luigiPos - 1).setEnabled(false);


                     if(luigiPos>63)
                     {
                        luigiPos=63;
                        buttonList.get(63).setIcon(luigi);
                        JOptionPane.showMessageDialog(null,"That is the beginning of the board, you can not move back any further");
                     }
                     buttonList.get(luigiPos).setEnabled(true);
                     buttonList.get(luigiPos).setIcon(luigi);
                     buttonList.get(marioPos).setEnabled(true);
                     buttonList.get(marioPos).setIcon(mario);
                     if(marioPos == luigiPos)
                     {
                        buttonList.get(marioPos).setIcon(both);
                     }
                  }
                  }
      }

      catch(Exception e)
      {
         e.printStackTrace();
      }   

      

    }

       
  /*
  *Action listeners for all the buttons, in control of all the movement and menu options
  */     
  @Override
  public void actionPerformed(ActionEvent ae)
  {
      Object choice = ae.getSource();
      
      /*
      *Exaplins how to play the game
      */
      if(ae.getActionCommand().equals("How-To Play"))
      {
         JOptionPane.showMessageDialog(null,"Two players begin at spot number one. Whoever reaches spot 64 first wins. Movement is relatively simple. Each turn the player gets to roll a die. \n He or she will then move that many spaces. If a player lands on a red box they have to move back 3 steps. On the other hand,\n if a player lands on a green box that player is allowed to move forward 4 steps.");
      }
      

      /*
      *Rolls a die so you know how many spaces to move
      */
      else if(choice == roll)
      {
      
         int roll = (int)(Math.random() * 6 + 1);
         JOptionPane.showMessageDialog(null,"You have rolled a "+Integer.toString(roll));
      }


         
   }
  

  /*
  *public static void method 
  */
  public static void main(String[] args)
  {
      
      ChutesAndLadders myFrame = new ChutesAndLadders(args[0]);
      myFrame.setTitle("Chutes and Ladders");
      myFrame.pack();
      myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      myFrame.setLocationRelativeTo(null);
      myFrame.setVisible(true);
      
      try
      {
         Thread t1 = new Thread(myFrame);
         t1.start();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }

      
   /*
   *Paints the icons on each button
   */
      public void paintComponent(Graphics g)
      {
         paintComponent(g);
      }
   } 


      
      