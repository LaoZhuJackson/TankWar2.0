package tankwar.game;

import tankwar.util.MyUtil;

import java.awt.*;

/**
 * 控制爆炸效果的类
 */
public class Explode {
    //总时长为16帧
    public static final int Explode_Frame_Count = 16;
    //导入资源
    private static Image[] images;
    //爆炸效果图的宽高
    private static int exploreWidth;
    private static int exploreHeight;

    static {
        images = new Image[Explode_Frame_Count];
        //利用数组存放爆炸动画帧
        for (int i = 0; i < images.length; i++) {
            int j=i/2+1;
            images[i] = MyUtil.createImages("images/blast" + j + ".gif");
        }
    }

    //爆炸效果属性
    private int x, y;
    //当前播放的帧的下标[0-7]
    private int index;
    //可见属性
    private boolean visible=true;

    //为构造池添加一个默认无参的构造方法


    public Explode() {
        index=0;
    }

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        index=0;
    }

    public static Image[] getImages() {
        return images;
    }

    public static void setImages(Image[] images) {
        Explode.images = images;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void paintSelf(Graphics g) {
        //此处调整爆炸效果出现的位置
        exploreWidth = 136/3;
        exploreHeight = 107/4;
        if (!visible)
            return;
        for (int i = 0; i < Explode_Frame_Count; i++) {
            g.drawImage(images[i], x-exploreWidth, y-exploreHeight, null);
            index++;
        }
        //播放完最后一帧设置为不可见
        if (index >= Explode_Frame_Count) {
            visible = false;
        }
    }

    @Override
    public String toString() {
        return "Explode{" +
                "x=" + x +
                ", y=" + y +
                ", index=" + index +
                ", visible=" + visible +
                '}';
    }
}
