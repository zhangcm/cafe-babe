package com.justz.jvm.processor;

/**
 * @author zhangcm
 * @since 1.0, 2018/8/4 上午9:25
 */
public class BADLY_NAMED_CODE {

    enum colors {
        red, blue, green;
    }

    static final int _FORTY_TWO = 42;

    public static int NOT_A_CONSTANT = _FORTY_TWO;

    protected void BADLY_NAMED_CODE() {
        return;
    }

    public void NOTcamelCASEmethodNAME() {
        return;
    }
}

/*
 * ➜  java git:(master) ✗ javac com/justz/jvm/processor/NameChecker.java
 * ➜  java git:(master) ✗ javac com/justz/jvm/processor/NameCheckProcessor.java
 * ➜  java git:(master) ✗ javac -processor com.justz.jvm.processor.NameCheckProcessor com/justz/jvm/processor/BADLY_NAMED_CODE.java
 *
 * 警告: 来自注释处理程序 'com.justz.jvm.processor.NameCheckProcessor' 的受支持 source 版本 'RELEASE_6' 低于 -source '1.8'
 * com/justz/jvm/processor/BADLY_NAMED_CODE.java:7: 警告: 名称"BADLY_NAMED_CODE"应当符合驼式命名法(Camel Case Names)
 *  public class BADLY_NAMED_CODE {
 *         ^
 *  com/justz/jvm/processor/BADLY_NAMED_CODE.java:9: 警告: 名称"colors"应当以大写字母开头
 *      enum colors {
 *      ^
 *  com/justz/jvm/processor/BADLY_NAMED_CODE.java:10: 警告: 常量"red"应当全部以大写字母或下划线命名，并且以字母开头
 *          red, blue, green;
 *          ^
 *  com/justz/jvm/processor/BADLY_NAMED_CODE.java:10: 警告: 常量"blue"应当全部以大写字母或下划线命名，并且以字母开头
 *          red, blue, green;
 *               ^
 *  com/justz/jvm/processor/BADLY_NAMED_CODE.java:10: 警告: 常量"green"应当全部以大写字母或下划线命名，并且以字母开头
 *          red, blue, green;
 *                     ^
 *  com/justz/jvm/processor/BADLY_NAMED_CODE.java:13: 警告: 常量"_FORTY_TWO"应当全部以大写字母或下划线命名，并且以字母开头
 *      static final int _FORTY_TWO = 42;
 *                       ^
 *  com/justz/jvm/processor/BADLY_NAMED_CODE.java:15: 警告: 名称"NOT_A_CONSTANT"应当以小写字母开头
 *      public static int NOT_A_CONSTANT = _FORTY_TWO;
 *                        ^
 *  com/justz/jvm/processor/BADLY_NAMED_CODE.java:17: 警告: 一个普通方法"BADLY_NAMED_CODE"不应当与类名重复，避免与构造函数产生混淆
 *      protected void BADLY_NAMED_CODE() {
 *                     ^
 *  com/justz/jvm/processor/BADLY_NAMED_CODE.java:17: 警告: 名称"BADLY_NAMED_CODE"应当以小写字母开头
 *      protected void BADLY_NAMED_CODE() {
 *                     ^
 *  com/justz/jvm/processor/BADLY_NAMED_CODE.java:21: 警告: 名称"NOTcamelCASEmethodNAME"应当以小写字母开头
 *      public void NOTcamelCASEmethodNAME() {
 *                  ^
 */
