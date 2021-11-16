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
    public static final int TYPE_SWIFT=1;
    public static final int TYPE_HEAVY=2;

    private long aiTime;
    //时间戳起始点，记录5秒开始的时间

    private static Image[] greyImg;
    static{
        greyImg=new Image[4];
        greyImg[0]= MyUtil.createImage("res/enemy1U.gif");
        greyImg[1]= MyUtil.createImage("res/enemy1D.gif");
        greyImg[2]= MyUtil.createImage("res/enemy1L.gif");
        greyImg[3]= MyUtil.createImage("res/enemy1R.gif");
    }
    private static Image[] greenImg;
    static{
        greenImg=new Image[4];
        greenImg[0]= MyUtil.createImage("res/enemy2U.png");
        greenImg[1]= MyUtil.createImage("res/enemy2D.png");
        greenImg[2]= MyUtil.createImage("res/enemy2L.png");
        greenImg[3]= MyUtil.createImage("res/enemy2R.png");
    }
    private static Image[] redImg;
    static{
        redImg=new Image[4];
        redImg[0]= MyUtil.createImage("res/enemy3U.png");
        redImg[1]= MyUtil.createImage("res/enemy3D.png");
        redImg[2]= MyUtil.createImage("res/enemy3L.png");
        redImg[3]= MyUtil.createImage("res/enemy3R.png");
    }




    private EnemyTank(int x, int y, int dir) {
        super(x, y, dir);
        //敌人一旦创建就开始计时
        aiTime=System.currentTimeMillis();
//        if(type==TYPE_HEAVY){
//            setHp(DEFAULT_HP+1);
//            setSpeed(DEFAULT_SPEED/2);
//        }
//        else if(type==TYPE_SWIFT){
//            setHp(DEFAULT_HP-1);
//            setSpeed(DEFAULT_SPEED*2);
//        }
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
        enemy.setType(MyUtil.getRandomNumber(0,3));
        if(enemy.getType()==TYPE_SWIFT){
            enemy.setMAX_HP(DEFAULT_HP-1);
            enemy.setHp(DEFAULT_HP-1);
            enemy.setSpeed(DEFAULT_SPEED*3/2);
            enemy.CUR_SPEED=DEFAULT_SPEED*3/2;
        }
        else if(enemy.getType()==TYPE_HEAVY){
            enemy.setMAX_HP(DEFAULT_HP+1);
            enemy.setHp(DEFAULT_HP+1);
            enemy.setSpeed(DEFAULT_SPEED*2/3);
            enemy.CUR_SPEED=DEFAULT_SPEED*2/3;
        }
        return enemy;
    }

    public void drawImgTank(Graphics g){
        ai();
        if(type==TYPE_NORMAL)g.drawImage(greyImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);
        if(type==TYPE_SWIFT)g.drawImage(greenImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);
        if(type==TYPE_HEAVY)g.drawImage(redImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);
//        System.out.println("坦克类型:"+type);
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
