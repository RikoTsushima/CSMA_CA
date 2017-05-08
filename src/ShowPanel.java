import javax.swing.*;



/**
 * Created by tiyan on 2017/5/3.
 */
public class ShowPanel extends JPanel {
   NetPanel[] NP=new NetPanel[2];
   WLAN wlan;
   int number;
    public  ShowPanel(int number){
        this.number=number;
           initPanel(number);
    }
    public void initPanel(int number){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        NP[0] = new NetPanel(number,0);
        if(number%2==0)
        {
            NP[1] = new NetPanel(number,1);
        }
        else
        {
            NP[1] = new NetPanel(number,1);
        }
        wlan = new WLAN(number);
        add(NP[0]);
        add(new JPanel().add(wlan));
        add(NP[1]);
    }

}
