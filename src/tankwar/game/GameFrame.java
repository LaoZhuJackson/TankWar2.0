package tankwar.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static tankwar.util.Constant.*;//静态引入

/**
 * 游戏主窗口类
 * 游戏展示所有的内容都在这
 * implements-->多重继承
 * runnable-->多线程
 */
public class GameFrame extends Frame implements Runnable{
    //游戏状态
    public static int gameState;
    //菜单指向
    private int menuIndex;

    /**
     * 对窗口进行初始化
     */
    public GameFrame() {
        initFrame();//初始化窗口属性
        initEventListener();//添加监听事件
        //启动刷新线程
        new Thread(this).start();
    }

    /**
     * 对游戏进行初始化
     */
    private void initGame() {
        gameState = State_Menu;
    }

    /**
     * 属性进行初始化
     */
    private void initFrame() {
        setTitle(Game_Title);
        setSize(Frame_Width, Frame_Height);
        setLocationRelativeTo(null);//屏幕居中
        setResizable(false);//不允许调整窗口大小
        setVisible(true);//使窗口可见
    }

    /**
     * Frame类继承下来的方法
     * 负责绘制所有需要显示的内容，该方法不能主动调用
     * 通过调用repaint();去回调该方法
     *
     * @param g 系统提供的画笔，系统进行初始化
     */
    public void update(Graphics g) {
        g.setFont(Game_Font);
        switch (gameState) {
            case State_Menu:
                drawMenu(g);
                break;
            case State_Help:
                drawHelp(g);
                break;
            case State_About:
                drawAbout(g);
                break;
            case State_Run:
                drawRun(g);
                break;
            case State_Over:
                drawOver(g);
                break;
        }
    }

    private void drawOver(Graphics g) {

    }

    private void drawRun(Graphics g) {
        //绘制黑色背景
        g.setColor(Color.BLACK);//设置画笔颜色
        g.fillRect(0, 0, Frame_Width, Frame_Height);//实心矩形
    }

    private void drawAbout(Graphics g) {

    }

    private void drawHelp(Graphics g) {

    }

    /**
     * 绘制菜单状态下的内容
     *
     * @param g 画笔对象，系统提供
     */
    private void drawMenu(Graphics g) {
        //绘制黑色背景
        g.setColor(Color.BLACK);//设置画笔颜色
        g.fillRect(0, 0, Frame_Width, Frame_Height);//实心矩形


        g.setColor(Color.white);
        for (int i = 0; i < Menus.length; i++) {
            g.drawString(Menus[i], x, y + Dis * i);
            g.drawImage(Menu_Select, Select_x, Select_y, null);
        }
    }

    /**
     * 初始化窗口的事件监听
     */
    private void initEventListener() {
        //注册监听事件
        addWindowListener(new WindowAdapter() {
            //重写JDK提供的closing方法
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //添加按键监听事件
        addKeyListener(new KeyAdapter() {
            //按下按键时被回调的方法
            @Override
            public void keyPressed(KeyEvent e) {
                //获得被按下键的键值
                int keyCode = e.getKeyCode();
                //不同游戏状态下对应的处理方法不同
                switch (gameState) {
                    case State_Menu:
                        keyEventMenu(keyCode);
                        break;
                    case State_Help:
                        keyEventHelp(keyCode);
                        break;
                    case State_About:
                        keyEventAbout(keyCode);
                        break;
                    case State_Run:
                        keyEventRun(keyCode);
                        break;
                    case State_Over:
                        keyEventOver(keyCode);
                        break;
                }
            }
            //按键松开时被回调的方法
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void keyEventOver(int keyCode) {

    }

    private void keyEventRun(int keyCode) {

    }

    private void keyEventAbout(int keyCode) {

    }

    private void keyEventHelp(int keyCode) {

    }

    //菜单状态下的按键处理
    private void keyEventMenu(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                Select_y -= Dis;
                //如果在最上面一项
                if (Select_y < y-32) {
                    Select_y=(Menus.length-1)*Dis+168;
                }
                repaint();//进行画面更新
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                Select_y += Dis;
                //如果在最下面一项
                if (Select_y >= (Menus.length*Dis+168)) {
                    Select_y=y-32;
                }
                break;
            case KeyEvent.VK_ENTER:
                //TODO
                newGame();
                break;
        }
    }

    /**
     * 开始新游戏的状态
     */
    private void newGame() {
        gameState=State_Run;
        //创建坦克对象，敌方坦克
    }


    @Override
    public void run() {
        //控制刷新界面
        while (true){
            repaint();
            try {
                Thread.sleep(Repaint_Interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
