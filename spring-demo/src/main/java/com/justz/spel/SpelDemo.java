package com.justz.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * spel用法示例
 */
public class SpelDemo {

    private static SpelExpressionParser parser = new SpelExpressionParser();

    private static EvaluationContext context = new StandardEvaluationContext();

    private static void parseText() {
        System.out.println(parser.parseExpression("'hello'").getValue(String.class));    // 注意单引号
        System.out.println(parser.parseExpression("\"hello\"").getValue(String.class));  // 转义双引号
        System.out.println(parser.parseExpression("1.024E+3").getValue(Long.class));
        System.out.println(parser.parseExpression("0xFFFF").getValue(Integer.class));
        System.out.println(parser.parseExpression("true").getValue(Boolean.class));
        System.out.println(parser.parseExpression("null").getValue());
    }

    private static void parseVariable() {
        String name = "Tom";
        context.setVariable("myName", name);
        System.out.println(parser.parseExpression("#myName").getValue(context, String.class));
    }

    private static void parseFieldAndMethod() {
        Person person = new Person("Tom", 18);
        context.setVariable("person", person);
        System.out.println(parser.parseExpression("#person.name").getValue(context, String.class));
        System.out.println(parser.parseExpression("#person.Name").getValue(context, String.class));
        System.out.println(parser.parseExpression("#person.getName()").getValue(context, String.class));
        System.out.println(parser.parseExpression("#person.getAge()").getValue(context, String.class));
        System.out.println(parser.parseExpression("#person.getAge()").getValue(context, Integer.class));

        List<String> list = Collections.singletonList("a");
        context.setVariable("list", list);
        System.out.println(parser.parseExpression("#list[0]").getValue(context, String.class));

        Map<String, String> map = Collections.singletonMap("A", "1");
        context.setVariable("map", map);
        System.out.println(parser.parseExpression("#map[A]").getValue(context, String.class));

        System.out.println(parser.parseExpression("{'A', 'B', 'C'}[1]").getValue(String.class));
        System.out.println(parser.parseExpression("{'A': '1', 'B': '2'}[A]").getValue(String.class));
    }

    /**
     * T操作符可以获取类型
     */
    private static void parseType() {
        System.out.println(parser.parseExpression("T(java.util.Date)").getValue(Class.class));
    }

    /**
     * 调用对象的静态方法
     */
    private static void callStaticMethod() {
        System.out.println(parser.parseExpression("T(Math).abs(-1)").getValue(Integer.class));
    }

    /**
     * 类型判断
     */
    private static void instanceOf() {
        System.out.println(parser.parseExpression("'asdf' instanceof T(String)").getValue(Boolean.class));
    }

    /**
     * 操作符
     * 关系操作符, 包括: eq(==), ne(!=), lt(<), le(<=), gt(>), ge(>=)
     * 逻辑运算符, 包括: and(&&), or(||), not(!)
     * 数学操作符, 包括: 加(+), 减(-), 乘(*), 除(/), 取模(%), 幂指数(^)
     * 其他操作符, 如:三元操作符, instanceof, 赋值(=), 正则匹配
     *
     * 三元操作符有个特殊的用法，一般用于赋默认值
     *
     */
    private static void parseOperate() {
        System.out.println(parser.parseExpression("1 > -1").getValue(Boolean.class));
        System.out.println(parser.parseExpression("1 gt -1").getValue(Boolean.class));
        System.out.println(parser.parseExpression("true or true").getValue(Boolean.class));
        System.out.println(parser.parseExpression("true || true").getValue(Boolean.class));
        System.out.println(parser.parseExpression("2 ^ 3").getValue(Integer.class));
        System.out.println(parser.parseExpression("true ? true : false").getValue(Boolean.class));
        System.out.println(parser.parseExpression("#name ?: 'default'").getValue(context, String.class));
        System.out.println(parser.parseExpression("1 instanceof T(Integer)").getValue(Boolean.class));
        System.out.println(parser.parseExpression("'5.00' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class));
        // 使用问号避免空指针
        System.out.println(parser.parseExpression("#name?.toUpperCase()").getValue(context, String.class));
    }

    /**
     * 集合选择
     * ?[expression]: 选择符合条件的元素
     * ^[expression]: 选择符合条件的第一个元素
     * $[expression]: 选择符合条件的最后一个元素
     * ![expression]: 可对集合中的元素挨个进行处理
     */
    private static void collectionSelect() {
        System.out.println(parser.parseExpression("{1, 3, 5, 7}.?[#this > 3]").getValue());
        System.out.println(parser.parseExpression("{1, 3, 5, 7}.^[#this > 3]").getValue());
        System.out.println(parser.parseExpression("{1, 3, 5, 7}.$[#this > 3]").getValue());
        System.out.println(parser.parseExpression("{1, 3, 5, 7}.![#this + 1]").getValue());

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");
        map.put(4, "D");
        context.setVariable("map", map);
        System.out.println(parser.parseExpression("#map.?[key > 3]").getValue(context));
        System.out.println(parser.parseExpression("#map.?[value == 'A']").getValue(context));
        System.out.println(parser.parseExpression("#map.?[key > 2 and key < 4]").getValue(context));
    }

    private static void parseTemplate() {
        Person person = new Person("Tom", 18);
        context.setVariable("person", person);

        System.out.println(parser.parseExpression("他的名字为#{#person.name}", new TemplateParserContext()).getValue(context));
    }

    public static void main(String[] args) {
//        parseText();
//        parseVariable();
//        parseFieldAndMethod();
//        parseType();
//        callStaticMethod();
//        instanceOf();
//        parseOperate();
//        collectionSelect();
        parseTemplate();
    }
}
