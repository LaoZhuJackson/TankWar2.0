package tankwar.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static tankwar.util.Constant.*;//静态引入

/**
 * 游戏主窗口类
 * 游戏展示所有的内容都在这
 * implements-->多重继承
 * runnable-->多线程
 */
public class GameFrame extends Frame implements Runnable{
    //定义一张和屏幕大小一致的图片
    private BufferedImage bufImg=new BufferedImage(Frame_Width,Frame_Height,BufferedImage.TYPE_4BYTE_ABGR);
    //游戏状态
    public static int gameState;
    //菜单指向
    private int menuIndex;
    //标题栏高度
    public static int titleBarH;

    //定义坦克对象
    private PlayerOne playerOne;
    private PlayerOne playerTwo;
    //定义敌方坦克
    private  List<Tank> enemies=new ArrayList<>();

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

        //求标题栏高度
        titleBarH=getInsets().top;
    }

    /**
     * Frame类继承下来的方法
     * 负责绘制所有需要显示的内容，该方法不能主动调用
     * 通过调用repaint();去回调该方法
     *
     * @param g 系统提供的画笔，系统进行初始化
     */
    public void update(Graphics g) {
        //得到图片的画笔
        Graphics gImg = bufImg.getGraphics();
        //使用图片画笔将所有内容绘制到图片上
        gImg.setFont(Game_Font);
        switch (gameState) {
            case State_Menu:
                drawMenu(gImg);
                break;
            case State_Help:
                drawHelp(gImg);
                break;
            case State_About:
                drawAbout(gImg);
                break;
            case State_One:
                drawOne(gImg);
                break;
            case State_Two:
                drawTwo(gImg);
                break;
            case State_Over:
                drawOver(gImg);
                break;
        }
        //使用系统画笔，将图片绘制到Frame上
        g.drawImage(bufImg,0,0,null);
    }

    private void drawOver(Graphics g) {

    }
    /*
    游戏运行状态时的内容绘制
     */
    private void drawOne(Graphics g) {
        //绘制黑色背景
        g.setColor(Color.BLACK);//设置画笔颜色
        g.fillRect(0, 0, Frame_Width, Frame_Height);//实心矩形

        drawEnemies(g);
        playerOne.paintSelf(g);//越后绘制，越在上层
    }

    private void drawEnemies(Graphics g){
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            enemy.paintSelf(g);
        }
    }

    private void drawTwo(Graphics g) {
        //绘制黑色背景
        g.setColor(Color.BLACK);//设置画笔颜色
        g.fillRect(0, 0, Frame_Width, Frame_Height);//实心矩形
        playerOne.paintSelf(g);
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
                        keyPressedEventMenu(keyCode);
                        break;
                    case State_Help:
                        keyPressedEventHelp(keyCode);
                        break;
                    case State_About:
                        keyPressedEventAbout(keyCode);
                        break;
                    case State_One:
                        keyPressedEventOne(keyCode);
                        break;
                    case State_Two:
                        keyPressedEventTwo(keyCode);
                        break;
                    case State_Over:
                        keyPressedEventOver(keyCode);
                        break;
                }
            }
            //按键松开时被回调的方法
            @Override
            public void keyReleased(KeyEvent e) {
                //获得被按下键的键值
                int keyCode = e.getKeyCode();
                //不同游戏状态下对应的处理方法不同
                switch (gameState) {
                    case State_One:
                        keyReleasedEventOne(keyCode);
                        break;
                    case State_Two:
                        keyReleasedEventTwo(keyCode);
                        break;
                }
            }
        });
    }

    private void keyReleasedEventTwo(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D -> playerOne.setState(PlayerOne.State_Stand);
            //case KeyEvent.VK_UP , KeyEvent.VK_DOWN , KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> playerTwo.setState(PlayerTwo.State_Stand);
        }
    }
    //按键松开后的处理方法,设置状态可以解决启动停顿问题
    private void keyReleasedEventOne(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D -> playerOne.setState(PlayerOne.State_Stand);
        }
    }

    private void keyPressedEventOver(int keyCode) {

    }

    private void keyPressedEventOne(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_W ->{
                playerOne.upward();
                playerOne.setState(Tank.State_Move);
            }
            case KeyEvent.VK_S ->{
                playerOne.downward();
                playerOne.setState(Tank.State_Move);
            }
            case KeyEvent.VK_A ->{
                playerOne.leftward();
                playerOne.setState(Tank.State_Move);
            }
            case KeyEvent.VK_D ->{
                playerOne.rightward();
                playerOne.setState(Tank.State_Move);
            }
            case KeyEvent.VK_J ->{
                playerOne.attack();
            }
        }
    }

    private void keyPressedEventTwo(int keyCode) {

    }

    private void keyPressedEventAbout(int keyCode) {

    }

    private void keyPressedEventHelp(int keyCode) {

    }

    //菜单状态下的按键处理
    private void keyPressedEventMenu(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                temp--;
                Select_y -= Dis;
                //如果在最上面一项
                if (Select_y < y-32) {
                    temp=Menus.length;
                    Select_y=(Menus.length-1)*Dis+118;
                }
                repaint();//进行画面更新
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                Select_y += Dis;
                temp++;
                //如果在最下面一项
                if (Select_y >= (Menus.length*Dis+118)) {
                    temp=1;
                    Select_y=y-32;
                }
                break;
            case KeyEvent.VK_ENTER:
                if(temp==State_One){
                    newGame_playerOne();
                }
                //TODO
                else if(temp==State_Two){
                    newGame_playerTwo();
                }
                break;
        }
    }

    /**
     * 开始新游戏的状态
     */
    private void newGame_playerOne() {
        gameState=State_One;
        //创建玩家
        playerOne=new PlayerOne("images/p1tankU.gif",125,510,"images/p1tankU.gif", "images/p1tankL.gif", "images/p1tankR.gif", "images/p1tankD.gif");
        //使用单独线程用于控制生成敌方坦克
        new Thread(){
            @Override
            public void run() {
                while(true){
                    if(enemies.size()<Enemy_Max){
                        EnemyTank enemy = EnemyTank.createEnemy();
                        enemies.add(enemy);
                        //System.out.println("当前坦克数量："+enemies.size());
                    }
                    try {
                        Thread.sleep(Enemy_Born);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void newGame_playerTwo() {
        gameState=State_Two;
        //创建坦克对象
        //playerTwo=new PlayerTwo("images/p2tankU.gif",615,510,"images/p2tankU.gif", "images/p2tankL.gif", "images/p2tankR.gif", "images/p2tankD.gif");
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
