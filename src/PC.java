import java.util.Random;

/**
 * Created by tiyan on 2017/5/4.
 */
public class PC implements Runnable {
   private int ID,data=(int)(Math.random()*200+50),successtime=0,failtime=0,r,monitor=9;
   int collision=50,speed,JammingSignal=4;
    private PCPanel pcPanel;
   private boolean iscollision=false;
   WLAN wlan;
   RightPanel RP;

   public PC(int ID,WLAN wlan,PCPanel pcPanel,RightPanel RP,int speed){
        this.ID=ID;
        this.wlan=wlan;
        this.pcPanel=pcPanel;
        this.RP=RP;
        this.speed=speed;
   }
    @Override
    public void run() {
        while (successtime < 10) {
                if (wlan.output== ID)
                    pcPanel.setState(2);
                else pcPanel.setState(4);
                //进行监听，若信道持续一定时间空闲，则进行传输
                if (wlan.bus.isEmpty()) {
                    for( monitor=0;monitor<9;monitor++){
                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    if(!wlan.bus.isEmpty())
                        break; //监听过程中信道不空闲，则再进行退避算法
                    }
                    if(monitor==9)
                        output();
                    else {waiting(); wlan.setState(0);}
                } else {waiting(); wlan.setState(0);}
            }
            pcPanel.setState(5);//传输完成，结束传输
        int tag=0;
        while(true) {
            if(wlan.output==ID&&tag==0) {pcPanel.setState(2);tag=1;}//传输完成后有可能接收数据
            if(wlan.output!=ID&&tag==1){pcPanel.setState(5);tag=0;}
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
        {   wlan.setState(1);
            new Thread(new BUS(IDto,collision,speed,wlan)).start();
            if(i%5==0)
                pcPanel.jProgressBar.setValue(i*100/data);
            if(!(wlan.output==IDto||wlan.output==0))//在信道出口检测到出现其他主机发出的信号则出现碰撞
            {    System.out.println("发生冲突,id="+wlan.output);
                iscollision=true;
                pcPanel.setState(3);
                pcPanel.failtime(++failtime);
                RP.addMessages(ID,1);//设定主机状态，记录失败次数
                break;
            } //若有碰撞则停止传输
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!iscollision){
        for (int i = collision; i < data; i++) //争用期内无碰撞，将数据发送完毕
        {   new Thread(new BUS(IDto,collision,speed,wlan)).start();
            if(i%5==0)pcPanel.jProgressBar.setValue(i*100/data);
            wlan.setState(1);
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
            for(int i=0; i<9;i++) //帧间最小间隔
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}
        else
            {   iscollision=false;
                pcPanel.jProgressBar.setString("发送干扰信号");
                   for (int i = 0; i < JammingSignal; i++)//发生碰撞，发送人为干扰信号
                {   new Thread(new BUS(-1,collision,speed,wlan)).start();
                    pcPanel.jProgressBar.setValue((i+1)*25);
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(failtime!=10)
                {
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
        {  if(wlan.output==ID&&tag==0) {pcPanel.setState(2);tag=1;}//退避过程中有可能接收数据
            if(wlan.output!=ID&&tag==1){pcPanel.setState(3,r);tag=0;}
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }}
    }
}
