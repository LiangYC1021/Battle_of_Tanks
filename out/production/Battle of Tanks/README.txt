1.对游戏的窗口进行初始化，设置标题，大小，坐标，窗口的事件的监听
2.游戏菜单的显示，以及游戏菜单的按键的控制
游戏被分成了若干个游戏的状态： gameState
游戏菜单
游戏帮助
游戏关于
游戏中
游戏结束
在不同的状态下，绘制不同的内容，按键有不同的处理方式
3.调整整个游戏窗口的重绘的机制。
要每秒固定的刷新我们的窗口。fps==33帧
每隔33ms 刷新一次（对整个窗口进行重绘）。repaint()。
单独启动一个线程用于窗口的重绘。
在P5视频中画出了坦克以及可以调整方向。

4.坦克类的定义和绘制

5.坦克的四向行走，以及边界的控制

6.坦克发射子弹

7.坦克的绘制使用图片

8.解决屏幕闪烁的问题  双缓冲解决

9.子弹的效率问题
解决方案：使用对象池来解决：提前创建好若干个子弹对象放到一个容器中。
需要的时候从对象池中拿一个出来使用。当子弹需要被销毁的时候，放回到原来的对象池中。

10.敌人坦克的控制
11.随机的在屏幕的左上角和右上角产生敌人的坦克
12.坦克分成相关的类：父类Tank，子类MyTank、EnemyTank
13.敌人坦克AI：每隔5秒让敌人坦克随机获得一个状态（站立或行走）
   敌人发射子弹的AI：游戏的每一帧都随机（5%概率）判断敌人是否发射子弹

14.添加爆炸类，爆炸效果的控制
15.使用对象池来管理爆炸对象

16.添加坦克名字和血条

17.敌人坦克的死亡处理（对象池管理）
18.我方坦克死亡的处理，切换游戏状态，游戏结束。
提供两个选项：回到主菜单或退出游戏(重置游戏数据，开始新游戏)

19.地图  地图元素类
设计：上、左、右距离窗口的宽度为1.5倍的坦克宽度。

21.实现一张地图的绘制显示

22.优化了地图的随机初始化。子弹和地图的碰撞

23.坦克和地图的碰撞

24.敌人坦克和地图的碰撞

25.玩家的老巢，老巢被击毁，游戏结束（延迟若干秒）

26.控制玩家发射炮弹的间隔

27.添加其他种类的地图元素
不可击毁的元素
遮挡物
河流

28.添加不同的地图种类

29.添加关卡
关卡的设计：
    1：敌人坦克的数量
    2：地图元素块的不同的摆放方式
    3：敌人坦克的类型不同（随着关卡的增加，难度增大）
    4：使用简单的文本来实现关卡的设计（每一关对应一个文本）

30.尝试添加背景音乐和音效
