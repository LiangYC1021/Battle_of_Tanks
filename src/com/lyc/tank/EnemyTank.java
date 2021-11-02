package com.lyc.tank;

import com.lyc.game.GameFrame;
import com.lyc.util.Constant;
import com.lyc.util.MyUtil;

import java.awt.*;

/**
 * 敌人坦克类
 */
public class EnemyTank extends Tank{
    private static Image[] enemyImg;
    //时间戳起始点，记录5秒开始的时间
    private long aiTime;
    static{
        enemyImg=new Image[4];
        enemyImg[0]= MyUtil.createImage("res/enemy1U.gif");
        enemyImg[1]= MyUtil.createImage("res/enemy1D.gif");
        enemyImg[2]= MyUtil.createImage("res/enemy1L.gif");
        enemyImg[3]= MyUtil.createImage("res/enemy1R.gif");
    }
    public EnemyTank(int x, int y, int dir) {
        super(x, y, dir);
        //敌人一旦创建就开始计时
        aiTime=System.currentTimeMillis();
    }

    //用于创建一个敌人的坦克
    public static Tank createEnemy(){
        int x= MyUtil.getRandomNumber(0,2)==0 ? RADIUS+ GameFrame.leftBarH:
                Constant.FRAME_WIDTH-RADIUS-GameFrame.rightBarH;
        int y=GameFrame.titleBarH+RADIUS;
        int dir=DIR_DOWN;
        Tank enemy = new EnemyTank(x, y, dir);
        enemy.setEnemy(true);
        //TODO
        enemy.setState(STATE_MOVE);;
        return enemy;
    }

    public void drawImgTank(Graphics g){
        ai();
        g.drawImage(enemyImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);
    }

    /**
     * 敌人的AI
     */
    private void ai(){
        if(System.currentTimeMillis()-aiTime > Constant.ENEMY_AI_INTERVAL){
            //间隔5秒随机一个状态
            setState(MyUtil.getRandomNumber(0,2) ==0 ? STATE_STAND:STATE_MOVE);
            setDir(MyUtil.getRandomNumber(DIR_UP,DIR_RIGHT+1));
            aiTime=System.currentTimeMillis();
        }
        //比较小的概率去开火
        if(Math.random() < Constant.ENEMY_FIRE_PERCENT){
            fire();
        }
    }
}