package tankwar.util;

import tankwar.game.Bullet;
import tankwar.game.Explode;

import java.util.ArrayList;
import java.util.List;

/**
 * 爆炸效果对象池
 */
public class ExplorePool {
    public static final int Default_Pool_Size=10;
    //拿子弹的时候会新建，防止归还过多，需要限制
    public static final int Pool_Max_Size=20;
    //用于保存所有爆炸效果
    private static List<Explode> pool =new ArrayList<>();
    //在类加载时预创建200个子弹
    static {
        for (int i = 0; i <Default_Pool_Size; i++) {
            pool.add(new Explode());
        }
    }

    /**
     * 从池塘中获取爆炸效果对象
     * @return
     */
    public static Explode get(){
        Explode explode=null;
        //如果池塘数据不够用，取的速度<放回的速度
        if(pool.size()==0){
            explode = new Explode();
        }else{
            explode=pool.remove(0);//如果有，则remove出第0个子弹
        }
        return explode;
    }
    //归还到对象池
    public static void theReturn(Explode explode){
        //到达pool最大值，不再归还
        if(pool.size()==Pool_Max_Size){
            return;
        }
        pool.add(explode);
    }
}
