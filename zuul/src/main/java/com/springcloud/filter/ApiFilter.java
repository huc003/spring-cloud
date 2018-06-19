package com.springcloud.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.springcloud.tools.RedisCountTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

/**
 * @Author: 胡成
 * @Version: 0.0.1V
 * @Date: 2018/6/15
 * @Description: API限流拦截器拦截器
 **/
@Slf4j
@Component
public class ApiFilter extends AbstractFilter {

    @Autowired
    private RedisCountTools redisCountTools;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String requestUri = request.getRequestURI();
        log.info("用户请求地址 -->  {}", request.getRequestURI());
        //从redis获取限流API限制
        String url = requestUri.substring(1, requestUri.length()).replaceAll("/", ".");
        log.info("redis限流值  -->  {}", url);
        Object obj = redisCountTools.get(url);
        if (obj != null) {
            JSONObject jb = JSONObject.parseObject(String.valueOf(obj));
            int ratelimit = jb.getInteger("ratelimit");
            int status = jb.getInteger("status");
            if (status == 0) {
                return null;
            }
            int count = 0;
            if (redisCountTools.get(url + "_count") != null) {
                count = Integer.valueOf(String.valueOf(redisCountTools.get(url + "_count")));
            }
            log.info("API --> {} 是否开启限流 --> {} 限流次数 --> {} 已访问次数 --> {}", requestUri, status, ratelimit, count);
            //并且时间大于等于一分钟
            if (ratelimit == count) {
                throw new ZuulRuntimeException(new ZuulException(TOO_MANY_REQUESTS.toString(),
                        TOO_MANY_REQUESTS.value(), null));
            }
            //请求成功API访问次数+1
            redisCountTools.incr(url + "_count", 1);
        }
        return null;
    }
}
