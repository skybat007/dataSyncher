package com.cetc.cloud.datasynch.provider.core.tools;

import java.util.HashMap;
import java.util.List;

/**
 * 空气质量计算公式
 * Created by Administrator on 2018/12/26.
 */
public class AQICalcTools {

    private static final HashMap<String, Integer> typeMapping = new HashMap<>();

    static {
        typeMapping.put("a34004", 1);
        typeMapping.put("a34002", 2);
        typeMapping.put("a21029", 3);
        typeMapping.put("a21026", 4);
        typeMapping.put("a21004", 5);
    }

    public static long getFinalAQIVal(List<List> hourlyValueSet) {
        long resAQI = 0;
        for (List singleFactor_value : hourlyValueSet) {
            String factorTypecode = (String) singleFactor_value.get(0);
            String value = (String) singleFactor_value.get(1);
            Integer mappingcode = typeMapping.get(factorTypecode);
            if (mappingcode != null) {
                long iAqi = getIAqi(Integer.valueOf(value), mappingcode);
                if (iAqi > resAQI) {
                    resAQI = iAqi;
                }
            }
        }
        return resAQI;
    }


    public static long getIAqi(int inputNum, int type) {
        //% PM10, 24-hr average, ug/m3
        final int[] pm10_24h = {0, 50, 150, 250, 350, 420, 500, 600};
        //% PM2.5, 24-hr average, ug/m3
        final int[] pm2_5_24h = {0, 35, 75, 115, 150, 250, 350, 500};
        //% O3,  8-hr average, ug/m3
        final int[] O3_8h = {0, 100, 160, 215, 265, 800, 1000, 1200};
        //% O3,  1-hr average, ug/m3
        final int[] O3_1h = {0, 160, 200, 300, 400, 800, 1000, 1200};
        //% NO2, 24-hr average, ug/m3
        final int[] NO2_24h = {0, 40, 80, 180, 280, 565, 750, 940};
        //% NO2, 1-hr average, ug/m3
        final int[] NO2_1h = {0, 100, 200, 700, 1200, 2340, 3090, 3840};
        // % SO2, 24-hr average, ug/m3
        final int[] SO2_24h = {0, 50, 150, 475, 800, 1600, 2100, 2620};
        // % SO2, 1-hr average, ug/m3
        final int[] SO2_1h = {0, 150, 500, 650, 800, 1600, 2100, 2620};
        //% CO,  24-hr average, mg/m3
        final int[] CO_24h = {0, 2, 4, 14, 24, 36, 48, 60};
        // % CO,  1-hr average, mg/m3
        final int[] CO_1h = {0, 5, 10, 35, 60, 90, 120, 150};
/*        PM2.5	a34004  1
//        PM10	a34002  2
//        O3臭氧	a21029  3
//        二氧化硫	a21026  4
//        二氧化氮	a21004  5
*/
        long res;
        switch (type) {
            case 1:
                res = getSingleAQIVal(inputNum, pm2_5_24h);
                break;
            case 2:
                res = getSingleAQIVal(inputNum, pm10_24h);
                break;
            case 3:
                res = getSingleAQIVal(inputNum, O3_1h);
                break;
            case 4:
                res = getSingleAQIVal(inputNum, SO2_1h);
                break;
            case 5:
                res = getSingleAQIVal(inputNum, NO2_1h);
                break;
            case 6:
                res = getSingleAQIVal(inputNum, CO_1h);
                break;
            case 7:
                res = getSingleAQIVal(inputNum, CO_24h);
                break;
            case 8:
                res = getSingleAQIVal(inputNum, NO2_1h);
                break;
            case 9:
                res = getSingleAQIVal(inputNum, SO2_1h);
                break;
            case 10:
                res = getSingleAQIVal(inputNum, CO_1h);
                break;
            default:
                return -1;
        }
        return res;
    }

    //获取单个类型的AQI转换值
    private static long getSingleAQIVal(int inputNum, int[] standardArr) {
        final int[] iaqi = {0, 50, 100, 150, 200, 300, 400, 500};
        //aqi_slope = (IAQI_hi - IAQI_lo)/(BP_hi - BP_lo)
        Double aqi_val = 0.0;
        int startIndex = getStartIndex(inputNum, standardArr);
        if (startIndex >= 0 && startIndex < standardArr.length - 1) {
            aqi_val = ((iaqi[startIndex + 1] - iaqi[startIndex]) * (inputNum - standardArr[startIndex]) / ((standardArr[startIndex + 1] - standardArr[startIndex]) * 1.0)) + iaqi[startIndex] * 1.0;

        } else {
            aqi_val = 501.0;
        }
        return Math.round(aqi_val);
    }

    private static int getStartIndex(int inputNum, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (inputNum >= arr[i]) {
                return i;
            }
        }
        return arr.length;
    }
}
