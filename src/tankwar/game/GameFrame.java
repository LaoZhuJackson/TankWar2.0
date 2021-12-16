package tankwar.game;

import tankwar.map.GameMap;
import tankwar.util.MyUtil;

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
public class GameFrame extends Frame implements Runnable {
    //标题栏高度
    public static int titleBarH;
    //游戏状态
    private static int gameState;
    //先不加载，用的时候再加载
    private Image overImg = null;
    //定义一张和屏幕大小一致的图片
    private BufferedImage bufImg = new BufferedImage(Frame_Width, Frame_Height, BufferedImage.TYPE_4BYTE_ABGR);
    //定义坦克对象
    private PlayerOne playerOne;
    private PlayerOne playerTwo;//记得修改数据类型 TODO
    //定义敌方坦克
    private List<Tank> enemies = new ArrayList<>();

    //定义地图相关内容
    private GameMap gameMap;

    /**
     * 对窗口进行初始化
     */
    public GameFrame() {
        initFrame();//初始化窗口属性
        initEventListener();//添加监听事件
        //启动刷新线程
        new Thread(this).start();
    }

    //获取和修改游戏状态
    public static int getGameState() {
        return gameState;
    }

    public static void setGameState(int gameState) {
        GameFrame.gameState = gameState;
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
        titleBarH = getInsets().top;
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
        g.drawImage(bufImg, 0, 0, null);
    }

    /**
     * 绘制游戏结束的方法
     *
     * @param g
     */
    private void drawOver(Graphics g) {
        if (overImg == null) {
            overImg = MyUtil.createImages("images/game_over.png");
        }
        //填充图片
        g.drawImage(overImg, 0, 0, null);

        g.setColor(Color.white);
        //添加按键的提示信息
        for (int i = 0; i < OverStr.length; i++) {
            g.drawString(OverStr[i], x_over, y_over + Dis * i);
            g.drawImage(Menu_Select, Select_x_over, Select_y_over, null);
        }
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

        //绘制地图
        gameMap.draw(g);

        //子弹与坦克的碰撞方法
        bulletCollideTank();

        //调用绘制爆炸效果的方法
        drawExplodes(g);
    }

    private void bulletCollideTank() {
        //我方子弹与敌方坦克碰撞
        for (Tank enemy : enemies) {
            enemy.CollideBullets(playerOne.getPlayerOne_bulletList());
        }
        //敌方坦克子弹与我方坦克碰撞
        for (Tank enemy : enemies) {
            playerOne.CollideBullets(enemy.getEnemy_bulletList());
        }
    }

    //所有坦克的爆炸效果
    private void drawExplodes(Graphics g) {
        //敌方坦克添加
        for (Tank enemy : enemies) {
            enemy.drawExplodes(g);
        }
        //我方坦克
        playerOne.drawExplodes(g);
    }

    //绘制所有敌方坦克，如果死亡则移除
    private void drawEnemies(Graphics g) {
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemyTank = enemies.get(i);
            if (enemyTank.idDie()) {
                enemies.remove(i);
                i--;
                continue;
            }
            enemyTank.paintSelf(g);
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
        }
    }

    //按键松开后的处理方法,设置状态可以解决启动停顿问题
    private void keyReleasedEventOne(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D -> playerOne.setState(PlayerOne.State_Stand);
        }
    }

    //游戏结束的按键处理 TODO
    private void keyPressedEventOver(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                    if (temp_over==1){
                        temp_over=2;
                        Select_y_over+=Dis;
                    }else {
                        temp_over--;
                        Select_y_over-=Dis;
                    }
                    repaint();
                    break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                //如果在最下面一项
                if (temp_over==2) {
                    temp_over = 1;
                    Select_y_over -=Dis;
                }else{
                    Select_y_over += Dis;
                    temp_over++;
                }
                break;
            case KeyEvent.VK_ENTER:
                if (temp_over==1){
                    setGameState(State_Menu);
                    resetGame();

                }else{
                    resetGame();
                    newGame_playerOne();
                }
                break;
        }
    }
    //重置游戏状态
    private void resetGame(){
        temp=1;
        temp_over=1;
        //归还子弹
        playerOne.bulletReturn();
//        playerTwo.bulletReturn();
        //销毁自己坦克
        playerOne=null;
        playerTwo=null;
        //清空敌人相关资源
        for (Tank enemy : enemies) {
            enemy.bulletReturn();
        }
        enemies.clear();
        //清空地图资源
        gameMap=null;

        //重置选择图标
        Select_y_over=y_over-32;
        Select_y=y-32;
    }

    private void keyPressedEventOne(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W -> {
                playerOne.upward();
                playerOne.setState(Tank.State_Move);
            }
            case KeyEvent.VK_S -> {
                playerOne.downward();
                playerOne.setState(Tank.State_Move);
            }
            case KeyEvent.VK_A -> {
                playerOne.leftward();
                playerOne.setState(Tank.State_Move);
            }
            case KeyEvent.VK_D -> {
                playerOne.rightward();
                playerOne.setState(Tank.State_Move);
            }
            case KeyEvent.VK_J -> {
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
                //如果在最上面一项
                if (temp==1) {
                    temp = Menus.length;
                    Select_y = (Menus.length - 1) * Dis + 118;
                }else{
                    temp--;
                    Select_y -= Dis;
                }
                repaint();//进行画面更新
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                //如果在最下面一项
                if (temp==6) {
                    temp = 1;
                    Select_y = y - 32;
                }else {
                    Select_y += Dis;
                    temp++;
                }
                break;
            case KeyEvent.VK_ENTER:
                if (temp == State_One) {
                    newGame_playerOne();
                }else if(temp==State_Exit){
                    System.exit(0);
                }
                //TODO
                else if (temp == State_Two) {
                    newGame_playerTwo();
                }
                break;
        }
    }

    /**
     * 开始新游戏的状态
     */
    private void newGame_playerOne() {
        //初始化
        gameState = State_One;
        //创建玩家
        playerOne = new PlayerOne("images/p1tankU.gif", 125, 510, "images/p1tankU.gif", "images/p1tankL.gif", "images/p1tankR.gif", "images/p1tankD.gif");

        gameMap=new GameMap();

        //使用单独线程用于控制生成敌方坦克
        new Thread() {
            @Override
            public void run() {
                while (gameState==State_One) {
                    if (enemies.size() < Enemy_Max) {
                        Tank enemy = EnemyTank.createEnemy();
                        enemies.add(enemy);
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
        gameState = State_Two;
        //创建坦克对象
        //playerTwo=new PlayerTwo("images/p2tankU.gif",615,510,"images/p2tankU.gif", "images/p2tankL.gif", "images/p2tankR.gif", "images/p2tankD.gif");
    }

    @Override
    public void run() {
        //控制刷新界面
        while (true) {
            repaint();
            try {
                Thread.sleep(Repaint_Interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
