package com.lyc.game;

import com.lyc.util.MyUtil;

import java.awt.*;
import java.util.ArrayList;
import java.awt.List;

/**
 * 坦克类
 */
public class Tank {
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
    private int speed;
    private int dir;
    private int state=STATE_STAND;
    private Color color;

    //TODO 炮弹
    private List bullets =new List();

    public Tank(int x,int y,int dir){
        this.x=x;
        this.y=y;
        this.dir=dir;
        color= MyUtil.getRandomColor();
    }

    /**|
     * 绘制坦克
     * @param g
     */
    public void draw(Graphics g){
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

    public List getBullets() {
        return bullets;
    }

    public void setBullets(List bullets) {
        this.bullets = bullets;
    }
}
