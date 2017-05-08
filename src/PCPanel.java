import javax.swing.*;
import java.awt.*;

/**
 * Created by tiyan on 2017/5/3.
 */
public class PCPanel extends JPanel{

   JLabel jlb1,jlb3,jlb2,jlb;
   JProgressBar jProgressBar;
    public PCPanel(int ID){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        jlb = new JLabel("PC"+ID);
        jlb.setAlignmentX((float)0.5);

        jlb1 = new JLabel(new ImageIcon("src/pc.png"));
        jlb1.setAlignmentX((float) 0.50);

        jlb3 = new JLabel("成功次数"+"0");
        jlb3.setAlignmentX((float) 0.50);

        jlb2 = new JLabel("失败次数"+"0");
        jlb2.setAlignmentX((float) 0.50);

        jProgressBar = new JProgressBar();
        jProgressBar.setPreferredSize(new Dimension(10,18));
        jProgressBar.setStringPainted(true);

        add(jlb1);
        add(Box.createVerticalStrut(-35));
        add(jlb);
        add(Box.createVerticalStrut(11));
        add(jProgressBar);
        add(jlb3);
        add(jlb2);
        setState(0);
    }
public void setState(int state){
   if(state==1){
       jProgressBar.setString("传输中···");
    setBackground(Color.GREEN);
   }
   else if(state==0){
       jProgressBar.setString("准备发送");
       setBackground(Color.ORANGE);
   }
   else if(state==2){
       jProgressBar.setString("接收中");
       setBackground(Color.blue);}
   else if(state==3){
       jProgressBar.setString("等待重传");
       setBackground(Color.PINK);
   }
   else if(state==4){
       jProgressBar.setString("监听中");
       setBackground(Color.yellow);
   }
   else if(state==5){
       jProgressBar.setString("结束传输");
       setBackground(Color.lightGray);
   }
}
public void  setState(int state,int r){
    jProgressBar.setString("等待重传,r="+String.valueOf(r));
    setBackground(Color.PINK);
}
public void successtime(int time){
    jlb3.setText("成功次数"+String.valueOf(time));
}
public void failtime(int time){
    jlb2.setText("失败次数"+String.valueOf(time));
}

}
