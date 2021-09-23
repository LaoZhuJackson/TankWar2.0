package tankwar.util;

public class MyUtil {
    private MyUtil(){}

    /**
     * 得到指定区间的随机数
     * @param min 区间最小值，包含
     * @param max 区间最大值，不包含
     * @return  随机数
     */
    public static final int getRandomNumber(double min,double max){//得是double值，避免接下来运算会被强制转换
        return  (int)(Math.random()*(max-min)+min);//Math.random()可以获得一个0.0到1.0的随机double值
    }

}
