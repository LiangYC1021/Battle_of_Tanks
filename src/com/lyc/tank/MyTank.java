package com.lyc.tank;

import java.awt.*;

/**
 * 自己的坦克类
 */
public class MyTank extends Tank{
    //坦克的图片数组
    private static Image[] tankImg;
    static{
        tankImg=new Image[4];
        tankImg[0]=Toolkit.getDefaultToolkit().createImage("res/hero1U.gif");
        tankImg[1]=Toolkit.getDefaultToolkit().createImage("res/hero1D.gif");
        tankImg[2]=Toolkit.getDefaultToolkit().createImage("res/hero1L.gif");
        tankImg[3]=Toolkit.getDefaultToolkit().createImage("res/hero1R.gif");
    }

    public MyTank(int x, int y, int dir) {
        super(x, y, dir);
    }

    @Override
    public void drawImgTank(Graphics g) {
        g.drawImage(tankImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);
    }
}
