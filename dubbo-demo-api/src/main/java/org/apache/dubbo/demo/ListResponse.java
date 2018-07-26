package org.apache.dubbo.demo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangcm
 * @since 1.0, 2018/7/19 下午3:13
 */
public class ListResponse<T> implements Serializable {

    private List<T> data;

    public ListResponse() {

    }

    public ListResponse(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
