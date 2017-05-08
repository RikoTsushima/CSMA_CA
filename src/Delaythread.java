/**
 * Created by tiyan on 2017/5/6.
 */
public class Delaythread implements  Runnable //主机发送数据完毕后在信道中的传播时延
{
   int ID,collision,speed;
   WLAN wlan;
    public Delaythread(WLAN wlan,int ID,int collision,int speed){
        this.ID=ID;
        this.wlan=wlan;
        this.collision=collision;
        this.speed=speed;
    }
    @Override
    public void run() {
        for (int i = 0; i < collision/2; i++)//余下数据继续传输
        {     wlan.BUS=ID;
        wlan.setState(1);
            try {
                Thread.sleep(speed*2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        wlan.BUS=0;//传输真正完成后置信道为空闲状态
        wlan.setState(0);
    }
}
