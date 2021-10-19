package com.lyc.game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.lyc.util.Constant.*;
/**
 * 游戏的主窗口类。
 * 所有游戏展示的内容都要在该类中实现
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
    }


    /**
     * 是Frame类的方法，继承了Frame类
     * 该方法负责了所有绘制的内容
     * 所有需要在屏幕中显示的内容
     * 都需要在该方法中调用。该方法不能主动调用。
     * 必须调用repaint()去回调该方法
     * @param g
     * update为刷新，导致页面一直出不来，故更改为paint
     */
    public void update(Graphics g){
        g.setFont(GAME_FONT);
        switch (gameState) {
            case STATE_MENU -> drawMenu(g);
            case STATE_HELP -> drawHelp(g);
            case STATE_ABOUT -> drawAbout(g);
            case STATE_RUN -> drawRun(g);
            case STATE_OVER -> drawOver(g);
        }
    }

    private void drawOver(Graphics g) {

    }

    private void drawRun(Graphics g) {
        //绘制黑色的背景
        g.setColor(Color.BLACK);
        g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);

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
                    case STATE_MENU -> keyEventMenu(keyCode);
                    case STATE_HELP -> keyEventHelp(keyCode);
                    case STATE_ABOUT -> keyEventAbout(keyCode);
                    case STATE_RUN -> keyEventRun(keyCode);
                    case STATE_OVER -> keyEventOver(keyCode);
                }
            }

            //按键松开的时候回调的内容
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    //菜单状态下的按键的处理
    private void keyEventMenu(int keyCode) {
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

    /**
     * 开始新游戏的方法
     */
    private void newGame() {
        gameState=STATE_RUN;
        //创建坦克对象、敌人的坦克对象

    }


    private void keyEventHelp(int keyCode) {

    }

    private void keyEventAbout(int keyCode) {

    }

    private void keyEventRun(int keyCode) {

    }

    private void keyEventOver(int keyCode) {

    }

    @Override
    public void run() {
        while(true){
            repaint();
            try {
                Thread.sleep(REPAINT_INTERAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
