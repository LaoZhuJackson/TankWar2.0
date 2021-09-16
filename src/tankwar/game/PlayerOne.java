package tankwar.game;

import tankwar.util.Constant;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerOne extends Tank{
    //初始参数
    private int atk=Default_Atk;
    private int HP=Default_HP;
    private int speed=Default_Speed;

    private List<Bullet> PlayerOne_bulletList=new ArrayList();

    public PlayerOne(String img, int x, int y,String upImg, String leftImg, String rightImg, String downImg) {
        super(img,x,y,upImg,leftImg,rightImg,downImg);
    }

    //坦克的逻辑处理
    private  void  logic(){
        //依旧需要分类处理
        switch (state){
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
    private void move(){
        switch (dir){
            case UP :
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

    private boolean moveToBorder(int x, int y) {
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
    //TODO
    private boolean hitWall(int x, int i) {
        return false;
    }

    public void attack() {
        Point p = getHeadPoint();
        Bullet bullet = new Bullet("images/tankmissile.gif", p.x, p.y, dir,atk);
        PlayerOne_bulletList.add(bullet);//把初始化的新子弹添加到列表里
        //攻击完后进入冷却，线程开始
        //new AttackCD().start();
    }

    private void PlayerOne_drawBullets(Graphics g){
        for (Bullet bullet : PlayerOne_bulletList) {
            bullet.paintSelf(g);
        }
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

    @Override
    public void paintSelf(Graphics g){
        logic();//调用，实现移动
        g.drawImage(img,x,y,null);
        PlayerOne_drawBullets(g);
    }
}
