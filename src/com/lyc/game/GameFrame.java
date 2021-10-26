package com.lyc.game;

import com.lyc.util.Constant;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static com.lyc.util.Constant.*;
/**
 * 游戏的主窗口类。
 * 所有游戏展示的内容都要在该类中实现
 */
public class GameFrame extends Frame implements Runnable{
    //1.定义一张和屏幕大小一样的图片
    private BufferedImage bufImg=new BufferedImage(FRAME_WIDTH,FRAME_HEIGHT,BufferedImage.TYPE_4BYTE_ABGR);
    //游戏状态
    public static int gameState;
    //菜单指向
    private int menuIndex;
    //标题栏 底部栏 两侧栏的高度或宽度
    public static int titleBarH;
    public static int bottomBarH;
    public static int leftBarH;
    public static int rightBarH;

    //定义坦克对象
    private Tank myTank;
    //敌人的坦克容器
    private List<Tank> enemies=new ArrayList<>();

    /**
     * 对窗口进行初始化
     */
    public GameFrame() {
        initFrame();
        initEventListener();
        initGame();
        //用于刷新窗口的线程
        new Thread(this).start();
    }

    /**
     * 对游戏进行初始化
     */
    private void initGame(){
        gameState=STATE_MENU;

    }

    /**
     * 属性进行初始化
     */
    private void initFrame() {
        //设置标题
        setTitle(GAME_TITLE);
        //设置窗口大小
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        //设置窗口的左上角的坐标
        setLocation(FRAME_X,FRAME_Y);
        //设置窗口大小不可改变
        setResizable(false);
        //设置窗口可见
        setVisible(true);
        //求标题栏的高度
        titleBarH=getInsets().top;
        bottomBarH=getInsets().bottom;
        leftBarH=getInsets().left;
        rightBarH=getInsets().right;
    }


    /**
     * 是Frame类的方法，继承了Frame类
     * 该方法负责了所有绘制的内容
     * 所有需要在屏幕中显示的内容
     * 都需要在该方法中调用。该方法不能主动调用。
     * 必须调用repaint()去回调该方法
     * @param g1
     * update为刷新，导致页面一直出不来，故更改为paint
     */
    public void update(Graphics g1){
        //2.得到图片的画笔
        Graphics g=bufImg.getGraphics();
        //3.使用图片画笔将所有的内容绘制到图片上
        g.setFont(GAME_FONT);
        switch (gameState) {
            case STATE_MENU -> drawMenu(g);
            case STATE_HELP -> drawHelp(g);
            case STATE_ABOUT -> drawAbout(g);
            case STATE_RUN -> drawRun(g);
            case STATE_OVER -> drawOver(g);
        }

        //4.使用系统画笔，将图片绘制到frame上来
        g1.drawImage(bufImg,0,0,null);
    }

    private void drawOver(Graphics g) {

    }

    private void drawRun(Graphics g) {
        //绘制黑色的背景
        g.setColor(Color.BLACK);
        g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);

        drawEnemies(g);
        myTank.draw(g);
    }

    //绘制所有的敌人坦克
    private void drawEnemies(Graphics g){
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy=enemies.get(i);
            enemy.draw(g);
        }
    }

    private void drawAbout(Graphics g) {

    }

    private void drawHelp(Graphics g) {

    }

    /**
     * 绘制菜单状态下的内容
     * @param g  系统提供的画笔对象
     */
    private void drawMenu(Graphics g){
        //绘制黑色的背景
        g.setColor(Color.BLACK);
        g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);

        //绘制白色的菜单
        final int STR_WIDTH=76;
        int x=(FRAME_WIDTH-STR_WIDTH)>>1;
        int y=(FRAME_HEIGHT)/3;
        final int DIS=50;//行间距


        for (int i = 0; i < MENUS.length; i++) {
            if(i==menuIndex){ //选中的菜单项的颜色，设置为红色
                g.setColor(Color.red);
            }
            else g.setColor(Color.WHITE);
            g.drawString(MENUS[i],x,y+DIS*i);
        }
    }


    /**
     * 初始化事件的监听
     */
    private void initEventListener(){
        //注册监听事件
        addWindowListener(new WindowAdapter() {
            //点击关闭按钮的时候，这个方法会被自动调用
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //添加按键监听事件
        addKeyListener(new KeyAdapter() {
            //按键被按下的时候被回调的方法
            @Override
            public void keyPressed(KeyEvent e) {
                //获得被按下的键的键值
                int keyCode=e.getKeyCode();
                //不同的游戏状态，给出不同的处理方法
                switch (gameState) {
                    case STATE_MENU -> keyPressedEventMenu(keyCode);
                    case STATE_HELP -> keyPressedEventHelp(keyCode);
                    case STATE_ABOUT -> keyPressedEventAbout(keyCode);
                    case STATE_RUN -> keyPressedEventRun(keyCode);
                    case STATE_OVER -> keyPressedEventOver(keyCode);
                }
            }

            //按键松开的时候回调的内容
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode=e.getKeyCode();
                //不同的游戏状态，给出不同的处理方法
                if(gameState==STATE_RUN){
                    keyReleasedEventRun(keyCode);
//                    myTank.setState(Tank.STATE_STAND);
                }
            }

        });
    }


    //菜单状态下的按键的处理
    private void keyPressedEventMenu(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP :
            case KeyEvent.VK_W :
                menuIndex--;
                if(menuIndex<0){
                    menuIndex=MENUS.length-1;
                }
                break;

            case KeyEvent.VK_DOWN :
            case KeyEvent.VK_S :
                menuIndex++;
                if(menuIndex>MENUS.length-1){
                    menuIndex=0;
                }
                break;

            case KeyEvent.VK_LEFT :
            case KeyEvent.VK_A :
                break;

            case KeyEvent.VK_RIGHT :
            case KeyEvent.VK_D :
                break;

            case KeyEvent.VK_ENTER:
                //TODO
                newGame();
                break;
        }
    }




    private void keyPressedEventHelp(int keyCode) {

    }

    private void keyPressedEventAbout(int keyCode) {

    }
    //游戏运行中的按键处理方法
    private void keyPressedEventRun(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                myTank.setDir(Tank.DIR_UP);
                myTank.setState(Tank.STATE_MOVE);
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                myTank.setDir(Tank.DIR_DOWN);
                myTank.setState(Tank.STATE_MOVE);
                break;

            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                myTank.setDir(Tank.DIR_LEFT);
                myTank.setState(Tank.STATE_MOVE);
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                myTank.setDir(Tank.DIR_RIGHT);
                myTank.setState(Tank.STATE_MOVE);
                break;

            case KeyEvent.VK_SPACE:
                myTank.fire();
                break;
        }
    }

    private void keyPressedEventOver(int keyCode) {

    }

    //按键松开的时候，游戏中的处理方法
    private void keyReleasedEventRun(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                myTank.setState(Tank.STATE_STAND);
                break;
        }
    }

    /**
     * 开始新游戏的方法
     */
    private void newGame() {
        gameState=STATE_RUN;
        //创建坦克对象、敌人的坦克对象
        myTank=new Tank(400,200,Tank.DIR_DOWN);

        //使用一个单独的线程用于控制生产敌人的坦克
        new Thread(){
            @Override
            public void run() {
                while(true){
                    if(enemies.size()< ENEMY_MAX_COUNT){
                        Tank enemy=Tank.createEnemy();
                        enemies.add(enemy);
                    }
                    try {
                        Thread.sleep(ENEMY_BORN_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void run() {
        while(true){
            //在此调用repaint，回调update
            repaint();
            try {
                Thread.sleep(REPAINT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
