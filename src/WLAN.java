import java.awt.*;

/**
 * Created by tiyan on 2017/5/4.
 */
public class WLAN extends Canvas {
   int number,BUS=0;
    public WLAN(int number){
    this.number=number;
    this.setBounds(0,0,50,50);
    }

    @Override
    public void paint(Graphics g) {
       setState(BUS);
    }
    public void setState(int BUS){
        Graphics g=getGraphics();
       if(BUS==0){
        g.setColor(Color.black);}
        else
       { g.setColor(Color.GREEN);
         }
        g.fillRect(0,17,this.getWidth(),16);
           if(number%2==0){
               for(int i=0;i<number/2;i++)
               {
                   g.fillRect((this.getWidth()/number)*(2*i+1),33,5,17);
                   g.fillRect((this.getWidth()/number)*(2*i+1),0,5,17);
               }}
           else{
               for(int i=0;i<number/2+1;i++)
               {
                   g.fillRect(this.getWidth() / (number + 1)*(2*i+1), 33, 5, 17);
               }
               for(int i=0;i<number/2;i++)
               {
                   g.fillRect((this.getWidth()/number)*(2*i+1),0,5,17);

               }
           }

    }
}
