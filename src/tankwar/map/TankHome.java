package tankwar.map;

import tankwar.util.Constant;
import tankwar.util.MyUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 主基地
 */
public class TankHome {
    //基地保护壳左上角坐标
    public static final int homeX= (Constant.Frame_Width-3*MapTile.tileW)>>1;
    public static final int homeY= Constant.Frame_Height-2*MapTile.tileW-5;

    private List<MapTile> tiles=new ArrayList<>();

    public TankHome() {
        tiles.add(new MapTile(homeX,homeY));
        tiles.add(new MapTile(homeX,homeY+MapTile.tileW));
        tiles.add(new MapTile(homeX+MapTile.tileW,homeY));
        tiles.add(new MapTile(homeX+MapTile.tileW*2,homeY));
        tiles.add(new MapTile(homeX+MapTile.tileW*2,homeY+MapTile.tileW));
        //基地块
        tiles.add(new MapTile(MyUtil.createImages("images/star.gif"),(Constant.Frame_Width-40)>>1,Constant.Frame_Height-45));
    }

    public void draw(Graphics g){
        for (MapTile tile : tiles) {
            tile.draw(g);
        }
    }

    public List<MapTile> getTiles() {
        return tiles;
    }
}
