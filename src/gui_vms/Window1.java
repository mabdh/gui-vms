/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_vms;

import com.apple.eawt.Application;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
/**
 *
 * @author muhammadabduh
 */
public class Window1 extends javax.swing.JFrame {

      ArrayList<Float> x_t1;
      ArrayList<Float> y_t1;
      ArrayList<Float> x_t2;
      ArrayList<Float> y_t2;
      ArrayList<Float> x_t3;
      ArrayList<Float> y_t3;
      ArrayList<Float> x_t4;
      ArrayList<Float> y_t4;
//      ArrayList<Float> x_t5;
//      ArrayList<Float> y_t5;
      ArrayList<Float> x_f1;
      ArrayList<Float> y_f1;
      ArrayList<Float> x_f2;
      ArrayList<Float> y_f2;
      ArrayList<Float> x_f3;
      ArrayList<Float> y_f3;
      ArrayList<Float> x_f4;
      ArrayList<Float> y_f4;
//      ArrayList<Float> x_f5;
//      ArrayList<Float> y_f5;
      ArrayList<Float> x_v1;
      ArrayList<Float> y_v1;
      ArrayList<Float> x_v2;
      ArrayList<Float> y_v2;
      ArrayList<Float> x_v3;
      ArrayList<Float> y_v3;
      ArrayList<Float> x_v4;
      ArrayList<Float> y_v4;
      ArrayList<Float> x_l;
      ArrayList<Float> y_l;
      
      //File name for save file picture
      String fn = "";           
      String fileelement;
      int flag;
      //ArrayList<String> recentFileList = new ArrayList<String>();
      
      String dirname = "/Users/muhammadabduh/Documents/Vibration Monitoring System File";
      String dirnameimage = "/Users/muhammadabduh/Documents/Vibration Monitoring System File/images";
      File files = new File("/Users/muhammadabduh/Documents/Vibration Monitoring System File/images/");
      String[] strFile ;
      int nfile;
      //File directory = null;
    
      File dirFile ;
      String dir;
      JFreeChart chartt1,chartt2,chartt3,chartt4,chartt5,chartf1,chartf2,chartf3,chartf4,chartf5,chartv1,chartv2,chartv3,chartv4,chartl;
      //private static int port = 8080; /* port the server listens on */
      DefaultListModel listModel = new DefaultListModel();
     DefaultListModel listTime = new DefaultListModel();
     Thread run ;
     
     String host;
     int port;
     
     Socket client;
     DataOutputStream socketOut;
     DataInputStream  socketIn;
     
      class OpenL implements ActionListener {

         
        @Override
    public  void actionPerformed(ActionEvent e) {
       
      JFileChooser c = new JFileChooser();
      // Demonstrate "Open" dialog:
      int rVal = c.showOpenDialog(Window1.this);
      if (rVal == JFileChooser.APPROVE_OPTION) {
        
        String curdir = c.getCurrentDirectory().toString();
        System.out.println("name dir = " +  c.getSelectedFile().getName());
       //  System.out.println("cur dir = " +  c.getSelectedFile().);
        dir = curdir.replace('\\','/') + "/" +c.getSelectedFile().getName();
        //dir=curdir +"/"+c.getName();
        dirFile = new File(dir);
        jTextField2.setText(dir);
        System.out.println(dir);
      }
      if (rVal == JFileChooser.CANCEL_OPTION) {
        jTextField2.setText("");
    //    dir.setText("");
      }
    }
    
     //   public File getDirFile(){
      //      return this.dirFile;
      // }
  }
  
    /**
     * Creates new form NewJFrame
     */
    // JScrollPane scrollBar =new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
    
