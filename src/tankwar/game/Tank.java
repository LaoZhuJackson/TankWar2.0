package tankwar.game;

import tankwar.util.Constant;
import tankwar.util.ExplorePool;
import tankwar.util.MyUtil;

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
    public static final int speed=Default_Speed;
    public static final int enemy_Default_Speed=4;
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
    public boolean isEnemy=false;
    //使用容器保存当前坦克上所有爆炸效果
    private List<Explode>explodes=new ArrayList<>();

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
        if (!moveToBorder(x - speed, y))//先碰撞就检测再移动
            x -= speed;
    }

    public void upward() {
        dir = Direction.UP;
        setImg(upImg);
        if (!moveToBorder(x, y + speed))//先碰撞就检测再移动
            y -= speed;
    }

    public void rightward() {
        dir = Direction.RIGHT;
        setImg(rightImg);
        if (!moveToBorder(x - speed, y))//先碰撞就检测再移动
            x += speed;
    }

    public void downward() {
        dir = Direction.DOWN;
        setImg(downImg);
        if (!moveToBorder(x, y + speed))//先碰撞就检测再移动
            y += speed;
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

    public boolean moveToBorder(int x, int y) {
        //左右边界
        if (x < 0 || x + length > Constant.Frame_Width) {
            return true;
        }
        //上下边界
        else if (y < GameFrame.titleBarH || y + length > Constant.Frame_Height) {
            return true;
        }
        return false;
    }

    //坦克和子弹碰撞
    public void CollideBullets(List<Bullet> bullets){
        //遍历所有子弹，和当前坦克进行碰撞检测
        for (Bullet bullet : bullets) {
            int bulletX = bullet.getX();
            int bulletY = bullet.getY();
            //子弹与坦克碰撞
            if (MyUtil.isCollide(this.x +length/2,y+length/2,length/2,bulletX,bulletY)){
                //子弹消失
                bullet.setVisible(false);
                //坦克操作
                //爆炸效果,以当前被击中的坦克坐标为爆炸坐标
                Explode explode = ExplorePool.get();
                //赋值
                explode.setX(x);
                explode.setY(y);
                explode.setVisible(true);
                explode.setIndex(0);

                explodes.add(explode);
            }
        }
    }

    /**
     * 绘制当前坦克的所有爆炸效果
     * @param g
     */
    public void drawExplodes(Graphics g){
        for (Explode explode : explodes) {
            explode.paintSelf(g);
        }
        //将不可见的爆炸效果删除，归还回爆炸池
        for (int i = 0; i < explodes.size(); i++) {
            Explode explode = explodes.get(i);
            if (!explode.isVisible()){
                Explode remove = explodes.remove(i);
                //还原
                ExplorePool.theReturn(remove);
                //remove后回退一格,因为后一个元素会顶上来
                i--;
            }
        }
    }

    //定义setImg函数
    public void setImg(String img) {
        this.img = Toolkit.getDefaultToolkit().getImage(img);
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }



    /**
     * 绘制坦克
     * @param g
     */
    public abstract void paintSelf(Graphics g);
}
