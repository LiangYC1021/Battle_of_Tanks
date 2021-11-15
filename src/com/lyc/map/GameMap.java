package com.lyc.map;

import com.lyc.game.GameFrame;
import com.lyc.tank.Tank;
import com.lyc.util.Constant;
import com.lyc.util.MapTilePool;
import com.lyc.util.MyUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏地图类
 */
public class GameMap {

    public static final int MAP_X=Tank.RADIUS*3+GameFrame.leftBarH;
    public static final int MAP_Y=Tank.RADIUS*3+GameFrame.titleBarH;
    public static final int MAP_WIDTH= Constant.FRAME_WIDTH-Tank.RADIUS*6-GameFrame.leftBarH;
    public static final int MAP_HEIGHT=Constant.FRAME_HEIGHT-Tank.RADIUS*8-GameFrame.titleBarH;

    //地图元素块的容器
    private List<MapTile> tiles=new ArrayList<>();

    //大本营
    private TankHouse house;
    public GameMap() {
       initMap();
    }

    /**
     * 初始化地图元素块
     */
    private void initMap(){
        //随机的得到一个地图元素块，添加到容器中
        final int COUNT=20;
        for(int i=0;i<COUNT;i++){
            MapTile tile = MapTilePool.get();
            int x= MyUtil.getRandomNumber(MAP_X,MAP_X+MAP_WIDTH-MapTile.tileW);
            int y= MyUtil.getRandomNumber(MAP_Y,MAP_Y+MAP_HEIGHT-MapTile.tileW);
            //新生成的块有重叠部分，重新生成
            if(isCollide(tiles,x,y)){
                i--;
                continue;
            }
            tile.setX(x);
            tile.setY(y);
            tiles.add(tile);
        }
        //初始化大本营
        house=new TankHouse();
        addHouse();
    }

    private void addHouse(){
        tiles.addAll(house.getTiles());
    }
    /**
     * 判断某一个点是否和已经产生了的所有的块有重叠
     * @param tiles
     * @param x
     * @param y
     * @return 有重叠返回true 否则返回false
     */
    private boolean isCollide(List<MapTile> tiles,int x,int y){
        for (MapTile tile : tiles) {
            int tilex=tile.getX(),tiley=tile.getY();
            if(Math.abs(tilex-x)<=MapTile.tileW && Math.abs(tiley-y)<=MapTile.tileW)return true;
        }
        return false;
    }
    public void draw(Graphics g){
        for (MapTile tile : tiles) {
            tile.draw(g);
        }
//        house.draw(g);
    }

    public List<MapTile> getTiles() {
        return tiles;
    }

    /**
     * 将所有不可见的地图块从容器中移除
     */
    public void clearDestroyTile(){
        for (int i = 0; i < tiles.size(); i++) {
            MapTile tile=tiles.get(i);
            if(!tile.isVisible()){
                tiles.remove(i);
                i--;
            }
        }
    }
}
