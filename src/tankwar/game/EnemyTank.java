package tankwar.game;

import tankwar.util.BulletsPool;
import tankwar.util.Constant;
import tankwar.util.MyUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyTank extends Tank {
    private int speed = enemy_Default_Speed;
    //记录AI5秒开始的时间
    private long AI_Time;
    private List<Bullet> Enemy_bulletList = new ArrayList<>();
    private int atk = Default_Atk;
    public int HP = Default_HP;
    private BloodBar bar =new BloodBar();

    public EnemyTank(String img, int x, int y, String upImg, String leftImg, String rightImg, String downImg) {
        super(img, x, y, upImg, leftImg, rightImg, downImg);
        //每创建一个敌方坦克，记录创建时间
        AI_Time = System.currentTimeMillis();
    }

    //用于创建敌方坦克
    public static EnemyTank createEnemy() {
        int x = MyUtil.getRandomNumber(0, 2) == 0 ? length : (Constant.Frame_Width - (2 * length));
        //System.out.println("当前敌方坦克位置随机数为："+x);
        int y = GameFrame.titleBarH + length;
        EnemyTank enemy = new EnemyTank("images/enemy1D.gif", x, y, "images/enemy1U.gif", "images/enemy1L.gif", "images/enemy1R.gif", "images/enemy1D.gif");
        enemy.isEnemy = true;
        enemy.dir = Direction.DOWN;
        enemy.state = State_Move;
        return enemy;
    }

    private void logic() {
        //依旧需要分类处理
        switch (state) {
            case State_Stand:
            case State_Die:
                break;
            case State_Move:
                move();
                break;
        }
    }

    //坦克移动,实现边界检测
    private void move() {
        switch (dir) {
            case UP:
                upward();
                break;
            case DOWN:
                downward();
                break;
            case LEFT:
                leftward();
                break;
            case RIGHT:
                rightward();
                break;
        }
    }

    public void attack() {
        Point p = getHeadPoint();
        System.out.println("当前坦克子弹方向：" + dir);
        //从对象池中获取子弹对象
        Bullet bullet = BulletsPool.get();
        //设置子弹属性
        bullet.setImg("images/enemymissile.gif");
        bullet.setX(p.x);
        bullet.setY(p.y);
        bullet.setDir(dir);
        bullet.setAtk(atk);
        bullet.setVisible(true);
        Enemy_bulletList.add(bullet);//把初始化的新子弹添加到列表里
        //攻击完后进入冷却，线程开始
        //new AttackCD().start();
    }

    private void Enemy_drawBullets(Graphics g) {
        for (Bullet bullet : Enemy_bulletList) {
            bullet.paintSelf(g);
        }
        //遍历所有子弹，将不可见的子弹移除，并还原回对象池
        for (int i = 0; i < Enemy_bulletList.size(); i++) {
            Bullet bullet = Enemy_bulletList.get(i);
            if (!bullet.isVisible()) {
                Bullet remove = Enemy_bulletList.remove(i);//从当前子弹列表移除
                BulletsPool.theReturn(remove);//归还回对象池
            }
        }
        //System.out.println("坦克子弹数量："+PlayerOne_bulletList.size());
    }

    //随机生成的方向
    public Direction getRandomDirection() {
        Random random = new Random();
        int rnum = random.nextInt(4);
        //利用switch，根据随机数确定方向
        switch (rnum) {
            case 0:
                setImg("images/enemy1L.gif");//防止静止的时候更换了方向但不更新贴图，导致子弹反向与贴图方向不符
                return Direction.LEFT;
            case 1:
                setImg("images/enemy1R.gif");
                return Direction.RIGHT;
            case 2:
                setImg("images/enemy1U.gif");
                return Direction.UP;
            case 3:
                setImg("images/enemy1D.gif");
                return Direction.DOWN;
            default:
                return null;
        }
    }

    /**
     * 敌方ai
     */
    private void ai() {
        if (System.currentTimeMillis() - AI_Time > Constant.Enemy_AI_Interval) {
            //间隔五秒，随机一个状态
            setState(MyUtil.getRandomNumber(0, 2) == 0 ? State_Stand : State_Move);//0->stand，1->move
            setDir(getRandomDirection());//随机方向
            System.out.println("坦克方向：" + dir);
            AI_Time = System.currentTimeMillis();//重置AI_Time
        }
        if (Math.random() < Constant.Enemy_Fire_Percent) {//随机生成0~1的随机数实现5%
            attack();
        }
    }

    public List<Bullet> getEnemy_bulletList() {
        return Enemy_bulletList;
    }

    @Override
    public void paintSelf(Graphics g) {
        logic();//调用，实现移动
        ai();
        g.drawImage(img, x, y, null);
        Enemy_drawBullets(g);
        //绘制血条
        bar.draw(g);
    }
}
