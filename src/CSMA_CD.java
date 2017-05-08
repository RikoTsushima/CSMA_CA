import javax.swing.*;
import java.awt.*;


/**
 * Created by tiyan on 2017/5/3.
 */
public class CSMA_CD extends JFrame {
ShowPanel SP;
RightPanel RP;
Thread PCs[] = new Thread[10];

public CSMA_CD(int number){
       super("CSMA/CD模拟");
        init(number);

    }
    public void init(int number){
        Container contentPane = getContentPane();
        SpringLayout springLayout = new SpringLayout();
        contentPane.setLayout(springLayout);
        SP = new ShowPanel(number);
        RP = new RightPanel();
        contentPane.add(SP);
        springLayout.putConstraint(SpringLayout.WEST,SP,5,SpringLayout.WEST,contentPane);
        springLayout.putConstraint(SpringLayout.WEST,RP,10,SpringLayout.EAST,SP);
        springLayout.putConstraint(SpringLayout.EAST,contentPane,5,SpringLayout.EAST,RP);
        springLayout.putConstraint(SpringLayout.EAST,contentPane,150,SpringLayout.WEST,RP);
        contentPane.add(RP);
        RP.jbt1.addActionListener(new MyActionListener(PCs,RP,SP,number));
        setBounds(0,0,number*70+150,325);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    public static void main(String[] args){
        String inputValue = JOptionPane.showInputDialog("请输入要模拟的主机数（1-10） 推荐：6");
        try {
            int a = Integer.parseInt(inputValue);
            new CSMA_CD(a);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }
}
