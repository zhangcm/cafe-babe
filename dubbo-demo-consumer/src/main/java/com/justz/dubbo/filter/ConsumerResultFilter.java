package com.justz.dubbo.filter;

import com.alibaba.dubbo.rpc.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangcm
 * @since 1.0, 2018/7/19 上午11:46
 */
public class ConsumerResultFilter implements Filter {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = invoker.invoke(invocation);
        String valueStr = toJsonString(result.getValue());

        Method method = ReflectionUtils.findMethod(invoker.getInterface(),
                invocation.getMethodName(), invocation.getParameterTypes());
        Type genericReturnType = method.getGenericReturnType();

        JavaType javaType = parseJavaType(objectMapper, genericReturnType);
        Object realValue = null;
        try {
            realValue = objectMapper.readValue(valueStr, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result realResult = new RpcResult(realValue);
        ((RpcResult) realResult).setAttachments(result.getAttachments());
        return realResult;
    }

    private <T> List<T> parseArray(String json, Type type) {
        try {
            return objectMapper.readValue(json, new TypeReference<Object>() {
                @Override
                public Type getType() {
                    return type;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T parseObject(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Class<?>> parseGenericType(Type type){
        List<Class<?>> rootList = new ArrayList<Class<?>>();
        if(type instanceof ParameterizedType){
            ParameterizedType pType = (ParameterizedType)type;
            rootList.add((Class<?>)pType.getRawType());
            for(Type at : pType.getActualTypeArguments()){
                List<Class<?>> childList = parseGenericType(at);
                rootList.addAll(childList);
            }
        }else{
            rootList.add((Class<?>)type);
        }
        return rootList;
    }

    public static JavaType parseJavaType(ObjectMapper jsonMapper, Type genericParameterType){
        List<Class<?>> list =  parseGenericType(genericParameterType);
        if(list==null || list.size()==1){
            return jsonMapper.getTypeFactory().constructType(genericParameterType);
        }
        Class<?>[] classes = list.toArray(new Class[list.size()]);
        Class<?>[] paramClasses = new Class[classes.length-1];
        System.arraycopy(classes,1,paramClasses,0,paramClasses.length);
        JavaType javaType = jsonMapper.getTypeFactory().constructParametrizedType(classes[0],classes[0],paramClasses);
        return javaType;
    }
}
