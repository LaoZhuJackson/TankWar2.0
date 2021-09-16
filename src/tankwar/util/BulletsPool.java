package tankwar.util;

import tankwar.game.Bullet;

import java.util.ArrayList;
import java.util.List;

/**
 * 子弹对象池类
 */
public class BulletsPool {
    public static final int Default_Pool_Size=200;
    //拿子弹的时候会新建，防止归还过多，需要限制
    public static final int Pool_Max_Size=300;
    //用于保存所有子弹
    private static List<Bullet> pool =new ArrayList<>();
    //在类加载时预创建200个子弹
    static {
        for (int i = 0; i <Default_Pool_Size; i++) {
            pool.add(new Bullet());
        }
    }

    /**
     * 从池塘中获取子弹对象
     * @return
     */
    public static Bullet get(){
        Bullet bullet=null;
        //如果池塘数据不够用，取的速度<放回的速度
        if(pool.size()==0){
            bullet = new Bullet();
        }else{
            bullet=pool.remove(0);//如果有，则remove出第0个子弹
        }
        System.out.println("获取了一个对象，剩余："+pool.size());
        return bullet;
    }
    //子弹被销毁时，归还到池塘
    public static void theReturn(Bullet bullet){
        //到达pool最大值，不再归还
        if(pool.size()==Pool_Max_Size){
            return;
        }
        pool.add(bullet);
        System.out.println("对象池中归还了一个子弹对象，当前数量为："+pool.size());
    }
}
