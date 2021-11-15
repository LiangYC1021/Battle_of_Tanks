package com.lyc.map;

import com.lyc.game.GameFrame;
import com.lyc.util.Constant;
import com.lyc.util.MapTilePool;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 玩家的大本营
 */
public class TankHouse {
    public static final int HOUSE_X= 2+(Constant.FRAME_WIDTH-3*MapTile.tileW- GameFrame.leftBarH)>>1;
    public static final int HOUSE_Y= (Constant.FRAME_HEIGHT-2*MapTile.tileW- GameFrame.bottomBarH);

    //一共六块地图块
    private List<MapTile> tiles =new ArrayList<>();

    public TankHouse() {
        tiles.add(new MapTile(HOUSE_X,HOUSE_Y));
        tiles.add(new MapTile(HOUSE_X,HOUSE_Y+ MapTile.tileW));
        tiles.add(new MapTile(HOUSE_X+ MapTile.tileW,HOUSE_Y));
        tiles.add(new MapTile(HOUSE_X+ MapTile.tileW*2,HOUSE_Y));
        tiles.add(new MapTile(HOUSE_X+ MapTile.tileW*2,HOUSE_Y+ MapTile.tileW));
        //老巢块
        tiles.add(new MapTile(HOUSE_X+ MapTile.tileW,HOUSE_Y+ MapTile.tileW));
        tiles.get(tiles.size()-1).setType(MapTile.TYPE_HOUSE);
    }
    public void draw(Graphics g){
        for (MapTile tile : tiles) {
            tile.draw(g);
        }
    }

    public List<MapTile> getTiles() {
        return tiles;
    }

}
