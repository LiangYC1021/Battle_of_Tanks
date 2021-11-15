package com.lyc.tank;

import com.lyc.game.Bullet;
import com.lyc.game.Explode;
import com.lyc.game.GameFrame;
import com.lyc.map.MapTile;
import com.lyc.util.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 坦克类
 */
public abstract class Tank {
    //四个方向
    public static final int DIR_UP=0;
    public static final int DIR_DOWN=1;
    public static final int DIR_LEFT=2;
    public static final int DIR_RIGHT=3;
    //半径
    public static final int RADIUS=20;
    //默认速度 每帧4像素
    public static final int DEFAULT_SPEED=4;
    //坦克的状态
    public static final int STATE_STAND=0;
    public static final int STATE_MOVE=1;
    public static final int STATE_DIE=2;
    //坦克的初始生命
    public static final int DEFAULT_HP=2;

    private int x,y;
    private int oldX,oldY;
    private int hp=DEFAULT_HP;
    private String name;
    private int atk;
    private int speed=DEFAULT_SPEED;
    private int dir;
    private int state=STATE_STAND;
    private Color color;
    private boolean isEnemy=false;

    private BloodBar bar=new BloodBar();

    //炮弹
    private List<Bullet> bullets =new ArrayList();
    //使用容器来保存当前坦克上的所有爆炸的效果
    private List<Explode> explodes=new ArrayList<>();

    public Tank(int x, int y, int dir){
        this.x=x;
        this.y=y;
        this.dir=dir;
        initTank();
    }

    public Tank(){
        initTank();
    }

    private void initTank(){
        color= MyUtil.getRandomColor();
        name=MyUtil.getRandomName();
        atk=1;
    }
    /**|
     * 绘制坦克
     * @param g
     */
    //每一帧都调用draw方法
    public void draw(Graphics g){
        logic();
        drawImgTank(g);
        drawBullets(g);
        drawName(g);
        bar.draw(g);
    }

    private void drawName(Graphics g){
        g.setColor(color);
        g.setFont(Constant.SMALL_FONT);
        g.drawString(name,x-RADIUS,y-35);
    }
    /**
     * 使用图片的方式去绘制坦克
     * @param g
     */
    public abstract void drawImgTank(Graphics g);

    /**
     * 使用系统的方式去绘制坦克
     * @param g
     */
    private void drawTank(Graphics g){
        g.setColor(color);
        //绘制坦克的圆
        g.fillOval(x-RADIUS,y-RADIUS,RADIUS<<1,RADIUS<<1);
        int endX=x,endY=y;
        switch (dir){
            case DIR_UP :
                endY=y-RADIUS*2;
                g.drawLine(x-1,y,endX-1,endY);
                g.drawLine(x+1,y,endX+1,endY);
                break;
            case DIR_DOWN :
                endY=y+RADIUS*2;
                g.drawLine(x-1,y,endX-1,endY);
                g.drawLine(x+1,y,endX+1,endY);
                break;
            case DIR_LEFT :
                endX=x-RADIUS*2;
                g.drawLine(x,y-1,endX,endY-1);
                g.drawLine(x,y+1,endX,endY+1);
                break;
            case DIR_RIGHT :
                endX=x+RADIUS*2;
                g.drawLine(x,y-1,endX,endY-1);
                g.drawLine(x,y+1,endX,endY+1);
                break;
        }
        g.drawLine(x,y,endX,endY);
    }
    //坦克的逻辑处理
    private void logic(){
        switch (state){
            case STATE_STAND:
                break;
            case STATE_MOVE:
                move();
                break;
            case STATE_DIE:
                break;

        }
    }

    //坦克移动的功能
    private void move(){
        oldX=x;oldY=y;
        switch (dir){
            case DIR_UP:
                y-=speed;
                if(y<RADIUS+GameFrame.titleBarH)y=RADIUS+GameFrame.titleBarH;
                break;
            case DIR_DOWN:
                y+=speed;
                if(y>Constant.FRAME_HEIGHT-RADIUS-GameFrame.bottomBarH)y=Constant.FRAME_HEIGHT-RADIUS-GameFrame.bottomBarH;
                break;
            case DIR_LEFT:
                x-=speed;
                if(x<RADIUS+GameFrame.leftBarH)x=RADIUS+GameFrame.leftBarH;
                break;
            case DIR_RIGHT:
                x+=speed;
                if(x>Constant.FRAME_WIDTH-RADIUS-GameFrame.rightBarH)x=Constant.FRAME_WIDTH-RADIUS-GameFrame.rightBarH;
                break;
        }
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 坦克的功能，坦克开火的方法
     * 创建了一个子弹对象，子弹对象的属性信息通过坦克的信息获得
     * 然后将创建的子弹添加到坦克管理的容器中。
     */
    boolean canFire=true;
    private long fireTime=0;
    public static final int FIRE_INTERVAL=500;
    public void fire(){
//        if(System.currentTimeMillis()-fireTime<=FIRE_INTERVAL)return;
        if(!canFire)return;
        int bulletX=x;
        int bulletY=y;
        switch (dir){
            case DIR_UP -> bulletY-=RADIUS;
            case DIR_DOWN -> bulletY+=RADIUS;
            case DIR_LEFT -> bulletX-=RADIUS;
            case DIR_RIGHT -> bulletX+=RADIUS;
        }
        //从对象池中获取子弹对象
        Bullet bullet = BulletsPool.get();
        //设置子弹的属性
        bullet.setX(bulletX);
        bullet.setY(bulletY);
        bullet.setDir(dir);
        bullet.setAtk(atk);
        bullet.setColor(color);
        bullet.setVisible(true);
        bullets.add(bullet);

        //发射子弹之后，记录时间
        fireTime=System.currentTimeMillis();
        canFire=false;
    }

    /**
     * 将当前坦克的发射的所有子弹绘制出来
     * @param g
     */
    private void drawBullets(Graphics g){
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        //遍历所有的子弹,将不可见的子弹移除，并还原回对象池
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if(!bullet.isVisible()){
                Bullet remove = bullets.remove(i);
                i--;
                BulletsPool.theReturn(remove);
                canFire=true;
            }
        }
    }

