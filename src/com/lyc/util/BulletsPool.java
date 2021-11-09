package com.lyc.util;

import com.lyc.game.Bullet;

import java.util.ArrayList;
import java.util.List;

/**
 * 子弹对象池类
 */
public class BulletsPool {
    public static final int DEFAULT_POOL_SIZE=2000;
    public static final int POOL_MAX_SIZE=3000;

    //用于保存所有的子弹的容器
    private static List<Bullet> pool =new ArrayList<>();
    //在类加载的时候，创建2000个子弹对象添加到容器中
    static {
        for (int i = 0; i <DEFAULT_POOL_SIZE; i++) {
            pool.add(new Bullet());
        }
    }

    /**
     * 从池塘中获取一个子弹对象
     * @return
     */
    public static Bullet get(){
        Bullet bullet=null;
        //池塘没有啦!
        if(pool.isEmpty()){
            bullet=new Bullet();
        }
        //池塘中还有对象，拿走第一个位置的子弹对象
        else bullet=pool.remove(0);
        return bullet;
    }
    //子弹被销毁的时候，归还到池塘中
    public static void theReturn(Bullet bullet){
        //池塘中的子弹的个数已经到达最大值，就不用归还
        if(pool.size()==POOL_MAX_SIZE) {
            return;
        }
        else pool.add(bullet);
    }
}
