package com.lyc.util;

import java.awt.*;

/**
 * 游戏中的常量都在该类中维护，方便后期的管理
 */
public class Constant {
    /*****************************游戏窗口相关**********************************/
    public static final String GAME_TITLE = "坦克大战";

    public static final int FRAME_WIDTH=1000;
    public static final int FRAME_HEIGHT=700;

    //获得屏幕的宽高
    public static final int SCREEN_W=Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_H=Toolkit.getDefaultToolkit().getScreenSize().height;

    public static final int FRAME_X=(SCREEN_W-FRAME_WIDTH)>>1;
    public static final int FRAME_Y=(SCREEN_H-FRAME_HEIGHT)>>1;

    /*****************************游戏菜单相关**********************************/
    public static final int STATE_MENU=0;
    public static final int STATE_HELP=1;
    public static final int STATE_ABOUT=2;
    public static final int STATE_RUN=3;
    public static final int STATE_OVER=4;

    public static final String[] MENUS={
            "开始游戏",
            "继续游戏",
            "游戏帮助",
            "游戏关于",
            "退出游戏"
    };

    public static final String OVER_STR0="按ESC键退出游戏";
    public static final String OVER_STR1="按ENTER键回到主菜单";

    //字体的设置
    public static final Font GAME_FONT =new Font("幼圆",Font.BOLD,24);
    public static final Font SMALL_FONT =new Font("幼圆",Font.BOLD,16);

    public static final int REPAINT_INTERVAL = 30;
    //最大敌人数量
    public static final int ENEMY_MAX_COUNT=10;
    public static final int ENEMY_BORN_INTERVAL =5000;

    public static final int ENEMY_AI_INTERVAL=1500;
    public static final double ENEMY_FIRE_PERCENT=0.04;

    public static final String HOUSE_STR="家";
}
