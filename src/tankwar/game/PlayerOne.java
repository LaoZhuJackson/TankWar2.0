package tankwar.game;

import tankwar.util.BulletsPool;
import tankwar.util.Constant;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerOne extends Tank {
    //初始参数
    private int atk = Default_Atk;
    public int HP = Default_HP;
    private int speed = Default_Speed;
    private String name="PLAYER ONE";
    private BloodBar bar =new BloodBar();

    private List<Bullet> PlayerOne_bulletList = new ArrayList<>();

    public PlayerOne(String img, int x, int y, String upImg, String leftImg, String rightImg, String downImg) {
        super(img, x, y, upImg, leftImg, rightImg, downImg);
    }

    //坦克的逻辑处理
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
                if (!moveToBorder(x, y - speed)&&!isCollideTile(x, y - speed))//先碰撞就检测再移动
                    y -= speed;
                break;
            case DOWN:
                if (!moveToBorder(x, y + speed)&&!isCollideTile(x, y + speed))//先碰撞就检测再移动
                    y += speed;
                break;
            case LEFT:
                if (!moveToBorder(x - speed, y)&&!isCollideTile(x - speed, y))//先碰撞就检测再移动
                    x -= speed;
                break;
            case RIGHT:
                if (!moveToBorder(x + speed, y)&&!isCollideTile(x + speed, y))//先碰撞就检测再移动
                    x += speed;
                break;
        }
    }

    public void attack() {
        Point p = getHeadPoint();
        //从对象池中获取子弹对象
        Bullet bullet = BulletsPool.get();
        //设置子弹属性
        bullet.setImg("images/tankmissile.gif");
        bullet.setX(p.x);
        bullet.setY(p.y);
        bullet.setDir(dir);
        bullet.setAtk(atk);
        bullet.setVisible(true);
        PlayerOne_bulletList.add(bullet);//把初始化的新子弹添加到列表里
        //攻击完后进入冷却，线程开始
        //new AttackCD().start();
    }

    private void PlayerOne_drawBullets(Graphics g) {
        for (Bullet bullet : PlayerOne_bulletList) {
            bullet.paintSelf(g);
        }
        //遍历所有子弹，将不可见的子弹移除，并还原回对象池
        for (int i = 0; i < PlayerOne_bulletList.size(); i++) {
            Bullet bullet = PlayerOne_bulletList.get(i);
            if(!bullet.isVisible()){
                Bullet remove = PlayerOne_bulletList.remove(i);//从当前子弹列表移除
                i--;
                BulletsPool.theReturn(remove);//归还回对象池
            }
        }
    }

    /**
     * 坦克销毁时处理子弹
     */
    public void bulletReturn(){
        for (Bullet bullet : PlayerOne_bulletList) {
            BulletsPool.theReturn(bullet);
        }
        //归还后清空
        PlayerOne_bulletList.clear();
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
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
        return HP;
    }

    public void setHp(int hp) {
        this.HP = hp;
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

    public List<Bullet> getPlayerOne_bulletList() {
        return PlayerOne_bulletList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 绘制坦克名字
     * @param g
     */
    private void drawName(Graphics g){
        g.setColor(Color.white);
        //设置字体大小
        g.setFont(Constant.Game_Small_Font);
        g.drawString(name,x,y-20);
    }

    @Override
    public void paintSelf(Graphics g) {
        logic();//调用，实现移动
        g.drawImage(img, x, y, null);
        PlayerOne_drawBullets(g);
        //绘制玩家名
        drawName(g);
        //绘制血条
        bar.draw(g);
    }
}
