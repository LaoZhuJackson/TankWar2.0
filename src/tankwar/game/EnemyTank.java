package tankwar.game;

import tankwar.util.Constant;
import tankwar.util.MyUtil;

import java.awt.*;

public class EnemyTank extends Tank {
    private int speed = enemy_Default_Speed;

    public EnemyTank(String img, int x, int y, String upImg, String leftImg, String rightImg, String downImg) {
        super(img, x, y, upImg, leftImg, rightImg, downImg);
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
        //System.out.println(enemy.state);
        return enemy;
    }

    private void logic() {
        //依旧需要分类处理
        switch (state) {
            case State_Stand:
                break;
            case State_Move:
                move();
                break;
            case State_Die:
                break;
        }
    }

    //坦克移动,实现边界检测
    private void move() {
        switch (dir) {
            case UP:
                if (!moveToBorder(x, y - speed))//先碰撞就检测再移动
                    y -= speed;
                break;
            case DOWN:
                if (!moveToBorder(x, y + speed))//先碰撞就检测再移动
                    y += speed;
                break;
            case LEFT:
                if (!moveToBorder(x - speed, y))//先碰撞就检测再移动
                    x -= speed;
                break;
            case RIGHT:
                if (!moveToBorder(x + speed, y))//先碰撞就检测再移动
                    x += speed;
                break;
        }
    }

    @Override
    public void paintSelf(Graphics g) {
        logic();//调用，实现移动
        g.drawImage(img, x, y, null);
    }
}
