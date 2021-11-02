package com.lyc.game;

import com.lyc.util.MyUtil;

import java.awt.*;

/**
 * 用来控制爆炸效果的类
 */
public class Explode {
    public static final int EXPLODE_FRAME_COUNT=8;
    //导入资源
    private static Image[] img;
    //爆炸效果的图片的宽度和高度
    public static int explodeWidth;
    public static int explodeHeight;
    static {
        img=new Image[EXPLODE_FRAME_COUNT];
        for (int i = 0; i < EXPLODE_FRAME_COUNT; i++) {
            int j=i+1;
            img[i]= MyUtil.createImage("res/blast"+j+".gif");
        }
    }

    //爆炸效果的属性
    private int x,y;
    //当前播放的帧的下标
    private int index=0;
    private boolean visible;

    public Explode() {
        explodeWidth=explodeHeight=index=0;
        visible=true;
    }
    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        explodeWidth=explodeHeight=index=0;
        visible=true;
    }

    public void draw(Graphics g){
        //对爆炸效果偏移量的确定
        if(explodeHeight<=0){
            explodeHeight=img[0].getHeight(null)/2;
            explodeWidth=img[0].getWidth(null)/2;
        }
        if(!visible)return;
        g.drawImage(img[index],x-explodeWidth,y-explodeHeight,null);
        index++;
        if(index>=EXPLODE_FRAME_COUNT){
            visible=false;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
