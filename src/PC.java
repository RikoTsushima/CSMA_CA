import java.util.Random;

/**
 * Created by tiyan on 2017/5/4.
 */
public class PC implements Runnable {
   private int ID,data=(int)(Math.random()*200+50),successtime=0,failtime=0,r,monitor;
   int collision=50,speed=10,JammingSignal=4;
   RightPanel RP;
   private PCPanel pcPanel;
   private boolean iscollision=false,isreach=false;
   WLAN wlan;
    public PC(int ID,WLAN wlan,PCPanel pcPanel,RightPanel RP,int speed){
        this.ID=ID;
        this.wlan=wlan;
        this.pcPanel=pcPanel;
        this.RP=RP;
        this.speed=speed;
   }
    @Override
    public void run() {

            while (successtime < 5) {
                if (wlan.BUS == ID)
                    pcPanel.setState(2);
                else pcPanel.setState(4);
                if (wlan.BUS == 0) {

                    for( monitor=0;monitor<9;monitor++){
                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    if(wlan.BUS!=0)break;}
                    if(monitor==9)
                        output();
                    else {waiting(); wlan.setState(0);}
                } else {waiting(); wlan.setState(0);}
            }
            pcPanel.setState(5);
        int tag=0;
        while(true) {

            if(wlan.BUS==ID&&tag==0) {pcPanel.setState(2);tag=1;}//传输完成后有可能接收数据
            if(wlan.BUS!=ID&&tag==1){pcPanel.setState(5);tag=0;}
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void output(){
         int IDto=(ID*2)%(wlan.number+1);
         wlan.setState(1);//设定传输目标
        pcPanel.setState(1);
        for (int i = 0; i <  collision; i++) //在争用期期间检测碰撞
        {
            if(i==collision/2) {wlan.BUS = IDto;isreach=true;}

            if(i%5==0)pcPanel.jProgressBar.setValue(i*100/data);

            if(((wlan.BUS!=IDto)&&isreach)||((wlan.BUS!=0)&&!isreach))
            {   isreach=false;
                iscollision=true;
                break;
            } //若有碰撞则停止传输

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!iscollision){
            isreach=false;
        for (int i = collision; i < data; i++) //争用期内无碰撞，将数据发送完毕
        {   wlan.setState(1);
            if(i%5==0)pcPanel.jProgressBar.setValue(i*100/data);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pcPanel.setState(0);
        pcPanel.jProgressBar.setValue(0);
        pcPanel.successtime(++successtime);
        RP.addMessages(ID,0);
        failtime=0;               //发送成功后重置失败次数
        pcPanel.failtime(0);
            new Thread(new Delaythread(wlan,IDto,collision,speed)).start();
            for(int i=0; i<10;i++) //帧间最小间隔
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}
        else
            {   pcPanel.setState(3);
                pcPanel.failtime(++failtime);
                RP.addMessages(ID,1);//设定主机状态，记录失败次数
                iscollision=false;
                for (int i = 0; i < JammingSignal; i++)//发生碰撞，发送人为干扰信号
                {   pcPanel.jProgressBar.setString("发送干扰信号");
                    pcPanel.jProgressBar.setValue((i+1)*25);
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(failtime!=10)
                {
                new Thread(new Delaythread(wlan,11,collision,speed)).start();//信道中传输无目标主机的干扰信号
               pcPanel.jProgressBar.setString("等待重传");
                    waiting();//执行退避算法
                     }
                     else {RP.addMessages(ID,2);
                    successtime=11;}
           }



    }
    public synchronized void waiting(){
         r=(int)Math.pow(2,new Random().nextInt(4)+1)-1;
         int tag=0;
         if(r>10) r=10;
         pcPanel.setState(3,r);
        for(int i=0; i<r*collision;i++)
        {  if(wlan.BUS==ID&&tag==0) {pcPanel.setState(2);tag=1;}//退避过程中有可能接收数据
            if(wlan.BUS!=ID&&tag==1){pcPanel.setState(3,r);tag=0;}
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }}
    }
}
