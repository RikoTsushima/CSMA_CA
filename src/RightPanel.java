import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by tiyan on 2017/5/3.
 */
public class RightPanel extends JPanel{
    JButton jbt1;
    JLabel jlb,jlb1,jlb2;
    JScrollPane jsp;
    JTextArea jta;
    JPanel jp2,jp1;
    JTextField speedtf;
    JSlider jsl;
    Point p = new Point();
    int height=15,speed=50;
    public RightPanel(){
        init();
    }

    public void addMessages(int ID,int state){
       if(state==0) jta.append("PC"+ID+":ID send success!\n");
        else if(state==1) jta.append("PC"+ID+":ID send collision!\n");
        else if(state==2) jta.append("PC"+ID+":ID send failure\n");
       p.setLocation(0,this.jta.getLineCount()*height);
       this.jsp.getViewport().setViewPosition(p);
   }

    public void addslider(){
       jsl = new JSlider(10,100);
       jsl.setSize(60,10);
       jsl.setValue(40);
       jsl.setPreferredSize(new Dimension(50,20));

       speedtf = new JTextField("运行速度：40");
       speedtf.setEditable(false);

       jsl.addChangeListener(new ChangeListener() {
           @Override
           public void stateChanged(ChangeEvent e) {
               JSlider source = (JSlider) e.getSource();
               speedtf.setText("运行速度："+String.valueOf(source.getValue()));
               speed=7500/(source.getValue()*3);
           }
       });
       jp2.add(speedtf);
       jp2.add(jsl);
       jp2.add(Box.createVerticalStrut(60));
    }

    public void init(){
       setLayout(new GridLayout(2,1));
       jbt1 = new JButton("开始演示");
       jlb = new JLabel("冲突窗口=0.005");
       jlb1 = new JLabel("帧间间隔=0.0009");
       jlb2 = new JLabel("干扰信号=0.0004");

       jta = new JTextArea(10,12);
       jta.setEditable(false);
       jta.setLineWrap(true);
       jsp = new JScrollPane(jta);

       jp1 = new JPanel();
       jp2 = new JPanel();
       jp2.setLayout(new BoxLayout(jp2,BoxLayout.Y_AXIS));


       jp1.add(jsp);

       jp2.add(jbt1);
       jp2.add(jlb);
       jp2.add(jlb1);
       jp2.add(jlb2);


       addslider();
       add(jp1);
       add(jp2);
    }
}
