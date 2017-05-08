import javax.swing.*;

/**
 * Created by tiyan on 2017/5/4.
 */
public class NetPanel extends JPanel {
   PCPanel[] PCp=new PCPanel[10];
   int j=0;
    public NetPanel(int number,int state){
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        add(Box.createHorizontalGlue());
        if(state==0)
        for(int i=0;i<number;i=i+2)
        {
            PCp[j]=new PCPanel(i+1);
        add(PCp[j++]);
        add(Box.createHorizontalGlue());
        }
        else for(int i=1;i<number;i=i+2)
        {
            PCp[j]=new PCPanel(i+1);
            add(PCp[j++]);
            add(Box.createHorizontalGlue());
        }
    }

}
