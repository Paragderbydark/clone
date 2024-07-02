

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.io.*;
public class Client  implements ActionListener {
    static JFrame f = new JFrame(); 
    static DataOutputStream dout;
    JTextField text;
     static JPanel p2;
    static Box vertical = Box.createVerticalBox();
    Client(){
        
        
        f.setLayout(null);
        
        JPanel p1 = new JPanel();
      
        p1.setBackground(new Color(7,99,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel (i3);
        back.setBounds(5,20,25,25);
        p1.add(back); 
        
        
        back.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent as){
            System.exit(0);
}});
       //profile pic 
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel (i6);
        profile.setBounds(35,10,50,50);
        p1.add(profile);
        
        JLabel name = new JLabel("Sayan");
        name.setBounds(100,20,100,20);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18 ));
        p1.add(name);
        
        JLabel status = new JLabel ("Active now ");
        status.setBounds(100,40,100,15);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,12 ));
        p1.add(status);
        
        // text feild panel 
        
        p2 = new JPanel();
        p2.setBounds(5,75,440,572);
        f.add(p2);
        
        
        // text feild // declearing it globally to get value from text feild 
        text=new JTextField();
        text.setBounds(7,655,310,38);
        text.setFont(new Font("SAN_SERIF",Font.BOLD,14 ));
        f.add(text);
        
        //send button
        
        JButton send = new JButton("send");
        send.setForeground(Color.white);
        send.setBackground(new Color(7,99,84));
        send.setBounds(320,655,125,38);
        
        send.addActionListener(this);
        f.add(send);
    
        
        f.setUndecorated(true);
        f.setSize(450,700);
        f.setLocation(800,50);
        f.getContentPane().setBackground(Color.white);
        
        
        f.setVisible(true);
    }
    
    public void  actionPerformed(ActionEvent ae){
        try{
      String out = text.getText(); // convert to jlabel then to jpanel and the use 
      //JLabel output = new JLabel(out); // string nahi lega isiliye label banake out pass karo and add karo new panel 
      //JPanel x1 = new JPanel();
      JPanel x1 = formatLabel(out);
      //x1.add(output);
      System.out.println(out); // return the text value 
      
      p2.setLayout(new BorderLayout());//top botoom left right center around border 
      JPanel right = new JPanel (new BorderLayout());
      right.add(x1 , BorderLayout.LINE_END);//sending border layout to right hand side 
      vertical.add(right);
      vertical.add(Box.createVerticalStrut(15));// gap between two box 
      
      p2.add(vertical,BorderLayout.PAGE_START);
      
      dout.writeUTF(out);
      //for empty text
      
      text.setText("");
      
      // reload the frame 
      f.repaint(); // we need obj of Jframe which makes it reload 
      f.invalidate();
      f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }
      
    
    }
    
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        
        panel.add(output);
        
        Calendar  cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }
    public static void main (String args[]){
        new Client();
        try{
            Socket s = new Socket("127.0.0.1" , 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            
            while(true){
             p2.setLayout(new BorderLayout());
             String msg = din.readUTF();
             JPanel panel = formatLabel(msg);
             
             JPanel left = new JPanel(new BorderLayout());
             left.add(panel,BorderLayout.LINE_START);
             vertical.add(left);
             f.validate();
             
             vertical.add(Box.createVerticalStrut(15));
             p2.add(vertical,BorderLayout.PAGE_START);
             
             
        }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
