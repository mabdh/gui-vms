/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_vms;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
       
    public class threadCapture extends Thread {  
      
      int Delay = 100; //Creating a delay or the speed of the progress bar  
      JProgressBar pb; //Constructing Progress Bar  
      JTextField jtf;
      JTextField jtf3;
      JButton btn;
      JLabel jl;
      int flag_thread;
        private static int port = 33; /* port the server listens on */
    
    private static String host = "167.205.67.39"; /* host to connect to */
    
      public  threadCapture(int flag,JProgressBar progressbar,JButton button6,JTextField textip,JTextField textport,JLabel jlab) {  
       pb = progressbar;  
       btn = button6;     
       jtf = textip;
       jtf3 = textport;
       flag_thread = flag;
       jl = jlab;
      }  
        
      //run Method. This is the area where we can adjust the performance of our progress bar.  
        @Override
      public void run() {  
           // FileWriter fw = null;
            String dirname = "/Users/muhammadabduh/Documents/Vibration Monitoring System File";
            File dir = new File(dirname);
            File oldfile;
          oldfile = new File("/Users/muhammadabduh/Documents/Vibration Monitoring System File/dump");
            
            
            
            if(!dir.exists())
            {
                dir.mkdir();
            }
            
            host = jtf.getText();
            //host = "167.205.67.61";
            port = Integer.parseInt(jtf3.getText());
            //port = 7;
     
                int minimum = 0;//pb.getMinimum(); //initializing minimum value of the progress bar
                int maximum = 200;//pb.getMaximum(); //initializing maximum value of the progress bar
                System.out.println(minimum);
                System.out.println(maximum);
                pb.setMinimum(minimum);
                pb.setMaximum(maximum);
                int ind = minimum;
                int ind2 = 0;
            try {
                FileOutputStream fout = new FileOutputStream(oldfile);
           
                
                /* Opening up the connection to the server */
                try 
                {
                    Socket           client    = new Socket(host, port);
                    DataOutputStream socketOut = new DataOutputStream(client.getOutputStream());
                    DataInputStream  socketIn  = new DataInputStream(client.getInputStream());
                    //DataInputStream  console   = new DataInputStream(System.in);
                    //PrintWriter outprint = new PrintWriter(client.getOutputStream(), true);
                    int length;
                    char c = 0;
                    String stin = null;
                    System.out.println("Connected to " + host );
                    client.setSoTimeout(5000);
//                    client.setSoLinger(true, 200);
//                     System.out.println("sl " + client.getSoLinger());
                    client.setReceiveBufferSize(10000);
//                    jta.removeAll();
                    boolean done = false;
                    //String line = "start\n";
                    //outprint.println(line);
                    //socketOut.writeBytes("$\n");
                    socketOut.writeBytes("start");
                    //socketOut.writeUTF("start");
                    //socketOut.writeByte(9);
                    //socketOut.writeChar(32);
                
                    
                    //socketOut.writeBytes("$\n");
                    //System.out.println("Send " + line );
                    do{
                               stin = "";
                                length = socketIn.available();
//                                System.out.println();
//                                System.out.println("------------------");
//                                System.out.println("length = " + length);
//                                System.out.println("------------------");
                                // create buffer
                                byte[] buf = new byte[length];
                             
                                // read the full data into the buffer
                                socketIn.readFully(buf);
                                
                                // for each byte in the buffer
                                for (byte b:buf)
                                {
                                  // convert byte to char
                                  c = (char)b; 
                                   fout.write(b);
                                   
                                   
                                   stin = stin + c;
                                   
                                   // prints character
//                                   System.out.print(c);
                                }
                                System.out.print(c);
                                if(socketIn.available()==0){
                                    jl.setText(" no input ");
                                }
                                else
                                {
                                    jl.setText("TRANSFERRING");
                                }
                                if(ind>maximum){ind=maximum;}
                                else{ind++;};
                                pb.setValue(ind);    
                                ind2++;
                                //jl.setText(stin);
//                                jta.append(stin);
//                                jta.append(ind2 + "\n");
                                //System.out.println("Ind2 : "+ind2);
                     }while(c!='#' || c=='$');
                    
                    client.shutdownInput();
                    
                    socketOut.close(); socketIn.close(); client.close();
                      System.out.println("terakhir pisan");
                      pb.setValue(maximum);
                      fout.close();
                    //if (pb.getValue() >= maximum) {    
//                        System.out.println("lebih dari maks");
                        jl.setText("SUCCESS");
                    //Test confirmation if it runs perfectly  
                    if(c=='$')
                    {
                        JOptionPane.showMessageDialog(null, "Error open file on Embedded Board!","Error!",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                     JOptionPane.showMessageDialog(null, "Test Successful!","Success!",JOptionPane.INFORMATION_MESSAGE);  
                    }

                      System.out.println("Selesai Transfer");
                      jl.setText("TRANSFER FINISHED");
                    String rtcname = null;
                      try{
                        
                        FileInputStream fstream = new FileInputStream(oldfile);
                        // Get the object of DataInputStream
                        DataInputStream fin = new DataInputStream(fstream);
                        BufferedReader bread = new BufferedReader(new InputStreamReader(fin));
                        //rtcname = null;
                        //Read File Line By Line
                        rtcname = bread.readLine();
                        System.out.println(rtcname);
                        //Close the input stream
                        fin.close();
                      }
                      catch (Exception e)
                      {
                          //Catch exception if any
                        System.out.println("Error: " + e.getMessage());
                      }
                      
                      try{
                      File newfile = new File(dirname+"/"+rtcname);
                      if(oldfile.renameTo(newfile))
                      {
                         System.out.println("Rename succesful");
                      }
                      else
                      {
                          System.out.println("Rename failed");
                          jl.setText("File Exist");
                          JOptionPane.showMessageDialog(null, "Name File Exist!","File Error",JOptionPane.INFORMATION_MESSAGE);
                      }
                      
                      }
                      catch(Exception e)
                      {
                          JOptionPane.showMessageDialog(null, "No folder C:\\Vibration Monitoring System File\\ in Exist. Create the folder!","File Error",JOptionPane.INFORMATION_MESSAGE);
                      }
                  } 
                catch (UnknownHostException e) 
                { 
                    System.err.println(host + ": unknown host."); 
                    JOptionPane.showMessageDialog(null, host + ": unknown host.","Connection Error",JOptionPane.INFORMATION_MESSAGE);
                } 
                catch (IOException e) 
                { 
                    System.err.println("I/O error with " + host); 
                    JOptionPane.showMessageDialog(null, "I/O error with " + host,"Connection Error",JOptionPane.INFORMATION_MESSAGE);
                }
            } 
            catch (FileNotFoundException ex) 
            {
                Logger.getLogger(threadCapture.class.getName()).log(Level.SEVERE, null, ex);
            }
                      btn.setEnabled(true);
                      pb.setValue(pb.getMinimum());
                      jl.setText("STATUS");
        }  
     }  