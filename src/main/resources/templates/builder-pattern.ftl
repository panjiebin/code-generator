<#-- 建造者模式 代码模板 -->
package ${pkg};

<#list  imports as impt>
import ${impt};
</#list>

/**
* ${description}
* @author ${author}
*/
public class ${name} {
<#list  fields as field>
    private final ${field.type} ${field.name};
</#list>

    private ${name}(Builder builder) {
    <#list  fields as field>
        this.${field.name} = builder.${field.name};
    </#list>
    }

<#list  fields as field>
    public ${field.type} get${field.name ? cap_first}() {
        return this.${field.name};
    }

</#list>
    public static Builder builder() {
        return new Builder();
    }

    <#-- 内部类Builder -->
    public static class Builder {
    <#list  fields as field>
        private ${field.type} ${field.name};
    </#list>

        private Builder() {
        }

    <#list  fields as field>
        public Builder ${field.name}(${field.type} ${field.name}) {
            this.${field.name} = ${field.name};
            return this;
        }

    </#list>
        public ${name} build() {
            return new ${name}(this);
        }
    }
}