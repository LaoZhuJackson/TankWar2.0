package tankwar.util;

import tankwar.game.Bullet;
import tankwar.game.EnemyTank;
import tankwar.game.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * 敌方坦克对象池
 */
public class EnemyTanksPool {
    public static final int Default_Pool_Size=20;
    public static final int Pool_Max_Size=20;

    private static List<Tank> pool =new ArrayList<>();

    static {
        for (int i = 0; i <Default_Pool_Size; i++) {
            pool.add(new EnemyTank());
        }
    }

    /**
     * 从池塘中获取敌方坦克对象
     * @return
     */
    public static Tank get(){
        Tank tank=null;
        //如果池塘数据不够用，取的速度<放回的速度
        if(pool.size()==0){
            tank = new EnemyTank();
        }else{
            tank=pool.remove(0);
        }
        return tank;
    }
    //子弹被销毁时，归还到池塘
    public static void theReturn(Tank tank){
        //到达pool最大值，不再归还
        if(pool.size()==Pool_Max_Size){
            return;
        }
        pool.add(tank);
    }
}