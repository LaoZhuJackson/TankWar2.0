package tankwar.game;

import java.awt.*;

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
    //坦克初始攻击
    public static int Default_Atk=100;
    //坦克坐标
    public int x,y;
    //坦克初始方向
    public Direction dir=Direction.UP;
    //坦克初始状态
    public int state=State_Stand;

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
    //获取坦克头部坐标
    public Point getHeadPoint() {
        switch (dir) {
            case LEFT:
                return new Point(x - 13, y + length / 2 - 8);//横坐标不变，纵坐标加半个坦克长度
            case RIGHT:
                return new Point(x + length - 4, y + length / 2 - 8);
            case UP:
                return new Point(x + length / 2 - 8, y - 12);
            case DOWN:
                return new Point(x + length / 2 - 8, y + length - 1);
            default:
                return null;
        }
    }

    //定义setImg函数
    public void setImg(String img) {
        this.img = Toolkit.getDefaultToolkit().getImage(img);
    }
    /**
     * 绘制坦克
     * @param g
     */
    public abstract void paintSelf(Graphics g);
}
