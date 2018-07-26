package org.apache.dubbo.demo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangcm
 * @since 1.0, 2018/7/17 下午4:25
 */
public interface DemoService {

    Person sayHello(String name);

    List<Person> queryList(String name);

    int getInt(String name);

    BigDecimal getBigDecimal(String name);

    ListResponse<Person> queryResponse(String name);
}
