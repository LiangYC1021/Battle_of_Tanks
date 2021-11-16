package com.lyc.map;

import com.lyc.game.GameFrame;
import com.lyc.tank.Tank;
import com.lyc.util.Constant;
import com.lyc.util.MapTilePool;
import com.lyc.util.MyUtil;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
       initMap(2);
    }

    /**
     * 初始化地图元素块
     * level 第几关
     */
    private void initMap(int level){
        loadLevel(level);
        //初始化大本营
        house=new TankHouse();
        addHouse();
    }

    /**
     * 加载关卡信息
     * @param level
     */
    private void loadLevel(int level) {
        Properties prop=new Properties();
        try {
            prop.load(new FileInputStream("level/lv_"+level));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将所有的地图信息都加载进来
        int enemyCount= Integer.parseInt(prop.getProperty("enemyCount"));
        String methodName=prop.getProperty("method");
        int invokeCount= Integer.parseInt(prop.getProperty("invokeCount"));

        //把实参都读取到数组中来
        String[] params=new String[invokeCount];
        for (int i = 1; i <= invokeCount; i++) {
            params[i-1]=prop.getProperty("param"+i);
        }
        invokeMethod(methodName,params);
    }

    //调用
    private void invokeMethod(String name, String[] params) {
        for (String param : params) {
            //获得每一行的方法的参数，解析
            String[] split=param.split(",");
            //使用一个int数组来保存解析后的内容
            int[] arr=new int[split.length];
            int i;
            for (i = 0; i < split.length; i++) {
                arr[i]= Integer.parseInt(split[i]);
            }
            //块之间的间隔是地图块的倍数
            final int DIS=MapTile.tileW;
            int dis=(int)(Double.parseDouble(split[i-1])*DIS);
            switch (name){
                case "addRow":
                    addRow(MAP_X+arr[0]*DIS,MAP_Y+arr[1]*DIS,MAP_X+MAP_WIDTH-arr[2]*DIS,arr[3],dis);
                    break;
                case "addCol":
                    addCol(MAP_X+arr[0]*DIS,MAP_Y+arr[1]*DIS,
                            MAP_Y+ MAP_HEIGHT-arr[2]*DIS,arr[3],dis);
                    break;
                case "addRect":
                    addRect(MAP_X+arr[0]*DIS,MAP_Y+arr[1]*DIS,
                            MAP_X+ MAP_WIDTH-arr[2]*DIS,
                            MAP_Y-MAP_HEIGHT-arr[3]*DIS,arr[4],dis);
                    break;
            }
        }
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

    /**
     * 只对没有遮挡效果的块进行绘制
     * @param g
     */
    public void drawBk(Graphics g){
        for (MapTile tile : tiles) {
            if(tile.getType()!=MapTile.TYPE_COVER)tile.draw(g);
        }
    }
    public void drawCover(Graphics g){
        for (MapTile tile : tiles) {
            if(tile.getType()==MapTile.TYPE_COVER)tile.draw(g);
        }
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

    /**
     * 往地图块容器中添加一行指定类型的地图块
     * @param type 地图块类型
     * @param DIS 地图块的间隔
     */
    public void addRow(int startX,int startY,int endX,int type,final int DIS){
        int count=0;
        count=(endX-startX)/(MapTile.tileW+DIS);
        for (int i = 0; i < count; i++) {
            MapTile tile = MapTilePool.get();
            tile.setType(type);
            tile.setX(startX+i*(MapTile.tileW+DIS));
            tile.setY(startY);
            tiles.add(tile);
        }
    }

    /**
     * 往地图块中添加一列元素
     * @param startX
     * @param startY
     * @param endY
     * @param type
     * @param DIS
     */
    public void addCol(int startX,int startY,int endY,int type,final int DIS){
        int count=0;
        count=(endY-startY)/(MapTile.tileW+DIS);
        for (int i = 0; i < count; i++) {
            MapTile tile = MapTilePool.get();
            tile.setType(type);
            tile.setX(startX);
            tile.setY(startY+i*(MapTile.tileW+DIS));
            tiles.add(tile);
        }
    }

    //对指定的矩形区域添加元素块
    public void addRect(int startX,int startY,int endX,int endY,int type,final int DIS){
        int len=(MapTile.tileW+DIS);
        int rows=(endY-startY)/len;
        for (int i = 0; i < rows; i++) {
            addRow(startX,startY+i*len,endX,type,DIS);
        }
        int cols=(endX-startX)/(MapTile.tileW+DIS);
    }
}
