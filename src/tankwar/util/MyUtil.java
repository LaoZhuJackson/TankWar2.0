package tankwar.util;

import java.awt.*;

public class MyUtil {
    private MyUtil() {
    }

    /**
     * 得到指定区间的随机数
     *
     * @param min 区间最小值，包含
     * @param max 区间最大值，不包含
     * @return 随机数
     */
    public static final int getRandomNumber(double min, double max) {//得是double值，避免接下来运算会被强制转换
        return (int) (Math.random() * (max - min) + min);//Math.random()可以获得一个0.0到1.0的随机double值
    }

    /**
     * 判断点是否在正方形内部
     *
     * @param rectX  正方形中心点的X坐标
     * @param rectY  正方形中心点的Y坐标
     * @param radius 正方形边长的一半
     * @param pointX 点的x坐标
     * @param pointY 点的y坐标
     * @return
     */
    public static final boolean isCollide(int rectX, int rectY, int radius, int pointX, int pointY) {
        //计算正方形X Y轴距离
        int disX = Math.abs(rectX - pointX);//绝对值防止负数出现
        int disY = Math.abs(rectY - pointY);
        if (disX < radius && disY < radius)
            return true;
        return false;
    }

    /**
     * 根据图片资源路径创建加载图片对象
     * @param path 图片资源路径
     * @return
     */
    public static Image createImages(String path){
        return Toolkit.getDefaultToolkit().createImage(path);
    }

}
