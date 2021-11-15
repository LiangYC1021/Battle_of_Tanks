package com.lyc.map;

import com.lyc.game.Bullet;
import com.lyc.util.BulletsPool;
import com.lyc.util.Constant;
import com.lyc.util.MyUtil;

import java.awt.*;
import java.util.List;

/**
 * 地图元素块
 */
public class MapTile {
    public static final int TYPE_NORMAL=0;
    public static final int TYPE_HOUSE=1;
    public static final int TYPE_COVER=2;
    public static final int TYPE_HARD=3;

    public static int tileW=50;
    public static int tileH=50;
    public static int radius=tileW>>1;
    private int type=TYPE_NORMAL;

    public static Image[] tileImg;
    static {
        tileImg=new Image[4];
        tileImg[TYPE_NORMAL]= MyUtil.createImage("res/walls/tile.png");
        tileImg[TYPE_HOUSE]= MyUtil.createImage("res/walls/home_icon.png");
        tileImg[TYPE_COVER]= MyUtil.createImage("res/walls/grass.png");
        tileImg[TYPE_HARD]= MyUtil.createImage("res/walls/iron.png");
        if(tileW<=0)tileW=tileImg[0].getWidth(null);
        if(tileH<=0)tileH=tileImg[0].getHeight(null);
    }
    //图片资源的左上角
    private int x,y;
    private boolean visible=true;

    public MapTile(int x, int y) {
        this.x = x;
        this.y = y;
        if(tileW<=0)tileW=tileImg[0].getWidth(null);
        if(tileH<=0)tileH=tileImg[0].getHeight(null);
    }

    public MapTile() {
    }

    public void draw(Graphics g){
        if(!visible)return;
        if(tileW<=0)tileW=tileImg[0].getWidth(null);
        if(tileH<=0)tileH=tileImg[0].getHeight(null);
        g.drawImage(tileImg[type],x,y,null);
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    /**
     * 地图块和若干个子弹是否有碰撞
     * @param
     * @return
     */
    public boolean isCollideBullet(List<Bullet> bullets){
        if(!visible)return false;
        for (Bullet bullet : bullets) {
            int bulletX= bullet.getX();
            int bulletY= bullet.getY();
            boolean collide=MyUtil.isCollide(x+radius,y+radius,radius,bulletX,bulletY);
            if(collide){
                //子弹的销毁
                bullet.setVisible(false);
                BulletsPool.theReturn(bullet);
                return true;
            }
        }
        return false;
    }

    public boolean isHouse(){
        return type==TYPE_HOUSE;
    }
}
