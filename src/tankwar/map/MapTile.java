package tankwar.map;

import tankwar.game.Bullet;
import tankwar.util.BulletsPool;
import tankwar.util.MyUtil;

import java.awt.*;
import java.util.List;

/**
 * 地图元素块
 */
public class MapTile {
    private static Image tileImg;
    public static int tileW =60;
    public static int radius =tileW>>1;
    static {
        tileImg= MyUtil.createImages("images/walls.gif");
    }
    //图片左上角坐标
    private int x,y;
    private boolean visible=true;

    public MapTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MapTile() {
    }

    public void draw(Graphics g){
        if (!visible)
            return;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, tileW, tileW);
    }

    /**
     * 判断地图块是否和子弹碰撞
     * @param bullets
     * @return
     */
    public boolean isCollideBullet(List<Bullet> bullets){
        if (!visible)
            return false;
        for (Bullet bullet : bullets) {
            int bulletX = bullet.getX();
            int bulletY = bullet.getY();

            boolean collide = MyUtil.isCollide(x + radius, y + radius, radius+6, bulletX, bulletY);
            if (collide){
                //子弹销毁
                bullet.setVisible(false);
                BulletsPool.theReturn(bullet);
                return true;
            }
        }
        return false;
    }
}
