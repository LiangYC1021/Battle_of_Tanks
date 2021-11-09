package com.lyc.util;

import com.lyc.game.Bullet;
import com.lyc.tank.EnemyTank;
import com.lyc.tank.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * 敌人坦克对象池
 */
public class EnemyTanksPool {
    public static final int DEFAULT_POOL_SIZE=20;
    public static final int POOL_MAX_SIZE=20;

    private static List<Tank> pool =new ArrayList<>();
    static {
        for (int i = 0; i <DEFAULT_POOL_SIZE; i++) {
            pool.add(new EnemyTank());
        }
    }

    /**
     * 从池塘中获取一个对象
     * @return
     */
    public static Tank get(){
        Tank tank=null;
        //池塘没有啦!
        if(pool.isEmpty()){
            tank=new EnemyTank();
        }
        //池塘中还有对象，拿走第一个位置的子弹对象
        else tank=pool.remove(0);
        return tank;
    }
    //子弹被销毁的时候，归还到池塘中
    public static void theReturn(Tank tank){
        //池塘中的子弹的个数已经到达最大值，就不用归还
        if(pool.size()==POOL_MAX_SIZE) {
            return;
        }
        else pool.add(tank);
    }
}
