import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import java.net.*;

/**
*@authors Mykhaylo Ivchenko, Michael Hallowell
*@version Server for Chutes and Ladders Board Game
*@date April 29th, 2019
*/
        
public class Server extends JFrame 
{    
   public static Vector clients=new Vector();
   int n = 0;
   JTextArea jtxChat;
   
   int mn;
   int ln;
   int data;
   
   
   /*
   *Creates the server and the GUI for the server on which to play the game on
   */
   public Server()
   {
   
      setTitle("Server");
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setVisible(true);
      
      setLayout(new BorderLayout(5,10));
      JPanel jpNorth = new JPanel();
     jpNorth.setLayout(new GridLayout(0,1));
      
      JLabel connect = new JLabel("Connected");
      jpNorth.add(connect);
      JTextArea jtxConnect = new JTextArea(5,20);
      jpNorth.add(jtxConnect);
      add(jpNorth,BorderLayout.NORTH);
      
      
      JPanel jpSouth = new JPanel();
     jpSouth.setLayout(new GridLayout(0,1));
      
      JLabel chat = new JLabel("Chat");
      jpSouth.add(chat);
      jtxChat = new JTextArea(5,20);
      jpSouth.add(jtxChat);
      add(jpSouth,BorderLayout.SOUTH);

      
      JPanel jpCenter = new JPanel();
      jpCenter.setLayout(new GridLayout(0,1));
      
      JLabel games = new JLabel("Games Being Played");
      jpCenter.add(games);
      JTextArea jtxgames = new JTextArea(3,20);
      jpCenter.add(jtxgames);
      add(jpCenter,BorderLayout.CENTER);
      pack();
      
      /*
      *Connects to the game using ServerSocket
      *Multi threader server so it can connect to multiple games
      */
		try
      {
			ServerSocket ss = new ServerSocket(16789);
         
         /*
         *Creates the threads and helps input the information into the GUI
         */
			while(true)
         {
				Socket cs = ss.accept();
				ThreadServer t1 = new ThreadServer(cs);
				t1.start();
            n++;
            System.out.println(cs.getInetAddress().getHostName());
            System.out.println(n+" clients connected");
            /*
            *Tells you who is connected and how many people are playing
            */
            jtxConnect.append("Client " + cs.getInetAddress().getHostName() +" connected\n");
            int b = 0;
            if(n>0 && n<3)
            {
               b = 1;
            }
            else
            {
               b = n/2;
            }
            jtxgames.append("There are " + b + " games being played right now");
			}
		}
      catch(BindException be)
      {
			be.printStackTrace();
		}
      catch(IOException ioe)
      {
			ioe.printStackTrace();
		}
	}
 
   
   /*
   *Main method
   */
   public static void main(String []args)
   {  
      new Server();
      
      
      try
      {
         System.out.println("getLocalHost: "+InetAddress.getLocalHost() );
		   System.out.println("getByName:    "+InetAddress.getByName("localhost") );
         
      }
      catch(IOException e)
      {
         e.printStackTrace();
      }
   
	}  
   
   /*
   *Creates the actual server including all the readers and writeers needed to communicate with the game
   */
   class ThreadServer extends Thread
   {
      Socket cs;
      BufferedReader reader;
      PrintWriter pw;
      BufferedWriter writer;
      
      /*
      *Creates readers and writers
      */
      public ThreadServer(Socket cs)
      {
         this.cs = cs;
         
         try
         {
            pw = new PrintWriter(new OutputStreamWriter(cs.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(cs.getOutputStream())); 
            clients.add(writer);
         }
         catch(IOException ioe)
         {
            ioe.printStackTrace();
         }
     }
     
    /*
    *Runs the information that is sent back to chat
    */ 
   public void run()
   {
      try
      {
          
         while(true)
         {
            try
            {
               data = Integer.parseInt(reader.readLine().trim().substring(5));
            }
            catch(NumberFormatException nfe)
            
            {}
            
            String begin = reader.readLine().trim().substring(0,5);
            
               /*
               *Decides to send it to the chat portion
               */
             if(begin.equals("Chats"))
             {
                  String data1 = reader.readLine().trim().substring(5);
                  
                  /*
                  *Appends back to the GUI for the server on what the chat is saying
                  */
                  jtxChat.append(data1 + "\n");


                  System.out.println("Received : "+data1);
                  
                     for (int i=0;i<clients.size();i++){
                               try
                               {
                                    /*
                                    *Returns the data
                                    */
                                    BufferedWriter bw= (BufferedWriter)clients.get(i);
                                    bw.write("Chats"+data1);
                                    bw.write("\r\n");
                                    bw.flush();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                        }
               
               }
               /*
               *Sends info back as to move mario forward
               */
               else if(begin.equals("MariF"))
             {
                  
                  
                  /*
                  *Appends back to the GUI for the server on what the chat is saying
                  */
                  
                  mn = data;

                  System.out.println("Received : "+data);
                  
                     for (int i=0;i<clients.size();i++){
                               try
                               {
                                    /*
                                    *Returns the data
                                    */
                                    BufferedWriter bw= (BufferedWriter)clients.get(i);
                                    bw.write("MariF"+Integer.toString(mn));
                                    bw.write("\r\n");
                                    bw.flush();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                        }
                        
               
               }
               /*
               *Sends info back as to move mario back
               */
               else if(begin.equals("MariB"))
             {
                  
                  /*
                  *Appends back to the GUI for the server on what the chat is saying
                  */
                  
                  mn = data;

                  System.out.println("Received : "+data);
                  
                     for (int i=0;i<clients.size();i++){
                               try
                               {
                                    /*
                                    *Returns the data
                                    */
                                    BufferedWriter bw= (BufferedWriter)clients.get(i);
                                    bw.write("MariB"+Integer.toString(mn));
                                    bw.write("\r\n");
                                    bw.flush();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                        }
               }
               /*
               *Sends info back as to move luigi forward
               */
               else if(begin.equals("LuigF"))
             {
                  
                  /*
                  *Appends back to the GUI for the server on what the chat is saying
                  */
                  
                  ln = data;

                  System.out.println("Received : "+data);
                  
                     for (int i=0;i<clients.size();i++){
                               try
                               {
                                    /*
                                    *Returns the data
                                    */
                                    BufferedWriter bw= (BufferedWriter)clients.get(i);
                                    bw.write("LuigF"+Integer.toString(ln));
                                    bw.write("\r\n");
                                    bw.flush();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                        }
               }
               /*
               *Sends info back as to move luigi backwards
               */
               else if(begin.equals("LuigB"))
             {
                  
                  /*
                  *Appends back to the GUI for the server on what the chat is saying
                  */
                  
                  ln = data;

                  System.out.println("Received : "+data);
                  
                     for (int i=0;i<clients.size();i++){
                               try
                               {
                                    /*
                                    *Returns the data
                                    */
                                    BufferedWriter bw= (BufferedWriter)clients.get(i);
                                    bw.write("LuigB"+Integer.toString(ln));
                                    bw.write("\r\n");
                                    bw.flush();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                        }
               }
               
            }
           
            
         }
         catch(Exception e)
         {
            e.printStackTrace();
         }
   }
   
   }
   
}