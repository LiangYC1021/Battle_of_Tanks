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

    public static final String[] USER_NAME = {
        "G","壹号玩家","非白","亚当斯","传奇","凤凰","冠军","卡特","国王","女王","奥兹",
        "公爵","呆头鹅","厨师","厄斐琉斯","英雄","贝尔","鲨鱼","马丁","船长","莫里斯",
        "闪电侠","盖伊","佐伊","怀特","宙斯","X","希拉","小袋鼠","李","库帕","霞",
        "锤石","卡莎","米勒","爱德华","Zed","探险家","摩根","小学弟","小袋鼠","格林",
        "旋风","托马斯","瑞兹","卢锡安","莫德凯撒","洛","烬","嘉文","熏伯","荃","库拉肯",
        "休斯","索尔","塔莉垭","Umi","Vex","吸血鬼","厅长","主播","地球人"
    };

    /**
     * 得到一个随机的名字
     * @return
     */
    public static final String getRandomName(){
        return USER_NAME[getRandomNumber(0,USER_NAME.length)];
    }
}
