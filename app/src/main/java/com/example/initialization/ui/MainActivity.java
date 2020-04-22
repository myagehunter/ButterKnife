package com.example.initialization.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.initialization.BaseActivity;
import com.example.initialization.R;
import com.example.initialization.utils.BaseUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *  一.原有绑定方法，
 *   优点：写法简单，易懂
 *   缺点：产生大量重复操作冗余高耗能，假如布局再复杂点，绑定的控件再多点，写起来会很烦躁。
 *   原理:是从头开始找，遇到有子控件的，就递归接着找，直到找到viewGroup 对象重写findViewTraversal之后，开始遍历查找
 *   AppCompatDelegate（委托对象）——>Window——>getDecorVeiw——>ViewGroup
 *
 *
 *    二.反射动态初始化控件
 *   优点：可以实现动态创建对象和编译，体现出很大的灵活性
 *   缺点：对性能有影响。使用反射基本上是一种解释操作，我们可以告诉JVM，我们希望做什么并且它 满足我们的要求。这类操作总是慢于只直接执行相同的操作。
 *   原理：通过java反射机制，获取属性类静态方法 命令必须一致
 *
 *
 *    三.butterKnife注解绑定控件  https://github.com/JakeWharton/butterknife
 *   优点：1.强大的View绑定和Click事件处理功能，简化代码，提升开发效率
 *         2.方便的处理Adapter里的ViewHolder绑定问题
 *        3.运行时不会影响APP效率，使用配置方便
 *        4.代码清晰，可读性强
 *    缺点：编译的时候久一点
 *    原理：注解绑定试图，编译开始解析时候 通过注解处理器收集目标类信息开始扫描，其中ButterView注解信息通过key/value bindingMap 形式保存
 *    在BindingSet 中，一个Bindingset 就对应一个使用类，之后通过javaPoet技术process生产java类文件，遍历bindingMap，根据在BindingSet得到JavaFile
 *    对象，辅助生成java类代码，同时将类生成字节码文件
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    /**原有事件绑定*/
    private Button mbtn_no;

    /**反射控件命名必须一致*/
    private Button mbtn_ok;
    /**绑定类资源事件*/
    private Unbinder unbinder;

    /**butterknife 添加注解绑定控件 BindViews 多控件绑定*/
    @BindView(R.id.mbtn_bind)
    private Button mbtn_bind;

    /**
     * setContentView 加载布局原理
     * 调用setContentView1.实质调用的是window的setContentView方法，window是在Activity的attach方法里创建的，实现类是PhoneWindow
     * 首先创建DecorView 容器,实质就是一个FrameLayout
     * 根据Feature(例如是否有标题栏)选择一个系统布局文件，然后加入到DecorView中。
     * 默认是一个常规的LinearLayout嵌套FrameLayout,而FrameLayout就是我们所熟知的id为content的副布局。
     * 最后通过LayoutInflater将我们自己创建的Activity布局文件加入到刚才那个名为content的FrameLayout中LayoutInflater.from(mContext).inflate(resId, contentParent);
     * window.setContentView——>创建容器DecorView——>加入系统布局文件（样式，状态栏是否显示）——>最后通过LayoutInflater将布局文件加入系统布局里
     * 层层嵌套
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**通过java反射动态初始化控件**/
        BaseUtil.smartInject(this);
        /**通过findviewById 绑定控件**/
        init();
        unbinder= ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    private  void init(){
       mbtn_no=(Button)findViewById(R.id.mbtn_yes);
       mbtn_no.setOnClickListener(this);
   }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mbtn_yes:
                Toast.makeText(this, "原有初始化控件绑定", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mbtn_ok:
                Toast.makeText(this, "通过反射动态控件绑定", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mbtn_bind:
                Toast.makeText(this, "通过ButterKnife注解绑定控件", Toast.LENGTH_SHORT).show();
                break;
           default:break;
        }
    }
}
