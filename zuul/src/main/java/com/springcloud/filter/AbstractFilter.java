package com.springcloud.filter;

import com.netflix.zuul.ZuulFilter;
import lombok.RequiredArgsConstructor;

/**
 * @Author: 胡成
 * @Version: 0.0.1V
 * @Date: 2018/6/15
 * @Description: 类描述
 **/
@RequiredArgsConstructor
public abstract class AbstractFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() {
        return null;
    }
}
