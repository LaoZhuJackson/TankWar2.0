package tankwar.util;

import javax.swing.*;
import java.awt.*;

/**
 *游戏常量类，方便后期管理
 */
public class Constant {
    /***********游戏窗口相关**********/
    public static final String Game_Title="坦克大战";
    public static final int Frame_Width=800;
    public static final int Frame_Height=600;

    /***********游戏菜单相关**********/
    public static final int State_Menu=0;
    public static final int State_One=1;
    public static final int State_Two=2;
    public static final int State_Continue=3;
    public static final int State_Help=4;
    public static final int State_About=5;
    public static final int State_Over=6;

    public static final int Str_Width=85;
    public static final int x=350;//字符横坐标
    public static  int Select_x=x-80;
    public static final int y=150;//字符纵坐标
    public static  int Select_y=y-32;
    public static final int Dis=80;//间距

    public static final String[] Menus={
            "单人游戏",
            "双人游戏",
            "继续游戏",
            "游戏帮助",
            "关于游戏",
            "退出游戏"
    };
    //字体
    public static final Font Game_Font=new Font("宋体",Font.BOLD,24);

    public static final int Repaint_Interval=30;
    //图片
    public static final Image Menu_Select=Toolkit.getDefaultToolkit().getImage("images/selectTank.gif");
    //暂存
    public static int temp=1;
    //最大坦克数量
    public static final int Enemy_Max=10;
    //敌人生成速度
    public static final int Enemy_Born=5000;
}
