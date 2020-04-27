# ButterKnife

*  一.原有绑定方法，  
   优点：写法简单，易懂  
   缺点：产生大量重复操作冗余高耗能，假如布局再复杂点，绑定的控件再多点，写起来会很烦躁。  
   原理:是从头开始找，遇到有子控件的，就递归接着找，直到找到viewGroup 对象重写findViewTraversal之后，开始遍历查找 
   AppCompatDelegate（委托对象）——>Window——>getDecorVeiw——>ViewGroup  

*  二.反射动态初始化控件 
   优点：可以实现动态创建对象和编译，体现出很大的灵活性 
   缺点：对性能有影响。使用反射基本上是一种解释操作，我们可以告诉JVM，我们希望做什么并且它 满足我们的要求。这类操作总是慢于只直接执行相同的操作。 
   原理：通过java反射机制，获取属性类静态方法 命令必须一致  

*  三.butterKnife注解绑定控件  https://github.com/JakeWharton/butterknife 
    优点： 1.强大的View绑定和Click事件处理功能，简化代码，提升开发效率2.方便的处理Adapter里的ViewHolder绑定问题 3.运行时不会影响APP效率
    缺点：编译的时候久一点  
    原理：注解绑定试图，编译开始解析时候 通过注解处理器收集目标类信息开始扫描，其中ButterView注解信息通过key/value bindingMap 形式保存 
    在BindingSet 中，一个Bindingset 就对应一个使用类，之后通过javaPoet技术process生产java类文件，遍历bindingMap，根据在BindingSet得到JavaFile 
    对象，辅助生成java类代码，同时将类生成字节码文件  


* 1. Original binding method,
Advantages: easy to write, easy to understand
Disadvantages: produce a lot of repetitive operation redundancy high energy consumption, if the layout is more complex, bound to the control more points, will be very annoying to write. 
How it works: start from scratch, recursively continue looking for child controls until you find the viewGroup object and override the findViewTraversal
AppCompatDelegate -- &gt;The Window - & gt;GetDecorVeiw - & gt;ViewGroup
* 2. Reflection dynamic initialization control
Advantages: you can dynamically create objects and compile, showing great flexibility
Disadvantages: impact on performance.Using reflection is basically an interpretive operation where we can tell the JVM what we want to do and it satisfies our requirements.This type of operation is always slower than simply performing the same operation directly. 
Principle: through the Java reflection mechanism, the static method command of the property class must be consistent
* 3. ButterKnife annotation bound controls at https://github.com/JakeWharton/butterknife
Advantages: 1. Powerful View binding and Click event processing function, simplify the code and improve the development efficiency; 2. Convenient to handle the problem of ViewHolder binding in Adapter; 3
Disadvantages: longer to compile
How it works: the annotation binding attempts to collect the target class information by the annotation handler at the beginning of the compilation and the beginning of the scan. The ButterView annotation information is saved as a key/value bindingMap
In the BindingSet, a BindingSet corresponds to a use class, and then the production of Java class files through the javaPoet technology process, traversing the bindingMap, according to the BindingSet JavaFile
Object that assists in generating Java class code while generating the class into a bytecode file
