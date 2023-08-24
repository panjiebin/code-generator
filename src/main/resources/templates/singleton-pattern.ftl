<#-- 单例模式 代码模板 -->
package ${pkg};

/**
* ${description}
* @author ${author}
*/
public class ${name} {

    public static ${name} getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static ${name} INSTANCE = new ${name}();
    }
}