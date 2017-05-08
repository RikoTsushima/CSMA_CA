import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by tiyan on 2017/5/5.
 */
public class MyActionListener implements ActionListener {
   Thread PCs[];
   RightPanel RP;
   int number;
   ShowPanel SP;
    public MyActionListener(Thread Pcs[],RightPanel RP,ShowPanel SP,int number){
       this.PCs=Pcs;
       this.RP=RP;
       this.SP=SP;
       this.number=number;
   }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(RP.jbt1)){
            for(int i=0;i<number;i++)
            {PCs[i]= new Thread(new PC(i+1,SP.wlan,SP.NP[i%2].PCp[i/2],RP,RP.speed));
                PCs[i].start();
            }

        }
       RP.jsl.setEnabled(false);
    }
}
