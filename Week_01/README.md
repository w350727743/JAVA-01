##类加载相关笔记

### 类的生命周期
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210117141538378.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwOTExNDA0,size_16,color_FFFFFF,t_70)
类在jvm中的生命周期：加载、验证、准备、解析、初始化、使用、卸载
类加载的：加载、验证、准备、解析、初始化

- **加载**：加载class文件的二进制字节流

- **验证**：也可以叫校验，校验class文件的合法性，是否符合jvm的要求。校验class的语义，版本号等
- **准备**：创建静态字段，并将其初始化为标准默认值（例如null或0值），并分配方法变量表，即在方法区中分配这些变量使用的内存空间（常量池在方法区中），准备阶段还未执行任何java代码。
>例如：public static int  a = 1;在准备阶段会将a初始化默认值为0,初始化的时候才赋值为1.
如果是public static final int a = 1,常量那么在准备阶段就直接赋值为1了

- **解析**：将class文件中的符号引用，解析并链接为直接引用（我个人的理解是：例如一个变量引用某个对象时，class文件中的变量只是一个符号，在解析阶段需要将这个符号和一个实际的对象内存地址关联起来）
- **初始化**：初始化过程包括：执行类的构造方法，static静态字段赋值（上面有讲到准备阶段只是赋了默认值）；static静态代码块执行

### 类加载的时机：
类的初始化触发时机：
- 当虚拟机启动时，初始化用户指定的主类，就是启动执行的 main方法所在的类；
- 当遇到用以新建目标类实例的 new 指令时，初始化 new 指令的目标类，就是new一个类的时候要初始化；
- 当遇到调用静态方法的指令时，初始化该静态方法所在的类；
- 当遇到访问静态字段的指令时，初始化该静态字段所在的类；
- 子类的初始化会触发父类的初始化；
- 如果一个接口定义了 default 方法，那么直接实现或者间接实现该接口的类的初
- 始化，会触发该接口的初始化；
- 使用反射 API 对某个类进行反射调用时，初始化这个类，其实跟前面一样，反射调用要么是已经有实例了，要么是静态方法，都需要初始化；
- 当初次调用 MethodHandle 实例时，初始化该 MethodHandle 指向的方法所在的类

以下情况不会执行类的初始化：
- 通过子类引用父类的静态字段，只会触发父类的初始化，而不会触发子类的初始化。
- 定义对象数组，不会触发该类的初始化。
- 常量在编译期间会存入调用类的常量池中，本质上并没有直接引用定义常量的类，不会触发定义常量所在的类。
- 通过类名获取Class对象，不会触发类的初始化，Hello.class不会让Hello类初始化。
- 通过Class.forName加载指定类时，如果指定参数initialize为false时，也不会触发
- 类初始化，其实这个参数是告诉虚拟机，是否要对类进行初始化Class.forName(“jvm.Hello”)默认会加载Hello类。
- 通过ClassLoader默认的loadClass方法，也不会触发初始化动作（加载了，但是不初始化）。

### 类加载器机制
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210117145351210.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwOTExNDA0,size_16,color_FFFFFF,t_70)
系统自带类加载器三个：BootstrapClassLoader、ExtClassLoader、AppClassLoader。
- BootstrapClassLoader：为启动类加载器，主要加载jre/lib/rt.jar，rt.jar里的类是java的核心类，是C++代码实现的
- ExtClassLoader：为扩展类加载器，它负责加载JRE的扩展目录，lib/ext或者由java.ext.dirs系统属性指定的目录中的JAR包的类
- AppClassLoader：为应用类加载器，他负责在jvm启动时加载来自Java命令的classpath或者­cp选项、java.class.path系统属性指定的jar包和类路径。(如果没有特别指定，则在没有使用自定义类加载器情况下，用户自定义的类都由此加载器加载)

类加载机制三个特点：
1. 双亲委托：当一个自定义类加载器需要加载一个类，比如java.lang.String，它很懒，不会一上来就直接试图加载它，而是先委托自己的父加载器去加载，父加载器如果发现自己还有父加载器，会一直往前找，这样只要上级加载器，比如启动类加载器已经加载了某个类比如java.lang.String，所有的子加载器都不需要自己加载了。如果到启动类加载器都没有加载过该类，那么会自上而下尝试加载该类，如果几个类加载器都没能加载到指定名称的类，那么会抛出ClassNotFountException异常。
2. 负责依赖：如果一个加载器在加载某个类的时候，发现这个类依赖于另外几个类或接口，也会去尝试加载这些依赖项。
3. 缓存加载：为了提升加载效率，消除重复加载，一旦某个类被一个类加载器加载，那么它会缓存这个加载结果，不会重复加载

### 自定义类加载器：
 继承ClassLoader类，重写findClass方法

---
## jvm内存模型
