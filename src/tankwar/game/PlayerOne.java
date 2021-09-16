package tankwar.game;

import tankwar.util.Constant;

import java.awt.*;

public class PlayerOne extends Tank{
    //boolean left, right, up, down;

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

    @Override
    public void paintSelf(Graphics g){
        logic();//调用，实现移动
        g.drawImage(img,x,y,null);
    }
}
