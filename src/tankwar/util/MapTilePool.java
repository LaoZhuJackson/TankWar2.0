package tankwar.util;

import tankwar.game.EnemyTank;
import tankwar.game.Tank;
import tankwar.map.MapTile;

import java.util.ArrayList;
import java.util.List;

public class MapTilePool {
    public static final int Default_Pool_Size=50;
    public static final int Pool_Max_Size=70;

    private static List<MapTile> pool =new ArrayList<>();

    static {
        for (int i = 0; i <Default_Pool_Size; i++) {
            pool.add(new MapTile());
        }
    }

    /**
     * 从池塘中获取敌方坦克对象
     * @return
     */
    public static MapTile get(){
        MapTile tile=null;
        //如果池塘数据不够用，取的速度<放回的速度
        if(pool.size()==0){
            tile = new MapTile();
        }else{
            tile=pool.remove(0);
        }
        return tile;
    }
    //子弹被销毁时，归还到池塘
    public static void theReturn(MapTile tile){
        //到达pool最大值，不再归还
        if(pool.size()==Pool_Max_Size){
            return;
        }
        pool.add(tile);
    }
}
