package com.lyc.tank;

import com.lyc.game.GameFrame;
import com.lyc.util.Constant;
import com.lyc.util.EnemyTanksPool;
import com.lyc.util.MyUtil;

import java.awt.*;

/**
 * 敌人坦克类
 */
public class EnemyTank extends Tank{
    public static final int TYPE_NORMAL=0;
    public static final int TYPE_HEAVY=1;
    public static final int TYPE_SWIFT=2;
    int type;
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

    private EnemyTank(int x, int y, int dir) {
        super(x, y, dir);
        //敌人一旦创建就开始计时
        aiTime=System.currentTimeMillis();
        type=MyUtil.getRandomNumber(0,3);
        if(type==TYPE_HEAVY){
            setHp(DEFAULT_HP+1);
            setSpeed(DEFAULT_SPEED/2);
        }
        else if(type==TYPE_SWIFT){
            setHp(DEFAULT_HP-1);
            setSpeed(DEFAULT_SPEED*2);
        }
    }
    public EnemyTank(){
        aiTime=System.currentTimeMillis();
    }

    //用于创建一个敌人的坦克
    public static Tank createEnemy(){
        int x= MyUtil.getRandomNumber(0,2)==0 ? RADIUS+ GameFrame.leftBarH:
                Constant.FRAME_WIDTH-RADIUS-GameFrame.rightBarH;
        int y=GameFrame.titleBarH+RADIUS;
        int dir=DIR_DOWN;
//        Tank enemy = new EnemyTank(x, y, dir);
        //用对象池来创建敌人坦克
        Tank enemy=EnemyTanksPool.get();
        enemy.setX(x);
        enemy.setY(y);
        enemy.setDir(dir);

        enemy.setEnemy(true);
        enemy.setState(STATE_MOVE);
        enemy.setHp(Tank.DEFAULT_HP);
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
//        int INTERVAL=Constant.ENEMY_AI_INTERVAL;
        int INTERVAL=MyUtil.getRandomNumber(2000,3000);
        if(System.currentTimeMillis()-aiTime > INTERVAL){
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
