package tankwar.game;

import tankwar.util.Constant;

import java.awt.*;

/**
 * 子弹类
 */
public class Bullet {
    //子弹默认速度
    public static final int Default_Speed = 8;
    //子弹半径
    public static final int Radius = 5;
    //子弹状态
    private boolean visible = true;

    private int x, y;
    private int speed = Default_Speed;
    private Direction dir;
    private int atk;
    private Image img = null;

    public Bullet(String img, int x, int y, Direction dir, int atk) {
        this.img = Toolkit.getDefaultToolkit().getImage(img);
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.atk = atk;
    }

    //给对象池使用的构造函数,所有属性均为默认
    public Bullet() {
    }


    /**
     * 子弹逻辑
     */
    private void logic() {
        move();
    }

    //子弹移动
    private void move() {
        switch (dir) {
            case LEFT:
                x -= speed;
                if (x < 0) visible = false;
                break;
            case RIGHT:
                x += speed;
                if (x > Constant.Frame_Width) visible = false;
                break;
            case UP:
                y -= speed;
                if (y < 0) visible = false;
                break;
            case DOWN:
                y += speed;
                if (y > Constant.Frame_Height) visible = false;
                break;
        }
    }

    /**
     * 子弹自身绘制的方法
     *
     * @param g
     */
    public void paintSelf(Graphics g) {
        if (!visible) return;//如果不可见则不再运行该方法

        logic();
        g.drawImage(img, x, y, null);
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public Image getImg() {
        return img;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImg(String img) {
        this.img = Toolkit.getDefaultToolkit().getImage(img);
    }
}
