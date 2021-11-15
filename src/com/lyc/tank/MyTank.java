package com.lyc.tank;

import com.lyc.util.MyUtil;

import java.awt.*;

/**
 * 自己的坦克类
 */
public class MyTank extends Tank{
    //坦克的图片数组
    private static Image[] tankImg;
    static{
        tankImg=new Image[4];
        tankImg[0]= MyUtil.createImage("res/hero1U.gif");
        tankImg[1]= MyUtil.createImage("res/hero1D.gif");
        tankImg[2]= MyUtil.createImage("res/hero1L.gif");
        tankImg[3]= MyUtil.createImage("res/hero1R.gif");
    }

    public MyTank(int x, int y, int dir) {
        super(x, y, dir);
//        DEFAULT_HP=5;
//        setHp(DEFAULT_HP);
    }

    @Override
    public void drawImgTank(Graphics g) {
        g.drawImage(tankImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);
    }

}
