package tankwar.map;

import tankwar.game.GameFrame;
import tankwar.game.Tank;
import tankwar.util.Constant;
import tankwar.util.MapTilePool;
import tankwar.util.MyUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏地图类
 */
public class GameMap {
    //属性
    private static final int Map_x=Tank.length+5;
    private static final int Map_y=Tank.length+5+ GameFrame.titleBarH;
    //地图宽高
    private static final int width= Constant.Frame_Width-2*Map_x;
    private static final int height= Constant.Frame_Height-3*Map_y+2*GameFrame.titleBarH;


    //地图元素块容器
    private List<MapTile> tiles=new ArrayList<>();

    public GameMap(){
        initMap();
    }

    /**
     * 初始化地图元素块
     */
    private void initMap(){
        final int Count=20;
        for (int i = 0; i < Count; i++) {
            MapTile tile = MapTilePool.get();
            //随机生成 TODO 设计地图
            int x= MyUtil.getRandomNumber(Map_x,Map_x+width-MapTile.tileW);
            int y= MyUtil.getRandomNumber(Map_y,Map_y+height-MapTile.tileW);
            System.out.println(MapTile.tileW);
            tile.setX(x);
            tile.setY(y);
            tiles.add(tile);
        }
    }

    public void draw(Graphics g){
        for (MapTile tile : tiles) {
            tile.draw(g);
        }
    }
}
