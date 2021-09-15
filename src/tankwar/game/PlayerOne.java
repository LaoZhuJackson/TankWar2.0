package tankwar.game;

import java.awt.*;

public class PlayerOne extends Tank{
    //boolean left, right, up, down;

    public PlayerOne(String img, int x, int y,String upImg, String leftImg, String rightImg, String downImg) {
        super(img,x,y,upImg,leftImg,rightImg,downImg);
    }



    @Override
    public void paintSelf(Graphics g){
        g.drawImage(img,x,y,null);
    }
}
