package tankwar.util;

public class MyUtil {
    private MyUtil(){}

    /**
     * 得到指定区间的随机数
     * @param min 区间最小值，包含
     * @param max 区间最大值，不包含
     * @return  随机数
     */
    public static final int getRandomNumber(int min,int max){
        return  (int)(Math.random()%(max-min)+min);
    }

}
