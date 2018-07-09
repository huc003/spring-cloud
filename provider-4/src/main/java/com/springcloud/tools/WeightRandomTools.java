package com.springcloud.tools;

/**
 * @Author: 胡成
 * @Version: 0.0.1V
 * @Date: 2018/6/14
 * @Description: 权重计算
 **/
public class WeightRandomTools {

    private static double weightArraySum(double [] weightArrays) {
        double weightSum = 0;
        for (double weightValue : weightArrays) {
            weightSum += weightValue;
        }
        return weightSum;
    }

    public static int getWeightRandom(double [] weightArrays) {
        double weightSum = weightArraySum(weightArrays);
        double stepWeightSum = 0;
        for (int i = 0; i < weightArrays.length; i++) {
            stepWeightSum += weightArrays[i];
            if (Math.random() <= stepWeightSum/weightSum) {
                return i;
            }
        }
        System.out.println("出错误了");
        return -1;
    }
}
