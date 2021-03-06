//package com.springcloud.api.config;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.netflix.loadbalancer.ILoadBalancer;
//import com.netflix.loadbalancer.IRule;
//import com.netflix.loadbalancer.Server;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//import java.util.Random;
//
///**
// * @Author: 胡成
// * @Version: 0.0.1V
// * @Date: 2018/6/19
// * @Description: 类描述
// **/
//@Slf4j
//@Configuration
//public class Provider3FeignConfig  implements IRule {
//
//    ILoadBalancer lb;
//
//    @Autowired
//    private RedisCountTools redisCountTools;
//
//    @Override
//    public Server choose(Object o) {
//        List<Server> servers = lb.getAllServers();
//        log.info("全部服务器 --> {}",servers);
//        return getWeightsServerByAppName(servers);
//    }
//
//    /**
//     * @Author: 胡成
//     * @Date: 2018/6/14 16:23
//     * @Description: 根据用户请求的appName去获取redis服务权重信息
//     **/
//    public Server getWeightsServerByAppName(List<Server> servers) {
//        String name = servers.get(0).getMetaInfo().getAppName().toLowerCase();
//        Object redisProvider = redisCountTools.get(name);
//        if (redisProvider == null || "".equals(redisProvider)) {
//            log.info("服务器数量  --> {}",servers.size());
//            int index = new Random().nextInt(servers.size());
//            log.info("选择服务器 --> {}",index);
//            log.info("request --> {} 客户端 --> {}", servers.get(index), name);
//            return servers.get(index);
//        }
//        JSONObject jb = JSONObject.parseObject(String.valueOf(redisProvider));
//        JSONArray ja = jb.getJSONArray("server");
//        double[] weightArrays = new double[ja.size()];
//        int[] ports = new int[jb.size()];
//        for (int i = 0; i < ja.size(); i++) {
//            weightArrays[i] = ja.getJSONObject(i).getDouble("weights");
//            ports[i] = ja.getJSONObject(i).getInteger("port");
//        }
//        int index = WeightRandomTools.getWeightRandom(weightArrays);
//        Server server = getServerByPort(servers, ports[index]);
//        log.info("request --> {} 客户端 --> {}", server, jb.getString("appname"));
//        return server;
//    }
//
//    private Server getServerByPort(List<Server> servers, int port) {
//        for (Server s : servers) {
//            if (s.getPort() == port) {
//                return s;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
//        this.lb = iLoadBalancer;
//    }
//
//    @Override
//    public ILoadBalancer getLoadBalancer() {
//        return this.lb;
//    }
//}
