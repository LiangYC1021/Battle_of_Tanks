package com.lyc.game;

import com.lyc.util.BulletsPool;
import com.lyc.util.Constant;
import com.lyc.util.MyUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * 坦克类
 */
public class Tank {
    //坦克的图片数组
    private static Image[] tankImg;
    private static Image[] enemyImg;
    //静态代码块中对它进行初始化
    static{
        tankImg=new Image[4];
        tankImg[0]=Toolkit.getDefaultToolkit().createImage("res/hero1U.gif");
        tankImg[1]=Toolkit.getDefaultToolkit().createImage("res/hero1D.gif");
        tankImg[2]=Toolkit.getDefaultToolkit().createImage("res/hero1L.gif");
        tankImg[3]=Toolkit.getDefaultToolkit().createImage("res/hero1R.gif");

        enemyImg=new Image[4];
        enemyImg[0]=Toolkit.getDefaultToolkit().createImage("res/enemy1U.gif");
        enemyImg[1]=Toolkit.getDefaultToolkit().createImage("res/enemy1D.gif");
        enemyImg[2]=Toolkit.getDefaultToolkit().createImage("res/enemy1L.gif");
        enemyImg[3]=Toolkit.getDefaultToolkit().createImage("res/enemy1R.gif");
    }

    //四个方向
    public static final int DIR_UP=0;
    public static final int DIR_DOWN=1;
    public static final int DIR_LEFT=2;
    public static final int DIR_RIGHT=3;
    //半径
    public static final int RADIUS=20;
    //默认速度 每帧4像素
    public static final int DEFAULT_SPEED=4;
    //坦克的状态
    public static final int STATE_STAND=0;
    public static final int STATE_MOVE=1;
    public static final int STATE_DIE=2;
    //坦克的初始生命
    public static final int DEFAULT_HP=1000;

    private int x,y;

    private int hp;
    private int atk;
    private int speed=DEFAULT_SPEED;
    private int dir;
    private int state=STATE_STAND;
    private Color color;
    private boolean isEnemy=false;

    //TODO 炮弹
    private List<Bullet> bullets =new ArrayList();

    public Tank(int x,int y,int dir){
        this.x=x;
        this.y=y;
        this.dir=dir;
        color= MyUtil.getRandomColor();
    }

    //用于创建一个敌人的坦克
    public static Tank createEnemy(){
        int x= MyUtil.getRandomNumber(0,2)==0 ? RADIUS+GameFrame.leftBarH:
                Constant.FRAME_WIDTH-RADIUS-GameFrame.rightBarH;
        int y=GameFrame.titleBarH+RADIUS;
        int dir=DIR_DOWN;
        Tank enemy = new Tank(x, y, dir);
        enemy.isEnemy=true;

        //TODO
        enemy.state=STATE_MOVE;

        return enemy;
    }

    /**|
     * 绘制坦克
     * @param g
     */
    //每一帧都调用draw方法
    public void draw(Graphics g){
        logic();
        drawImgTank(g);
        drawBullets(g);
    }

    /**
     * 使用图片的方式去绘制坦克
     * @param g
     */
    private void drawImgTank(Graphics g){
        if(isEnemy){
            g.drawImage(enemyImg[dir],x-RADIUS,y-RADIUS,null);
        }
        else g.drawImage(tankImg[dir],x-RADIUS,y-RADIUS,null);
    }

    /**
     * 使用系统的方式去绘制坦克
     * @param g
     */
    private void drawTank(Graphics g){
        g.setColor(color);
        //绘制坦克的圆
        g.fillOval(x-RADIUS,y-RADIUS,RADIUS<<1,RADIUS<<1);
        int endX=x,endY=y;
        switch (dir){
            case DIR_UP :
                endY=y-RADIUS*2;
                g.drawLine(x-1,y,endX-1,endY);
                g.drawLine(x+1,y,endX+1,endY);
                break;
            case DIR_DOWN :
                endY=y+RADIUS*2;
                g.drawLine(x-1,y,endX-1,endY);
                g.drawLine(x+1,y,endX+1,endY);
                break;
            case DIR_LEFT :
                endX=x-RADIUS*2;
                g.drawLine(x,y-1,endX,endY-1);
                g.drawLine(x,y+1,endX,endY+1);
                break;
            case DIR_RIGHT :
                endX=x+RADIUS*2;
                g.drawLine(x,y-1,endX,endY-1);
                g.drawLine(x,y+1,endX,endY+1);
                break;
        }
        g.drawLine(x,y,endX,endY);
    }
    //坦克的逻辑处理
    private void logic(){
        switch (state){
            case STATE_STAND:
                break;
            case STATE_MOVE:
                move();
                break;
            case STATE_DIE:
                break;

        }
    }

    //坦克移动的功能
    private void move(){
        switch (dir){
            case DIR_UP:
                y-=speed;
                if(y<RADIUS+GameFrame.titleBarH)y=RADIUS+GameFrame.titleBarH;
                break;
            case DIR_DOWN:
                y+=speed;
                if(y>Constant.FRAME_HEIGHT-RADIUS-GameFrame.bottomBarH)y=Constant.FRAME_HEIGHT-RADIUS-GameFrame.bottomBarH;
                break;
            case DIR_LEFT:
                x-=speed;
                if(x<RADIUS+GameFrame.leftBarH)x=RADIUS+GameFrame.leftBarH;
                break;
            case DIR_RIGHT:
                x+=speed;
                if(x>Constant.FRAME_WIDTH-RADIUS-GameFrame.rightBarH)x=Constant.FRAME_WIDTH-RADIUS-GameFrame.rightBarH;
                break;
        }
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public java.util.List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(java.util.List<Bullet> bullets) {
        this.bullets = bullets;
    }

    /**
     * 坦克的功能，坦克开火的方法
     * 创建了一个子弹对象，子弹对象的属性信息通过坦克的信息获得
     * 然后将创建的子弹添加到坦克管理的容器中。
     */
    public void fire(){
        int bulletX=x;
        int bulletY=y;
        switch (dir){
            case DIR_UP -> bulletY-=RADIUS;
            case DIR_DOWN -> bulletY+=RADIUS;
            case DIR_LEFT -> bulletX-=RADIUS;
            case DIR_RIGHT -> bulletX+=RADIUS;
        }
        //从对象池中获取子弹对象
        Bullet bullet = BulletsPool.get();
        //设置子弹的属性
        bullet.setX(bulletX);
        bullet.setY(bulletY);
        bullet.setDir(dir);
        bullet.setAtk(atk);
        bullet.setColor(color);
        bullet.setVisible(true);
        bullets.add(bullet);
    }

    /**
     * 将当前坦克的发射的所有子弹绘制出来
     * @param g
     */
    private void drawBullets(Graphics g){
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        //遍历所有的子弹,将不可见的子弹移除，并还原回对象池
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if(!bullet.isVisible()){
                Bullet remove = bullets.remove(i);
                BulletsPool.theReturn(remove);
            }
//            System.out.println("坦克的子弹的数量："+bullets.size());
        }
    }
}
