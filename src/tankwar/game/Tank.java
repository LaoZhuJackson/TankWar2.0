package tankwar.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 坦克类
 */
public abstract class Tank  {
    //坦克贴图
    public Image img;
    //四个方向的图片
    private final String upImg;
    private final String leftImg;
    private final String rightImg;
    private final String downImg;
    //坦克尺寸
    public static final int length=60;
    //默认速度 每帧4像素
    public static final int Default_Speed=4;
    //坦克状态
    public static final int State_Stand=0;
    public static final int State_Move=1;
    public static final int State_Die=2;
    //坦克的初始生命
    public static final int Default_HP=1000;
    //坦克坐标
    public int x,y;

    private int hp=Default_HP;
    private int atk;
    public int speed=Default_Speed;
    public Direction dir=Direction.UP;
    public int state=State_Stand;

    //TODO 炮弹
    private List bullet=new ArrayList();

    public Tank(String img,int x, int y,String upImg, String leftImg, String rightImg, String downImg) {
        this.img = Toolkit.getDefaultToolkit().getImage(img);//将图片参数从Image类型转换成String类型
        this.x=x;
        this.y=y;
        //给四个方向的图片赋值
        this.upImg = upImg;
        this.leftImg = leftImg;
        this.rightImg = rightImg;
        this.downImg = downImg;
    }

    //坦克移动，改变贴图和方向
    public void leftward() {
        dir = Direction.LEFT;
        setImg(leftImg);
    }

    public void upward() {
        dir = Direction.UP;
        setImg(upImg);

    }

    public void rightward() {
        dir = Direction.RIGHT;
        setImg(rightImg);

    }

    public void downward() {
        dir = Direction.DOWN;
        setImg(downImg);

    }

    //定义setImg函数
    public void setImg(String img) {
        this.img = Toolkit.getDefaultToolkit().getImage(img);
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List getBullet() {
        return bullet;
    }

    public void setBullet(List bullet) {
        this.bullet = bullet;
    }

    /**
     * 绘制坦克
     * @param g
     */
    public abstract void paintSelf(Graphics g);
}
