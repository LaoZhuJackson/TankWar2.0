package tankwar.map;

import tankwar.util.MyUtil;

import java.awt.*;

/**
 * 地图元素块
 */
public class MapTile {
    private static Image tileImg;
    public static int tileW =60;
    static {
        tileImg= MyUtil.createImages("images/walls.gif");
    }
    //图片左上角坐标
    private int x,y;

    public MapTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MapTile() {
    }

    public void draw(Graphics g){
        g.drawImage(tileImg,x,y,null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
