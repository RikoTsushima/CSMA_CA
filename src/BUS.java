import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by tiyan on 2017/5/8.
 */
public class BUS implements Runnable{
    ConcurrentLinkedQueue<Integer> bus;
    private int IDto;
    int collision,speed;
    WLAN wlan;
    public  BUS(int IDto,int collision,int speed,WLAN wlan){
      this.bus=wlan.bus;
      this.IDto=IDto;
      this.collision=collision;
      this.speed=speed;
      this.wlan=wlan;
    }


    @Override
    public  void run() {
        bus.offer(IDto);
        for(int i=0;i<collision/2;i++)
        {try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }}
        synchronized(bus) {
            if(!bus.isEmpty()) {
               wlan.output=bus.poll();
               System.out.println("输出"+wlan.output);
               if(bus.isEmpty())
               {wlan.output=0;
                wlan.setState(0);
                System.out.println("信道空");}
            }
        }
    }
}
