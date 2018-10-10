package com.justz.dubbo.rpc;

import java.io.Serializable;

/**
 * 方法调用信息
 */
public class Invocation implements Serializable {

    private Class<?> interfaceClass;

    private String methodName;

    private Object[] args;

    private Class[] paramTypes;

    public Invocation(Class<?> interfaceClass, String methodName, Object[] args, Class[] paramTypes) {
        this.interfaceClass = interfaceClass;
        this.methodName = methodName;
        this.args = args;
        this.paramTypes = paramTypes;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }
}