    /**
     * 坦克销毁的时候处理坦克的所有子弹
     */
    public void bulletsReturn(){
        for (Bullet bullet : bullets) {
            BulletsPool.theReturn(bullet);
        }
        bullets.clear();
    }
    //坦克和敌人的子弹碰撞的方法
    public void collideBullets(List<Bullet> bullets){
        //遍历所有子弹，依次和当前的坦克进行碰撞的检测
        for (Bullet bullet : bullets) {
            int bulletX=bullet.getX();
            int bulletY=bullet.getY();

            //子弹和坦克碰撞了
            if(MyUtil.isCollide(x,y,RADIUS,bulletX,bulletY)){
                //子弹消失
                bullet.setVisible(false);
                //坦克受到伤害
                hurt(bullet);
                addExplode(bulletX,bulletY);
            }
        }
    }
    private void addExplode(int x,int y){
        //添加爆炸效果
        Explode explode = ExplodesPool.get();
        explode.setX(x);
        explode.setY(y);
        explode.setVisible(true);
        explode.setIndex(0);
        explodes.add(explode);
    }
    private void hurt(Bullet bullet){
        int atk=bullet.getAtk();
        System.out.println("atk = "+atk);
        hp-=atk;
        if(hp<=0) {
            hp = 0;
            die();
        }
    }

    //坦克死亡需要处理的内容 TODO
    private void die(){
        //敌人
        if(isEnemy){
            //TODO 敌人坦克被消灭了 归还对象池
            EnemyTanksPool.theReturn(this);
        }
        //GG TODO
        else {
            delaySecondsToOver(1000);
        }
    }

    /**
     * 判断当前坦克是否死亡
     * @return
     */
    public boolean isDie(){
        return hp<=0;
    }
    /**
     * 绘制当前坦克上的所有的爆炸的效果
     * @param g
     */
    public void drawExplodes(Graphics g){
        for (Explode explode : explodes) {
            explode.draw(g);
        }
        //将不可见的爆炸效果删除，还给对象池
        for (int i = 0; i < explodes.size(); i++) {
            Explode explode=explodes.get(i);
            if(!explode.isVisible()){
                Explode remove = explodes.remove(i);
                ExplodesPool.theReturn(remove);
                i--;
            }
        }
    }

    //内部类，来表示坦克的血条
    class BloodBar{
        public static final int BAR_LENGTH=RADIUS*2;
        public static final int BAR_HEIGHT=5;

        public void draw(Graphics g){
            //填充底色
            g.setColor(Color.YELLOW);
            g.fillRect(x-RADIUS,y-RADIUS-BAR_HEIGHT*2,BAR_LENGTH,BAR_HEIGHT);
            //红色的当前血量
            g.setColor(Color.RED);
            g.fillRect(x-RADIUS,y-RADIUS-BAR_HEIGHT*2,hp*BAR_LENGTH/DEFAULT_HP,BAR_HEIGHT);
            //蓝色的边框
            g.setColor(Color.WHITE);
            g.drawRect(x-RADIUS,y-RADIUS-BAR_HEIGHT*2,BAR_LENGTH,BAR_HEIGHT);
        }
    }

    //坦克的子弹和地图块的碰撞
    public void bulletCollideMapTiles(List<MapTile> tiles){
        for (MapTile tile : tiles) {
            if(tile.isCollideBullet(bullets)){
                //添加爆炸效果
                addExplode(tile.getX()+MapTile.radius, tile.getY()+MapTile.radius);
                //设置地图块销毁
                tile.setVisible(false);
                //归还对象池
                MapTilePool.theReturn(tile);
                canFire=true;
                //如果老巢被销毁 一秒后切换到游戏结束的画面
                if(tile.isHouse()){
                    delaySecondsToOver(3000);
                }
            }
        }
    }

    /**
     * 等待若干毫秒后游戏结束
     * @param millisSecond
     */
    private void delaySecondsToOver(int millisSecond){
        new Thread(){
            public void run() {
                try {
                    Thread.sleep(millisSecond);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GameFrame.setGameState(Constant.STATE_OVER);
            }
        }.start();
    }
    /**
     * 从tile中提取8个点，来判断8个点是否有任何一个点和当前坦克有了碰撞
     * @param tiles
     * @return
     */
    public boolean isCollideTile(List<MapTile>tiles){
        for (MapTile tile : tiles) {
            //p1
            int tileX= tile.getX();
            int tileY= tile.getY();
            boolean collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide)return true;
            //p2
            tileX+=MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide)return true;
            //p3
            tileX+=MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide)return true;
            //p4
            tileY+=MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide)return true;
            //p5
            tileY+=MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide)return true;
            //p6
            tileX-=MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide)return true;
            //p7
            tileX-=MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide)return true;
            //p8
            tileY-=MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            if(collide)return true;
        }
        return false;
    }

    /**
     * 坦克回退的方法
     */
    public void back(){
        x=oldX;
        y=oldY;
    }
}
