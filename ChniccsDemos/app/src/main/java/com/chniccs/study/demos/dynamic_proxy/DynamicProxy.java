package com.chniccs.study.demos.dynamic_proxy;

/**
 * Created by chniccs on 16/8/9.
 * 引用自：http://www.iteye.com/topic/683613/
 */

import android.util.Log;

import com.chniccs.study.demos.app.Constants;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 相亲接口
 */
interface XiangQinInterface {
    /**
     * 相亲方法
     */
    public void xiangQin();
}

/**
 * 张三相亲实现类
 */
class ZhangSanXiangQinInterfaceImpl implements XiangQinInterface {
    public void xiangQin() {
        Log.d(Constants.TAG, "张三去相亲，娶个漂亮老婆。");
    }
}


/**
 * 相亲可是一辈子的大事，相亲前要准备一下，打扮得帅气些。
 */
class ReadyInvocationHandler implements InvocationHandler {
    //相亲接口的实现类，也就是张三相亲类
    private Object zhangSan = null;

    public ReadyInvocationHandler(Object realSubject) {
        this.zhangSan = realSubject;
    }

    public Object invoke(Object proxy, Method m, Object[] args) {
        Object result = null;
        try {
            /**
             * 动态代理类$Proxy0调用xiangQin方法时会调用它自己的xiangQin方法，
             * 而它自己的xiangQin方法里面调用的是super.h.invoke(this, , )，也就是父类Proxy的h的invoke方法，
             * 也就是ReadyInvocationHandler类的invoke方法。
             * 所以，invoke(Object proxy, Method m, Object[] args)种的proxy实际上就是动态代理类$Proxy0，
             * 如果你将其强转成XiangQinInterface然后调用它的xiangQin方法，然后它就会调用super.h.invoke(this, , )，这样就会死循环。
             */
            /**
             * 网上关于这里最多问题就是Object proxy放在这里用来做什么呢？这个我也不知道，
             * 不过至少我们知道它到底是个什么东西，具体做什么用嘛就不得而知了
             */
            Log.d(Constants.TAG, proxy.getClass().getSimpleName());
            Log.d(Constants.TAG, "张三相亲前，代理人给他打扮了打扮。");
            result = m.invoke(zhangSan, args);
        } catch (Exception ex) {
            System.exit(1);
        }
        return result;
    }
}

/**
 * 张三来到了婚介所(相亲现场)，开始相亲。
 *
 */
public class DynamicProxy {
    public static void dynamicProxy() {
        //先将张三相亲这个相亲的实现类实例化，也就是得到XiangQinInterface接口的一个实例对象
        XiangQinInterface zhangSan = new ZhangSanXiangQinInterfaceImpl();
        /**
         * 得到ZhangSanXiangQinInterfaceImpl这个类的一个代理类，同时为代理类绑定了一个处理类ReadyInvocationHandler。
         * 听着很绕口，其实就是每次调用ZhangSanXiangQinInterfaceImpl这个子类的xiangQin方法时，
         * 不是zhangSan这个ZhangSanXiangQinInterfaceImpl类的实例去调用，
         * 而是这个ZhangSanXiangQinInterfaceImpl的代理类ReadyInvocationHandler去调用它自己的invoke方法,
         * 这个invoke方法里呢可以调用zhangSan这个实例的xiangQin方法
         */
        /**
         * 在java种怎样实现动态代理呢
         * 第一步，我们要有一个接口，还要有一个接口的实现类，而这个实现类呢就是我们要代理的对象，
         * 所谓代理呢也就是在调用实现类的方法时，可以在方法执行前后做额外的工作，这个就是代理。
         * 第二步，我们要自己写一个在要代理类的方法执行时，能够做额外工作的类，而这个类必须继承InvocationHandler接口，
         * 为什么要继承它呢？因为代理类的实例在调用实现类的方法的时候，不会调真正的实现类的这个方法，
         * 而是转而调用这个类的invoke方法（继承时必须实现的方法），在这个方法中你可以调用真正的实现类的这个方法。
         * 第三步，在要用代理类的实例去调用实现类的方法的时候，写出下面两段代码。
         */
        XiangQinInterface proxy = (XiangQinInterface) Proxy.newProxyInstance(
                zhangSan.getClass().getClassLoader(),
                zhangSan.getClass().getInterfaces(),
                new ReadyInvocationHandler(zhangSan));
        proxy.xiangQin();
        /**
         * 这里要解释下中部那段长长的代码的意思，以及具体做了哪些工作？
         * 第一，根据zhangSan.getClass().getClassLoader()这个要代理类的类加载器和
         * zhangSan.getClass().getInterfaces()要代理类所实现的所有的接口
         * 作为参数调用Proxy.getProxyClass(ClassLoader loader, Class<?>... interfaces)
         * 的方法返回代理类的java.lang.Class对象，也就是得到了java动态生成的代理类$Proxy0的Class对象。
         * 同时，java还让这个动态生成的$Proxy0类实现了要代理类的实现的所有接口，并继承了Proxy接口。
         * 第二，实例化这个动态生成的$Proxy0类的一个实例，实例化代理类的构造函数为Proxy(InvocationHandler h)，
         * 也就是说要实例化这个动态生成的$Proxy0类，必须给它一个InvocationHandler参数，也就是我们自己实现的用来在代理类
         * 方法执行前后做额外工作的类ReadyInvocationHandler。
         * 这段代码Proxy.newProxyInstance(zhangSan.getClass().getClassLoader(),zhangSan.getClass().getInterfaces(),new ReadyInvocationHandler(zhangSan))
         * 得到的其实是一个类名叫$Proxy0 extends Proxy implements XiangQinInterface的类。
         * 第三，将这个$Proxy0类强制转型成XiangQinInterface类型，调用xiangQin方法。
         */
    }
    public static void nonProxy(){
        ZhangSanXiangQinInterfaceImpl zhangSanXiangQinInterface=new ZhangSanXiangQinInterfaceImpl();
        zhangSanXiangQinInterface.xiangQin();
    }
}
