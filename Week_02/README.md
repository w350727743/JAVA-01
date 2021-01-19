##学习笔记
**GC日志打印相关**
|参数|说明|
|--|--|
|-XX:PrintGCDetails| 开启gc日志打印
|-Xloggc:{path}/gc.log| 输出gc日志到指定路径文件
|-Xloggc:{path}/gc.%t.log| java8以后可以用%p,%t占位符来指定gc输出文件，%p:进程pid;%t:启动时间戳
|-XX:+UseGCLogFileRotation| 启动日志循环打印
|-XX:NumberOfGClogFiles=5| 会滚动覆盖旧的gc日志，只保留最新的5个
|-XX:GCLogFileSize = 2M |设置每个GC日志最大为2M
|-XX:+PrintGCDateStamps| 增加具体时间输出

**垃圾收集器：**
参数     | 说明
-------- | -----
-XX:+UseSerialGC  | 开启串行垃圾收集器
-XX:+UseParallelGC  | 开启并行垃圾收集器
-XX:+UseConcMarkSweepGC  | 开启CMS垃圾收集器
-XX:+UseG1GC|开启G1垃圾收集器

并行：多个线程并行进行垃圾收集，用户线程会暂停
并发：用户线程和垃圾收集线程交替运行，用户线程不会停止

**-XX:+UseSerialGC（串行垃圾收集器）**
 串行垃圾收集器，client的模式jvm下默认的垃圾收集器，单线程做垃圾收集，会触发全线`STW`，停止所有用户线程。年轻代使用`mar-copy（标记-复制）`算法，老年代使用`mark-sweep-compact(标记-清除-整理)`算法

**-XX:+UseParallelGC（并行垃圾收集器）**
并行垃圾收集器，吞吐量优先，垃圾收集时候用户线程暂停，多个线程同时并行进行垃圾收集来减少GC时间。年轻代使用`mar-copy（标记-复制）`算法，老年代使用`mark-sweep-compact(标记-清除-整理)`算法。
可以通过命令行参数`-XX:ParallelGCThreads=N`来指定 GC 线程数， 其默认值为CPU核心数。

**-XX:+UseConcMarkSweepGC （CMS垃圾收集器）**
使用CMS，那么`年轻代默认使用UseParNewGC收集器`，老年代使用CMS。CMS是并发垃圾收集器，垃圾除了初始标记和重新标记阶段，其他阶段垃圾线程都是和用户线程并发得在执行。

两个特点，来避免老年代垃圾收集时候出现长时间停顿：
 - 不对老年代进行整理，而是使用空闲列表(free-lists)来管理内存空间的回收
-  在 mark-and-sweep (标记-清除) 阶段的大部分工作和应用线程一起并发执行

CMS垃圾回收得几个阶段:
1. 初始标记（标记GCRoot和GCRoot直接引用的对象，此过程`Stop-the-world`）
2. 并发标记（遍历老年代，标记所有存活对象，从初始标记的对象往下可达性标记）
3. 并发预清理（处理应用发生变化的对象，如果在并发标记过程中引用关系发生了变化，JVM会通过“ Card（卡片） ”的方式将发生了改变的区域标记为“脏”区，脏区所引用的对象也会被标记为可达对象）
4. 最终标记（此过程`Stop-the-world`,防止引用关系发生变化）
5. 并发清除（清除所有不再使用的对象，回收内存）
6. 并发重置（重置CMS算法相关内部数据，为下一次GC做准备）

**-XX:+UseG1GC（G1垃圾收集器）** 
> G1适合大内存，需要较低的延迟场景.。一般超过8G以上的大堆比较建议使用G1

G1垃圾收集器最大特点是，堆空间不再是划分为整块的young区、old区而是划分为许多的Region(默认是2048块)。然后一部分region组成end区，一部分组合成old区，一部分组合成Survive区。这样划分之后，使得 G1不必每次都去收集整个堆空间，而是以增量的方式来进行处理: 每次只处理一部分内
存块，称为此次GC的回收集(collection set)。
>垃圾最多的小块会被优先收集

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210119203227571.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwOTExNDA0,size_16,color_FFFFFF,t_70)
上图还省略了一个H区（大对象区），在一个对象大小超过region50%时候，会被分配到H区

G1有几个很重要的参数：

- `-XX:+UseG1GC` :启用G1 GC；
- `-XX:+InitiatingHeapOccupancyPercent` :决定什么情况下发生G1 GC；
- `-XX:MaxGCPauseMills` :期望每次GC暂定的时间，比如我们设置为50，则G1 GC会通过调节每次GC的操作时间，尽量让每次系统的GC停顿都在50上下浮动。如果某次GC时间超过50ms，比如说100ms，那么系统会自动在后面动态调整GC行为，围绕50毫秒浮动。

mixed GC:并发标记完成之后，G1将执行一次mixed GC，就是不只清理年轻代，还将一部分老年代区域也加入到 回收集 中。

full GC:G1里如果发生了full GC，那么会退化为串行GC,停顿时间达到秒级，我们尽可能要避免这种情况的发生。

- 并发模式失败：G1启动标记周期，但在Mix GC之前，老年代就被填满，这时候G1会放弃标记周期。这种情形下，需要增加堆大小，或者调整周期（例如增加线程数-XX:ConcGCThreads等）。
- 晋升失败或者疏散失败：G1在进行GC的时候没有足够的内存供存活对象或晋升对象使用，由此触发了Full GC。可以在日志中看到(to-space exhausted)或者（to-space overflow）。解决这种问题的方式是：
a,增加 -XX:G1ReservePercent 选项的值（并相应增加总的堆大小），为“目标空间”增加预留内存量。
b,通过减少 -XX:InitiatingHeapOccupancyPercent 提前启动标记周期。
c,也可以通过增加 -XX:ConcGCThreads 选项的值来增加并行标记线程的数目。
- 巨型对象分配失败：当巨型对象找不到合适的空间进行分配时，就会启动Full GC，来释放空间。这种情况下，应该避免分配大量的巨型对象，增加内存或者增大-XX:G1HeapRegionSize，使巨型对象不再是巨型对象。


**总结**
具体使用什么垃圾收集器看实际情况而定，例如：
- 如果系统考虑吞吐优先，CPU资源都用来最大程度处理业务，用Parallel GC
- 如果系统考虑低延迟有限，每次GC时间尽量短，用CMS GC；
- 如果系统内存堆较大，同时希望整体来看平均GC时间可控，使用G1 GC
- 如果可以使用jdk11，大堆，又要求GC停顿时间很短，可以使用ZGC
- 多核的年代，Serial串行收集器已经不建议使用了