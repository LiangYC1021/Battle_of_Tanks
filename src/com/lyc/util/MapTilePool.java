package com.lyc.util;

import com.lyc.map.MapTile;
import com.lyc.tank.EnemyTank;
import com.lyc.tank.Tank;

import java.util.ArrayList;
import java.util.List;

public class MapTilePool {
    public static final int DEFAULT_POOL_SIZE=50;
    public static final int POOL_MAX_SIZE=70;

    private static List<MapTile> pool =new ArrayList<>();
    static {
        for (int i = 0; i <DEFAULT_POOL_SIZE; i++) {
            pool.add(new MapTile());
        }
    }

    /**
     * 从池塘中获取一个对象
     * @return
     */
    public static MapTile get(){
        MapTile tile=null;
        //池塘没有啦!
        if(pool.isEmpty()){
            tile=new MapTile();
        }

        else tile=pool.remove(0);
        return tile;
    }

    public static void theReturn(MapTile tile){
        //归还
        if(pool.size()==POOL_MAX_SIZE) {
            return;
        }
        else pool.add(tile);
    }

}