    public Window1() {
        initComponents();      
        setTitle( "Vibration Monitoring System" );
        flag = 1;
        strFile = new String[500];
        

        ImageIcon im = new ImageIcon(getClass().getResource("/gui_vms/vms.png"));
        Image logo = im.getImage();
//        this.setIconImage(logo);
//        setIconImage(im);
        Application application = Application.getApplication();
//        Image image = Toolkit.getDefaultToolkit().getImage("/gui_vms/vms.png");
        application.setDockIconImage(logo);
//        application.setDockIconBadge("Vibration Monitoring System");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Vibration Monitoring System");
        //browse
        
        jButton3.addActionListener(new OpenL());
        Container cp = getContentPane();
        cp.add(new Browse(), BorderLayout.SOUTH);
        jTextField2.setEditable(false);    
        
        //About
        jMenuItem8.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("About Us");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(new About());
                frame.pack();
                frame.setVisible(true);
                ImageIcon im = new ImageIcon(getClass().getResource("/gui_vms/vms.png"));
                Image logo = im.getImage();
                frame.setIconImage(logo);
            };
        });
        
        //list        
        jList1.setListData(listModel.toArray());
        jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	jList1.addListSelectionListener(new ListSelectionListener() {

                    @Override
			public void valueChanged(ListSelectionEvent e) {
                                fn = "";
                                String filename_child;
                                String[] splitchild;
                                
                               System.out.println("11 - " + listModel.get(jList1.getSelectedIndex()));
                               filename_child = listModel.get(jList1.getSelectedIndex()).toString();
                               splitchild = filename_child.split(" ");
                               for(int i = 0;i < 12;i++)
                               {
                                  fn+=splitchild[0].charAt(i);
                               }
                               System.out.println("---"+fn+"---");
                               System.out.println(dirname + "/" +fn);
                               dirFile = new File(dirname + "/" +fn);
				openFileReadyToPlot(dirFile);
                                System.out.println(dirFile.getAbsolutePath());
			}
		});
         
        //radio button
                jButton3.setEnabled(true); 
       jTextField2.setEnabled(true);
       jButton1.setEnabled(false); 
       jButton4.setEnabled(false); 
       jList1.setEnabled(false);
       jButton5.setEnabled(true);
    }
    
    public static void main(String args[]) {
         StartScreen splash = new StartScreen(3000);
         splash.showSplashAndExit();
       new Window1();
      
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
         java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
              
                new Window1().setVisible(true);
               
               new Window1().setTitle("Vibration Monitoring System v1.0");
               
            }
        });
         
         
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        jPopupMenu4 = new javax.swing.JPopupMenu();
        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jMenuItem3.setText("jMenuItem3");

        jMenuItem4.setText("jMenuItem4");

        org.jdesktop.layout.GroupLayout jFrame1Layout = new org.jdesktop.layout.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout jFrame2Layout = new org.jdesktop.layout.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout jFrame3Layout = new org.jdesktop.layout.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout jFrame4Layout = new org.jdesktop.layout.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(getIconImage());

        jButton1.setText("Update List File");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jList1.setModel(listModel);
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setToolTipText("List file ready to plot");
        jScrollPane1.setViewportView(jList1);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("STATUS");
        jLabel2.setToolTipText("");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton3.setText("Browse");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Plot!");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel4.setText("Capture Data From Sensor");

        jButton6.setText("Capture!");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jTabbedPane1.setBackground(new java.awt.Color(153, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Acceleration Sensor 1"));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 300));

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Acceleration Sensor 1", jPanel1);
        jPanel1.getAccessibleContext().setAccessibleName("tab1");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Acceleration Sensor 2"));
        jPanel4.setPreferredSize(new java.awt.Dimension(600, 300));

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Acceleration Sensor 2", jPanel4);
        jPanel4.getAccessibleContext().setAccessibleDescription("");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Acceleration Sensor 3"));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 300));

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Acceleration Sensor 3", jPanel2);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Acceleration Sensor 4"));

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Acceleration Sensor 4", jPanel5);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Frequency Sensor 1"));

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Spectrum Sensor1", jPanel7);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Frequency Sensor 2"));

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Spectrum Sensor 2", jPanel8);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Frequency Sensor 3"));

        org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Spectrum Sensor 3", jPanel9);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Frequency Sensor 4"));

        org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Spectrum Sensor 4", jPanel10);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(" Velocity Sensor 1"));

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Velocity Sensor 1", jPanel3);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Velocity Sensor 2"));

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Velocity Sensor 2", jPanel6);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Velocity Sensor 3"));

        org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Velocity Sensor 3", jPanel11);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Velocity Sensor 4"));

        org.jdesktop.layout.GroupLayout jPanel13Layout = new org.jdesktop.layout.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Velocity Sensor 4", jPanel13);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Vibration Location"));

        org.jdesktop.layout.GroupLayout jPanel12Layout = new org.jdesktop.layout.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 829, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 745, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Vibration Location", jPanel12);

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Recently Captured Data");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Open Data From File");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel1.setText("Select data to plot !");

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("192.168.0.10");
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setText("7");
        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel5.setText("IP Address");

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        jMenu1.setText("File");

        jMenuItem6.setText("Save Batch Images to Files");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem7.setText("Exit");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("About");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jSeparator4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 317, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(20, 20, 20)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(jSeparator3)
                                    .add(layout.createSequentialGroup()
                                        .add(jLabel5)
                                        .add(47, 47, 47)
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jButton6)
                                            .add(layout.createSequentialGroup()
                                                .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 91, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                        .add(0, 85, Short.MAX_VALUE))))
                            .add(layout.createSequentialGroup()
                                .add(122, 122, 122)
                                .add(jButton5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(layout.createSequentialGroup()
                                .add(20, 20, 20)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 232, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jButton3))
                                    .add(jRadioButton2)))
                            .add(layout.createSequentialGroup()
                                .add(20, 20, 20)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jRadioButton1)
                                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                        .add(jSeparator2)
                                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                            .add(52, 52, 52)
                                            .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(31, 31, 31)
                                            .add(jButton4))
                                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                            .add(98, 98, 98)
                                            .add(jLabel1))
                                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))))))
                    .add(layout.createSequentialGroup()
                        .add(96, 96, 96)
                        .add(jLabel4))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 327, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(18, 18, Short.MAX_VALUE)
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 862, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jRadioButton1)
                        .add(8, 8, 8)
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 189, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jButton1)
                            .add(jButton4))
                        .add(18, 18, 18)
                        .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jRadioButton2)
                        .add(26, 26, 26)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jButton3))
                        .add(28, 28, 28)
                        .add(jButton5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 94, Short.MAX_VALUE)
                        .add(jSeparator3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel4)
                        .add(32, 32, 32)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel5))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButton6)
                        .add(44, 44, 44)
                        .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(60, 60, 60)
                        .add(jSeparator4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Tab1");

        getAccessibleContext().setAccessibleParent(jFrame1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
             updateListFilesAndFolders(dirname);
        } catch (IOException ex) {
            Logger.getLogger(Window1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        String gambart1="/"+fn+"T1.jpg";
        String gambart2="/"+fn+"T2.jpg";
        String gambart3="/"+fn+"T3.jpg";
        String gambart4="/"+fn+"T4.jpg";
        String gambarf1="/"+fn+"F1.jpg";
        String gambarf2="/"+fn+"F2.jpg";        
        String gambarf3="/"+fn+"F3.jpg";        
        String gambarf4="/"+fn+"F4.jpg";
        String gambarv1="/"+fn+"V1.jpg";
        String gambarv2="/"+fn+"V2.jpg";        
        String gambarv3="/"+fn+"V3.jpg";        
        String gambarv4="/"+fn+"V4.jpg";
        String gambarl="/"+fn+"L.jpg"; 
                
        try {
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambart1), chartt1, 900, 600);
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambart2), chartt2, 900, 600);
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambart3), chartt3, 900, 600);
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambart4), chartt4, 900, 600);
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambarf1), chartf1, 900, 600);
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambarf2), chartf2, 900, 600);
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambarf3), chartf3, 900, 600);
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambarf4), chartf4, 900, 600);
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambarv1), chartv1, 900, 600);
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambarv2), chartv2, 900, 600);
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambarv3), chartv3, 900, 600);
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambarv4), chartv4, 900, 600);
            ChartUtilities.saveChartAsJPEG(new File(dirnameimage+gambarl), chartl, 900, 600);
            JOptionPane.showMessageDialog(null, "Images Saved!","Save file",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No folder C:\\Vibration Monitoring System File\\images\\ in Exist. Create the folder!","File Error",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // delete list // TODO add your handling code here:
        //int index_list = jList1.getSelectedIndex();
        String filehasilparsing = String.valueOf(listModel.get(jList1.getSelectedIndex()).toString()) ;
        String[] substring;
          substring = filehasilparsing.split(" ");
         dirFile = new File(dirname + "/" +substring[0]);
        //is selected list?
         System.out.println(substring[0]);
        int showConfirmDialog = JOptionPane.showConfirmDialog(null, "Do you really want to delete the file ?","Warning!", JOptionPane.YES_NO_OPTION,JOptionPane.CANCEL_OPTION);
        if (showConfirmDialog == 1)
        {
            //NO
           System.out.println("cuy");
        }
        else
        {
            //YES
            System.out.println("coi");
            dirFile.delete();
            try {
                updateListFilesAndFolders(dirname);
            } catch (IOException ex) {
                Logger.getLogger(Window1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //remove file
        //delete the list
        //update file management
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
            // TODO add your handling code here:
       //OpenL browseopen = new OpenL();
       //dirFile = new File ("C:/Vibration Monitoring System File/00_00_55.TXT");
        openFileReadyToPlot(dirFile);
              
    }//GEN-LAST:event_jButton5ActionPerformed

            
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        long startTime = System.currentTimeMillis();

        jButton6.setEnabled(false); 
          File dirrektori = new File(dirname);
          File oldfile;
          oldfile = new File("/Users/muhammadabduh/Documents/Vibration Monitoring System File/dump");
            
            
            jLabel2.setText("TRANSFERRING");
            if(!dirrektori.exists())
            {
                dirrektori.mkdir();
            }
            
            host = jTextField1.getText();
            //host = "167.205.67.61";
            port = Integer.parseInt(jTextField3.getText());
              
            try {
                  FileWriter fw = new FileWriter(oldfile);
                
                /* Opening up the connection to the server */
                try 
                {
                    client    = new Socket(host, port);
                    socketOut = new DataOutputStream(client.getOutputStream());
                    socketIn  = new DataInputStream(client.getInputStream());
                 
                   int length;
                   int indikator = 0;
                    char c = 0;
                    String stin = null;
                    System.out.println("Connected to " + host );
                    client.setSoTimeout(30000);
                    //client.setReceiveBufferSize(10000);
                   
                    boolean done = false;
                    
                    socketOut.writeBytes("s");
                    System.out.println("abis write");
                    
                    
                    String responseLine;
                    while ((responseLine = socketIn.readLine()) != null) {
                    
                    //System.out.println(responseLine);
                    //jLabel2.setText(Integer.toString(indikator));
                    //jLabel2.setText("TRANSFERRING");
                    if (responseLine.indexOf("#") != -1) {
                        fw.write(responseLine);
                        jLabel2.setText("SUCCESS");                    
                    System.out.println("Selesai Transfer");
                    jLabel2.setText("TRANSFER FINISHED");
                    JOptionPane.showMessageDialog(null, "Transfer Finished!","Success",JOptionPane.INFORMATION_MESSAGE);

                      break;
                    }
                    else if(responseLine.indexOf("OFE") != -1){
                        JOptionPane.showMessageDialog(null, "Open File Error","Error!",JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                    else{
                        fw.write(responseLine+"\n");
                    }
                          
                    indikator++;
                    }

                    System.out.println("sebelum shutdown");
                    client.shutdownInput();
                    System.out.println("abis shutdown");
                    socketOut.close(); 
                    socketIn.close(); 
                    client.close();
                    System.out.println("terakhir pisan");
                    fw.flush();
                    fw.close();
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
                               System.out.println("Rename succesful to "+ rtcname);
                               JOptionPane.showMessageDialog(null, "File save as " + rtcname,"Success",JOptionPane.INFORMATION_MESSAGE);
                            }
                            else
                            {
                                System.out.println("Rename failed");
                                jLabel2.setText("File Exist");
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
            } catch (IOException ex) {
              Logger.getLogger(Window1.class.getName()).log(Level.SEVERE, null, ex);
          }
                      jButton6.setEnabled(true);
                      jLabel2.setText("STATUS");
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        //JOptionPane.showMessageDialog(null, "Execution Time " + totalTime,"Information",JOptionPane.INFORMATION_MESSAGE);
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
       jButton3.setEnabled(true); 
       jTextField2.setEnabled(true);
       jButton1.setEnabled(false); 
       jButton4.setEnabled(false); 
       jList1.setEnabled(false);
       jButton5.setEnabled(true);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
         jButton3.setEnabled(false);
         jTextField2.setEnabled(false);
         jButton1.setEnabled(true); 
         jButton4.setEnabled(true); 
         jList1.setEnabled(true);
         jButton5.setEnabled(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JPopupMenu jPopupMenu4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    public void openFileReadyToPlot(File f){
         int i=0,i_t = 0,i_f = 0,i_l = 0,n_t1 = 0,n_t2 = 0,n_t3 = 0,n_t4 = 0;
         int n_f1 = 0,n_f2 = 0,n_f3 = 0,n_f4 = 0, n_l = 0;
         int n_v1 = 0,n_v2 = 0,n_v3 = 0,n_v4 = 0;
        x_t1 = new ArrayList<Float>();
        y_t1 = new ArrayList<Float>();
        x_t2 = new ArrayList<Float>();
        y_t2 = new ArrayList<Float>();
        x_t3 = new ArrayList<Float>();
        y_t3 = new ArrayList<Float>();
        x_t4 = new ArrayList<Float>();
        y_t4 = new ArrayList<Float>();
        
        x_f1 = new ArrayList<Float>();
        y_f1 = new ArrayList<Float>();
        x_f2 = new ArrayList<Float>();
        y_f2 = new ArrayList<Float>();
        x_f3 = new ArrayList<Float>();
        y_f3 = new ArrayList<Float>();
        x_f4 = new ArrayList<Float>();
        y_f4 = new ArrayList<Float>();
        
      x_v1 = new ArrayList<Float>();
      y_v1 = new ArrayList<Float>();
      x_v2 = new ArrayList<Float>();
      y_v2 = new ArrayList<Float>();
      x_v3 = new ArrayList<Float>();
      y_v3 = new ArrayList<Float>();
      x_v4 = new ArrayList<Float>();
      y_v4 = new ArrayList<Float>();
        x_l = new ArrayList<Float>();
        y_l = new ArrayList<Float>();
        
      
        try{
        Scanner input = new Scanner(f);
        float timex;
        float fs = 12800, N = 512;
   //------------------------------Data time 1----------------------------------------------//          
        //signature time
        String line = input.nextLine();
        System.out.println(line);
        i_t = 0;
    	//System.out.println(line);
        //data time
        while(input.hasNextFloat())
    	{
    		float value = input.nextFloat();
                timex = (float)i_t/fs;
                y_t1.add(i_t,value);
                //value = input.nextFloat();
                x_t1.add(i_t,timex);
                
    		//System.out.println(value);           
                i_t++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
        
        n_t1 = x_t1.size();
        System.out.println("n_t1 = " + n_t1);
        
     //-----------------------------Data time 2----------------------------------------------//        
        line = input.nextLine();
        System.out.println(line);
        line = input.nextLine();
        System.out.println(line);
       
        i_t = 0;
        while(input.hasNextFloat())
    	{
    		float value = input.nextFloat();
                timex = (float)i_t/fs;
                y_t2.add(i_t,value);
                //value = input.nextFloat();
                x_t2.add(i_t,timex);
                
    		//System.out.println(value);            
                i_t++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
        
        n_t2 = x_t2.size();
        System.out.println("n_t2 = " + n_t2);
   //-------------------------------Data time 3---------------------------------------------//          
        line = input.nextLine();
        System.out.println(line);
        line = input.nextLine();
        System.out.println(line);
        i_t = 0;
        while(input.hasNextFloat())
//        while(i_t<1024)
    	{
    		float value = (float)input.nextFloat();
                timex = (float)i_t/fs;                
                y_t3.add(i_t,value);
                //value = input.nextFloat();
                x_t3.add(i_t,timex);
//                System.out.println(value);
                
            
                i_t++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
        
        n_t3 = x_t3.size();
        System.out.println("n_t3 = " + n_t3);
  //-------------------------------Data time 4---------------------------------------------//           
        line = input.nextLine();
        System.out.println(line);
         line = input.nextLine();
        System.out.println(line);

        i_t = 0;
        while(input.hasNextFloat())
    	{
    		float value = input.nextFloat();
                timex = (float)i_t/fs;
                y_t4.add(i_t,value);
                //value = input.nextFloat();
                x_t4.add(i_t,timex);
    		//System.out.println(value);
                
            
                i_t++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
        
        n_t4 = x_t4.size();
        System.out.println("n_t4 = " + n_t4);
      //------------------------------Data Integral 1----------------------------------------------//          
        //signature time
        line = input.nextLine();
        System.out.println(line);
         line = input.nextLine();
        System.out.println(line);
//        float value = Float.parseFloat(input.nextLine());
//        System.out.println(value);
//        y_v1.add(0,value);
        i_t = 0;
        line = input.nextLine();
        System.out.println("a" +line);
         line = input.nextLine();
        System.out.println("b" +line);
        //data time
        while(input.hasNextFloat())
    	{
    		float value = input.nextFloat();
                timex = (float)i_t/fs;
                y_v1.add(i_t,value);
                //value = input.nextFloat();
                x_v1.add(i_t,timex);
                
    		//System.out.println(value);           
                i_t++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
        
        n_v1 = x_v1.size();
        System.out.println("n_v1 = " + n_v1);
        
     //-----------------------------Data Integral 2----------------------------------------------//        
        line = input.nextLine();
        System.out.println(line);
        line = input.nextLine();
        System.out.println(line);
//        value = Float.parseFloat(input.nextLine());
//        System.out.println(value);
//        y_v2.add(0,value);
        i_t = 0;
        while(input.hasNextFloat())
    	{
    		float value = input.nextFloat();
                timex = (float)i_t/fs;
                y_v2.add(i_t,value);
                //value = input.nextFloat();
                x_v2.add(i_t,timex);
                
    		//System.out.println(value);            
                i_t++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
        
        n_v2 = x_v2.size();
        System.out.println("n_v2 = " + n_v2);
   //-------------------------------Data Integral 3---------------------------------------------//          
        line = input.nextLine();
        System.out.println(line);
        line = input.nextLine();
        System.out.println(line);
//        value = Float.parseFloat(input.nextLine());
//        System.out.println(value);
//        y_v3.add(0,value);
        i_t = 0;
        while(input.hasNextFloat())
//        while(i_t<1024)
    	{
    		float value = (float)input.nextFloat();
                timex = (float)i_t/fs;                
                y_v3.add(i_t,value);
                //value = input.nextFloat();
                x_v3.add(i_t,timex);
//                System.out.println(value);
                
            
                i_t++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
        
        n_v3 = x_v3.size();
        System.out.println("n_v3 = " + n_v3);
  //-------------------------------Data Integral 4---------------------------------------------//           
        line = input.nextLine();
        System.out.println(line);
         line = input.nextLine();
        System.out.println(line);
//       value = Float.parseFloat(input.nextLine());
//        System.out.println(value);
//        y_v4.add(0,value);
        i_t = 0;
        while(input.hasNextFloat())
    	{
    		float value = input.nextFloat();
                timex = (float)i_t/fs;
                y_v4.add(i_t,value);
                //value = input.nextFloat();
                x_v4.add(i_t,timex);
    		//System.out.println(value);
                
            
                i_t++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
        
        n_v4 = x_v4.size();
        System.out.println("n_v4 = " + n_v4);
 //-------------------------------Data Freq 1---------------------------------------------//       
        line = input.nextLine();
        System.out.println(line);
        line = input.nextLine();
        System.out.println("a -" + line);
        
        i_f = 0;
        while(input.hasNextFloat())
    	{
    		float value = input.nextFloat();
                timex = (float)((i_f*fs)/N);
                x_f1.add(i_f,timex);
                //value = input.nextFloat();
                y_f1.add(i_f,value);
    		//System.out.println(value);
                i_f++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
       //end of freq
       n_f1 = x_f1.size();
        System.out.println("n_f1 = " + n_f1);
   //-----------------------------Data Freq 2-----------------------------------------------//   
        line = input.nextLine();
        System.out.println(line);
         line = input.nextLine();
        System.out.println(line);
        i_f = 0;
        while(input.hasNextFloat())
    	{
    		float value = input.nextFloat();
                timex = (float)((i_f*fs)/N);
                x_f2.add(i_f,timex);
                //value = input.nextFloat();
                y_f2.add(i_f,value);
    		//System.out.println(value);
                i_f++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
       //end of freq
       n_f2 = x_f2.size();
        System.out.println("n_f2 = " + n_f2);
   //-----------------------------Data Freq 3-----------------------------------------------//   
        line = input.nextLine();
        System.out.println(line);
          line = input.nextLine();
        System.out.println(line);
        i_f = 0;
        while(input.hasNextFloat())
    	{
    		float value = input.nextFloat();
                timex = (float)((i_f*fs)/N);
                x_f3.add(i_f,timex);
                //value = input.nextFloat();
                y_f3.add(i_f,value);
    		//System.out.println(value);
                i_f++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
       //end of freq
       n_f3 = x_f3.size();
        System.out.println("n_f3 = " + n_f3);
     //-----------------------------Data Freq 4-----------------------------------------------//   
        line = input.nextLine();
        System.out.println(line);
          line = input.nextLine();
        System.out.println(line);
        i_f = 0;
        while(input.hasNextFloat())
    	{
    		float value = input.nextFloat();
                timex = (float)((i_f*fs)/N);
                x_f4.add(i_f,timex);
                //value = input.nextFloat();
                y_f4.add(i_f,value);
    		//System.out.println(value);
                i_f++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
       //end of freq
       n_f4 = x_f4.size();
        System.out.println("n_f4 = " + n_f4);

   //-----------------------------Data Location-----------------------------------------------//   

        line = input.nextLine();
        System.out.println(line);
        line = input.nextLine();
        System.out.println(line);
        i_l = 0;
        while(input.hasNextFloat())
    	{
    		float value = input.nextFloat();
                x_l.add(i_l,value);
                value = input.nextFloat();
                y_l.add(i_l,value);
    		//System.out.println(value);
                i_l++;
    		//This loop will continue until it reaches the line that reads: "End of File"
    	}
        //end of file
         n_l = x_l.size();
        System.out.println("n_l = " + n_l);
        line = input.nextLine(); //EOF
        System.out.println(line);
        line = input.nextLine(); //EOF
        System.out.println(line);
        fileelement = dirFile.getName();
    	//Now we print this line to the user
    	//System.out.println(line);
        plotTime(x_t1,y_t1,x_t2,y_t2,x_t3,y_t3,x_t4,y_t4);
        plotFreq(x_f1,y_f1,x_f2,y_f2,x_f3,y_f3,x_f4,y_f4);
        plotVelocity(x_v1,y_v1,x_v2,y_v2,x_v3,y_v3,x_v4,y_v4);
        plotLocation(x_l,y_l);
    	//Now that we are done, we want to close the text document we read from.
    	input.close();
        System.out.println("File tertutup");
    	/* Lastly, we want to provide the catch statement that will print out any errors that
    	 * occur so we can deal with them.
    	 */
    	}
        catch(Exception ex)
        {//System.out.println(ex);
        JOptionPane.showMessageDialog(null, "Wrong input file !","File Error",JOptionPane.INFORMATION_MESSAGE); 
        System.out.println("null");
        }
    }

    private void plotTime(ArrayList<Float> x1, ArrayList<Float> y1,
                          ArrayList<Float> x2, ArrayList<Float> y2,
                          ArrayList<Float> x3, ArrayList<Float> y3,
                          ArrayList<Float> x4, ArrayList<Float> y4)
    {
        // float[] x = new float[1024];
       // float[] y = new float[1024];
//----------------------------Graph Time 1-------------------------//
        int k = 0, n = 0;
       n = x_t1.size();
        //plotted data
       XYSeries seriest1 = new XYSeries("Data Time 1");
          
       for(k=0;k<n;k++){
        seriest1.add(x1.get(k), y1.get(k));
        //System.out.println(y1.get(k));
       }
        XYSeriesCollection datasett1 = new XYSeriesCollection();
        datasett1.addSeries(seriest1);
        
         JFreeChart chart_t1 = ChartFactory.createXYLineChart(
            "Acceleration " + fileelement,
            "Time (s)",
            "Amplitude (m/s^2)",
            datasett1,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
         
         this.chartt1 = chart_t1;
         
        XYPlot xyplot = (XYPlot) chart_t1.getPlot();
        NumberAxis rangeAxis = (NumberAxis) xyplot.getRangeAxis();

        rangeAxis.setRange(-20.0,20.0);
         
         ChartPanel myChartt1 = new ChartPanel(chartt1);
         myChartt1.setMouseWheelEnabled(true); 
         myChartt1.setSize(20,20);
         jPanel1.removeAll();
         jPanel1.setLayout(new java.awt.BorderLayout());
         jPanel1.add(myChartt1,BorderLayout.CENTER);
         jPanel1.validate();
         
       // throw new UnsupportedOperationException("Not yet implemented");
//----------------------------Graph Time 2-------------------------//
        k = 0;
        n = 0;
       n = x_t2.size();
        //plotted data
       XYSeries seriest2 = new XYSeries("Data Time 2");
          
       for(k=0;k<n;k++){
        seriest2.add(x2.get(k), y2.get(k));   
       }
        XYSeriesCollection datasett2 = new XYSeriesCollection();
        datasett2.addSeries(seriest2);
        
         JFreeChart chart_t2 = ChartFactory.createXYLineChart(
            "Acceleration " + fileelement,
            "Time (s)",
            "Amplitude (m/s^2)",
            datasett2,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
         
         this.chartt2 = chart_t2;
         
         xyplot = (XYPlot) chart_t2.getPlot();
         rangeAxis = (NumberAxis) xyplot.getRangeAxis();

        rangeAxis.setRange(-20.0,20.0);
         
         ChartPanel myChartt2 = new ChartPanel(chart_t2);
         myChartt2.setMouseWheelEnabled(true);
         myChartt2.setSize(20,20);
         jPanel4.removeAll();
         jPanel4.setLayout(new java.awt.BorderLayout());
         jPanel4.add(myChartt2,BorderLayout.CENTER);
         jPanel4.validate();
//----------------------------Graph Time 3-------------------------//
        k = 0;
        n = 0;
       n = x_t3.size();
        //plotted data
       XYSeries seriest3 = new XYSeries("Data Time 3");
          
       for(k=0;k<n;k++){
        seriest3.add(x3.get(k), y3.get(k));   
       }
        XYSeriesCollection datasett3 = new XYSeriesCollection();
        datasett3.addSeries(seriest3);
        
         JFreeChart chart_t3 = ChartFactory.createXYLineChart(
            "Acceleration " + fileelement,
            "Time (s)",
            "Amplitude (m/s^2)",
            datasett3,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
         
         this.chartt3 = chart_t3;
         
         xyplot = (XYPlot) chart_t3.getPlot();
         rangeAxis = (NumberAxis) xyplot.getRangeAxis();

        rangeAxis.setRange(-20.0,20.0);
        
         ChartPanel myChartt3 = new ChartPanel(chart_t3);
         myChartt3.setMouseWheelEnabled(true);
         myChartt3.setSize(20,20);
         jPanel2.removeAll();
         jPanel2.setLayout(new java.awt.BorderLayout());
         jPanel2.add(myChartt3,BorderLayout.CENTER);
         jPanel2.validate();
//----------------------------Graph Time 4-------------------------//
        k = 0;
        n = 0;
       n = x_t4.size();
        //plotted data
       XYSeries seriest4 = new XYSeries("Data Time 4");
          
       for(k=0;k<n;k++){
        seriest4.add(x4.get(k), y4.get(k));   
       }
        XYSeriesCollection datasett4 = new XYSeriesCollection();
        datasett4.addSeries(seriest4);
        
         JFreeChart chart_t4 = ChartFactory.createXYLineChart(
            "Acceleration " + fileelement,
            "Time (ms)",
            "Amplitude (m/s^2)",
            datasett4,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
         
         this.chartt4 = chart_t4;
         
         xyplot = (XYPlot) chart_t4.getPlot();
         rangeAxis = (NumberAxis) xyplot.getRangeAxis();

        rangeAxis.setRange(-20.0,20.0);
         
         ChartPanel myChartt4 = new ChartPanel(chart_t4);
         myChartt4.setMouseWheelEnabled(true);
         myChartt4.setSize(20,20);
         jPanel5.removeAll();
         jPanel5.setLayout(new java.awt.BorderLayout());
         jPanel5.add(myChartt4,BorderLayout.CENTER);
         jPanel5.validate();

    }

        private void plotVelocity(ArrayList<Float> x1, ArrayList<Float> y1,
                          ArrayList<Float> x2, ArrayList<Float> y2,
                          ArrayList<Float> x3, ArrayList<Float> y3,
                          ArrayList<Float> x4, ArrayList<Float> y4)
    {
        // float[] x = new float[1024];
       // float[] y = new float[1024];
//----------------------------Graph V 1-------------------------//
        int k = 0, n = 0;
       n = x_v1.size();
        //plotted data
       XYSeries seriesv1 = new XYSeries("Data Velocity 1");
          
       for(k=0;k<n;k++){
        seriesv1.add(x1.get(k), y1.get(k));
        //System.out.println(y1.get(k));
       }
        XYSeriesCollection datasetv1 = new XYSeriesCollection();
        datasetv1.addSeries(seriesv1);
        
         JFreeChart chart_v1 = ChartFactory.createXYLineChart(
            "Velocity " + fileelement,
            "Time (ms)",
            "Amplitude (mm/s)",
            datasetv1,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
         
         this.chartv1 = chart_v1;
         
         XYPlot xyplot = (XYPlot) chart_v1.getPlot();
        NumberAxis rangeAxis = (NumberAxis) xyplot.getRangeAxis();

        rangeAxis.setRange(-20.0,20.0);
         
         ChartPanel myChartv1 = new ChartPanel(chartv1);
         myChartv1.setMouseWheelEnabled(true); 
         myChartv1.setSize(20,20);
         jPanel3.removeAll();
         jPanel3.setLayout(new java.awt.BorderLayout());
         jPanel3.add(myChartv1,BorderLayout.CENTER);
         jPanel3.validate();
         
       // throw new UnsupportedOperationException("Not yet implemented");
//----------------------------Graph V 2-------------------------//
        k = 0;
        n = 0;
       n = x_v2.size();
        //plotted data
       XYSeries seriesv2 = new XYSeries("Data Velocity 2");
          
       for(k=0;k<n;k++){
        seriesv2.add(x2.get(k), y2.get(k));   
       }
        XYSeriesCollection datasetv2 = new XYSeriesCollection();
        datasetv2.addSeries(seriesv2);
        
         JFreeChart chart_v2 = ChartFactory.createXYLineChart(
            "Velocity " + fileelement,
            "Time (ms)",
            "Amplitude (mm/s)",
            datasetv2,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
         
         this.chartv2 = chart_v2;
         
          xyplot = (XYPlot) chart_v2.getPlot();
         rangeAxis = (NumberAxis) xyplot.getRangeAxis();

        rangeAxis.setRange(-20.0,20.0);
         
         ChartPanel myChartv2 = new ChartPanel(chart_v2);
         myChartv2.setMouseWheelEnabled(true);
         myChartv2.setSize(20,20);
         jPanel6.removeAll();
         jPanel6.setLayout(new java.awt.BorderLayout());
         jPanel6.add(myChartv2,BorderLayout.CENTER);
         jPanel6.validate();
//----------------------------Graph V 3-------------------------//
        k = 0;
        n = 0;
       n = x_v3.size();
        //plotted data
       XYSeries seriesv3 = new XYSeries("Data Velocity 3");
          
       for(k=0;k<n;k++){
        seriesv3.add(x3.get(k), y3.get(k));   
       }
        XYSeriesCollection datasetv3 = new XYSeriesCollection();
        datasetv3.addSeries(seriesv3);
        
         JFreeChart chart_v3 = ChartFactory.createXYLineChart(
            "Velocity " + fileelement,
            "Time (ms)",
            "Amplitude (mm/s)",
            datasetv3,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
         
         this.chartv3 = chart_v3;
         
          xyplot = (XYPlot) chart_v3.getPlot();
         rangeAxis = (NumberAxis) xyplot.getRangeAxis();

        rangeAxis.setRange(-20.0,20.0);
         
         ChartPanel myChartv3 = new ChartPanel(chart_v3);
         myChartv3.setMouseWheelEnabled(true);
         myChartv3.setSize(20,20);
         jPanel11.removeAll();
         jPanel11.setLayout(new java.awt.BorderLayout());
         jPanel11.add(myChartv3,BorderLayout.CENTER);
         jPanel11.validate();
//----------------------------Graph V 4-------------------------//
        k = 0;
        n = 0;
       n = x_v4.size();
        //plotted data
       XYSeries seriesv4 = new XYSeries("Data Velocity 4");
          
       for(k=0;k<n;k++){
        seriesv4.add(x4.get(k), y4.get(k));   
       }
        XYSeriesCollection datasetv4 = new XYSeriesCollection();
        datasetv4.addSeries(seriesv4);
        
         JFreeChart chart_v4 = ChartFactory.createXYLineChart(
            "Velocity " + fileelement,
            "Time (ms)",
            "Amplitude (mm/s)",
            datasetv4,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
         
         this.chartv4 = chart_v4;
         
          xyplot = (XYPlot) chart_v4.getPlot();
         rangeAxis = (NumberAxis) xyplot.getRangeAxis();

        rangeAxis.setRange(-20.0,20.0);
         
         ChartPanel myChartv4 = new ChartPanel(chart_v4);
         myChartv4.setMouseWheelEnabled(true);
         myChartv4.setSize(20,20);
         jPanel13.removeAll();
         jPanel13.setLayout(new java.awt.BorderLayout());
         jPanel13.add(myChartv4,BorderLayout.CENTER);
         jPanel13.validate();

    }
    
    private void plotFreq(ArrayList<Float> x1, ArrayList<Float> y1,
                          ArrayList<Float> x2, ArrayList<Float> y2,
                          ArrayList<Float> x3, ArrayList<Float> y3,
                          ArrayList<Float> x4, ArrayList<Float> y4 )
    {
//----------------------------Graph Freq 1-------------------------//        
        int k = 0,n = 0;
        n = x_f1.size();
        float ff;
        //plotted data
          XYSeries seriesf1 = new XYSeries("Spectrum 1");
       for(k=0;k<256;k++){
        ff=(float) (y1.get(k)* 2.000); 
        y1.set(k, ff);
       }
       for(k=0;k<256;k++){
        seriesf1.add(x1.get(k), y1.get(k));   
       }
        XYSeriesCollection datasetf1 = new XYSeriesCollection();
        datasetf1.addSeries(seriesf1);
        
         JFreeChart chart_f1 = ChartFactory.createXYLineChart(
            "Frequency Domain " + fileelement,
            "Frequency (Hz)",
            "Magnitude",
            datasetf1,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
          this.chartf1 = chart_f1;
        
          XYPlot xyplot = (XYPlot) chart_f1.getPlot();
        NumberAxis rangeAxis = (NumberAxis) xyplot.getRangeAxis();

        rangeAxis.setRange(0.0,2000.0);
          
         ChartPanel myChartf1 = new ChartPanel(chart_f1);
         myChartf1.setMouseWheelEnabled(true);
         jPanel7.removeAll();
         jPanel7.setLayout(new java.awt.BorderLayout());
         jPanel7.add(myChartf1,BorderLayout.CENTER);
         jPanel7.validate();

        //throw new UnsupportedOperationException("Not yet implemented");
//----------------------------Graph Freq 2-------------------------//        
        k = 0;
        n = 0;
        n = x_f2.size();
        //plotted data
          XYSeries seriesf2 = new XYSeries("Spectrum 2");
      for(k=0;k<256;k++){
        ff=(float) (y2.get(k)* 2.000); 
        y2.set(k, ff);
       }
       for(k=0;k<256;k++){
        seriesf2.add(x2.get(k), y2.get(k));   
       }
        XYSeriesCollection datasetf2 = new XYSeriesCollection();
        datasetf2.addSeries(seriesf2);
        
         JFreeChart chart_f2 = ChartFactory.createXYLineChart(
            "Frequency Domain " + fileelement,
            "Frequency (Hz)",
            "Magnitude",
            datasetf2,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
          this.chartf2 = chart_f2;
          
        xyplot = (XYPlot) chart_f2.getPlot();
        rangeAxis = (NumberAxis) xyplot.getRangeAxis();

        rangeAxis.setRange(0.0,2000.0);
          
         ChartPanel myChartf2 = new ChartPanel(chart_f2);
         myChartf1.setMouseWheelEnabled(true);
         jPanel8.removeAll();
         jPanel8.setLayout(new java.awt.BorderLayout());
         jPanel8.add(myChartf2,BorderLayout.CENTER);
         jPanel8.validate();
        //throw new UnsupportedOperationException("Not yet implemented");
//----------------------------Graph Freq 3-------------------------//        
        k = 0;
        n = 0;
        n = x_f3.size();
        //plotted data
          XYSeries seriesf3 = new XYSeries("Spectrum 3");
        for(k=0;k<256;k++){
        ff=(float) (y3.get(k)* 2.000); 
        y3.set(k, ff);
       }
       for(k=0;k<256;k++){
        seriesf3.add(x3.get(k), y3.get(k));   
       }
        XYSeriesCollection datasetf3 = new XYSeriesCollection();
        datasetf3.addSeries(seriesf3);
        
         JFreeChart chart_f3 = ChartFactory.createXYLineChart(
            "Frequency Domain " + fileelement,
            "Frequency (Hz)",
            "Magnitude",
            datasetf3,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
          this.chartf3 = chart_f3;
        
          xyplot = (XYPlot) chart_f3.getPlot();
        rangeAxis = (NumberAxis) xyplot.getRangeAxis();

        rangeAxis.setRange(0.0,2000.0);
          
         ChartPanel myChartf3 = new ChartPanel(chart_f3);
         myChartf3.setMouseWheelEnabled(true);
         jPanel9.removeAll();
         jPanel9.setLayout(new java.awt.BorderLayout());
         jPanel9.add(myChartf3,BorderLayout.CENTER);
         jPanel9.validate();
        //throw new UnsupportedOperationException("Not yet implemented");
//----------------------------Graph Freq 4-------------------------//        
        k = 0;
        n = 0;
        n = x_f4.size();
        //plotted data
          XYSeries seriesf4 = new XYSeries("Spectrum 4");
        for(k=0;k<256;k++){
        ff=(float) (y4.get(k)* 2.000); 
        y4.set(k, ff);
       }
       for(k=0;k<256;k++){
        seriesf4.add(x4.get(k), y4.get(k));   
       }
        XYSeriesCollection datasetf4 = new XYSeriesCollection();
        datasetf4.addSeries(seriesf4);
        
         JFreeChart chart_f4 = ChartFactory.createXYLineChart(
            "Frequency Domain " + fileelement,
            "Frequency (Hz)",
            "Magnitude",
            datasetf4,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
          this.chartf4 = chart_f4;
        
          xyplot = (XYPlot) chart_f4.getPlot();
        rangeAxis = (NumberAxis) xyplot.getRangeAxis();

        rangeAxis.setRange(0.0,2000.0);
          
         ChartPanel myChartf4 = new ChartPanel(chart_f4);
         myChartf4.setMouseWheelEnabled(true);
         jPanel10.removeAll();
         jPanel10.setLayout(new java.awt.BorderLayout());
         jPanel10.add(myChartf4,BorderLayout.CENTER);
         jPanel10.validate();
        //throw new UnsupportedOperationException("Not yet implemented");

    }
    
    private void plotLocation(ArrayList<Float> x, ArrayList<Float> y) {
        int k = 0,n = 0;
        
        n = x_l.size();
        //plotted data
          XYSeries series = new XYSeries("Location");
       for(k=0;k<n;k++){
        series.add(x.get(k), y.get(k));   
       }
       
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        
        JFreeChart chart = ChartFactory.createScatterPlot(
            "Location " + fileelement,
            "x (cm)",
            "y (cm)",
            dataset,
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );
        
       // range
         ChartPanel myChart = new ChartPanel(chart);
          this.chartl = chart;
        
          XYPlot xyplot = (XYPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) xyplot.getRangeAxis();
        NumberAxis domainAxis = (NumberAxis) xyplot.getDomainAxis();
        rangeAxis.setRange(0,100);
        rangeAxis.setTickUnit(new NumberTickUnit(2.0));
        domainAxis.setRange(0,100);
        domainAxis.setTickUnit(new NumberTickUnit(2.0));
          
         myChart.setRangeZoomable(false);
         myChart.setDisplayToolTips(true);
         
         
        
       //  myChart.mouseMoved(MouseEvent e);         
         jPanel12.removeAll();
         jPanel12.setLayout(new java.awt.BorderLayout());
         jPanel12.add(myChart,BorderLayout.CENTER);
         jPanel12.validate();
        //throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private void UpdatefileManagement(String[] strFile, int nfile) {
        
	if(files.isDirectory()){
                strFile = files.list();
 
		if(files.list().length>0){
                       nfile = files.list().length;
                       System.out.println("Directory is not empty!");
                       System.out.println("isi file " + nfile);
                                              
		}else{
                       System.out.println("Directory is empty!");
 
		}
 
	}else{
 
		System.out.println("This is not a directory");
 
	}
    }
    
    public void updateListFilesAndFolders(String directoryName) throws IOException{
 
        File directory = new File(directoryName);
        Path p; 
        BasicFileAttributes view;
        String[]time;
        //get all the files from a directory
        File[] fList = directory.listFiles();
        int i = 0;
         listModel.removeAllElements();
         listTime.removeAllElements();
        for (File file : fList){
        System.out.println(file.getName());
        if(file.isDirectory()){
        
        }
        else
        {
            if("dump".equals(file.getName()))
            {

            }
            else if(".DS_Store".equals(file.getName()))
            {
                
            }
            else if("null".equals(file.getName()))
            {
                
            }
            else
            {
                p = Paths.get("/Users/muhammadabduh/Documents/Vibration Monitoring System File/" + file.getName());
                view = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();
                time = (view.creationTime()).toString().split("T");
                listModel.addElement(file.getName() + "  -  " + time[0]);
            }
        }
        
        i++;
        }

        //listModel.addElement(recentFileList);
        jList1.setListData(listModel.toArray());
       
    }
}
