package com.lyc.util;

import java.awt.*;

/**
 * 工具类
 */
public class MyUtil {
    private MyUtil(){}

    /**
     * 得到指定的区间的随机数
     * @param min 区间最小值，包含
     * @param max 区间最大值，不包含
     * @return 随机数
     */
    public static final int getRandomNumber(int min,int max){
        return (int)(Math.random()*(max-min)+min);
    }

    /**
     * 得到随机的颜色
     * @return
     */
    public static final Color getRandomColor(){
        int red=getRandomNumber(0,256);
        int blue=getRandomNumber(0,256);
        int green=getRandomNumber(0,256);
        Color c=new Color(red,green,blue);
        return c;
    }

    /**
     * 判断某一个点是否在一个正方形的内部
     * @param rectX 正方形中心点的x坐标
     * @param rectY 正方形中心点的y坐标
     * @param radius 正方形边长的一半
     * @param pointX 点的x坐标
     * @param pointY 点的y坐标
     * @return 如果点在正方形内部，返回true，否则返回false
     */
    public static final boolean isCollide(int rectX,int rectY,int radius,int pointX,int pointY){
        int disX=Math.abs(rectX-pointX);
        int disY=Math.abs(rectY-pointY);
        return (disX<radius)&&(disY<radius);
    }

    /**
     * 根据图片的路径获取图片
     * @param path 图片资源的路径
     * @return
     */
    public static final Image createImage(String path){
        return Toolkit.getDefaultToolkit().createImage(path);
    }
}
