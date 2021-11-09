package com.lyc.util;

import com.lyc.game.Bullet;
import com.lyc.game.Explode;

import java.util.ArrayList;
import java.util.List;

/**
 * 爆炸效果的对象池
 */
public class ExplodesPool {

    public static final int DEFAULT_POOL_SIZE=10;
    public static final int POOL_MAX_SIZE=20;

    //用于保存所有的爆炸效果的容器
    private static List<Explode> pool =new ArrayList<>();
    static {
        for (int i = 0; i <DEFAULT_POOL_SIZE; i++) {
            pool.add(new Explode());
        }
    }

    /**
     * 从池塘中获取一个爆炸效果对象
     * @return
     */
    public static Explode get(){
        Explode explode=null;
        //池塘没有啦!
        if(pool.isEmpty()){
            explode=new Explode();
        }
        //池塘中还有对象，拿走第一个位置的子弹对象
        else explode=pool.remove(0);
        return explode;
    }

    public static void theReturn(Explode explode){
        if(pool.size()==POOL_MAX_SIZE) {
            return;
        }
        else pool.add(explode);
    }
}
