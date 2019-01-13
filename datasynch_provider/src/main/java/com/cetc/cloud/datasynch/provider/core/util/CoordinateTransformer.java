package com.cetc.cloud.datasynch.provider.core.util;

/**
 * Description：坐标转换工具
 * Created by luolinjie on 2018/4/18.
 */
public class CoordinateTransformer {

    public static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    public static final double pi = 3.1415926535897932384626;  // π
    public static final double a = 6378245.0;  // 长半轴
    public static final double ee = 0.00669342162296594323; // 偏心率平方


    /**
     * Baidu-->84坐标转换
     */

    public static double[] bd09_to_wgs84(double bd_lon, double bd_lat) {
        double[] res = bd09_to_gcj02(bd_lon, bd_lat);

        return gcj02_to_wgs84(res[0], res[1]);
    }
    /**
     * 84坐标转换-->Baidu
     */
    public static double[] wgs84_to_bd09(double lon, double lat) {
        double[] res = wgs84_to_gcj02(lon, lat);
        return gcj02_to_bd09(res[0], res[1]);
    }

    /**
     * 火星坐标系(GCJ-02)转百度坐标系(BD-09)
     * 谷歌、高德——>百度
     * :param lng:火星坐标经度
     * :param lat:火星坐标纬度
     * :return:
     */
    public static double[] gcj02_to_bd09(double lng, double lat) {

        double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * x_pi);
        double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * x_pi);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;

        double[] coordinates = new double[2];
        coordinates[0] = bd_lng;
        coordinates[1] = bd_lat;
        return coordinates;
    }

    /**
     * 百度坐标系(BD-09)转火星坐标系(GCJ-02)
     * 百度——>谷歌、高德
     * :param bd_lat:百度坐标纬度
     * :param bd_lon:百度坐标经度
     * :return:转换后的坐标列表形式
     */
    public static double[] bd09_to_gcj02(double bd_lon, double bd_lat) {

        double x = bd_lon - 0.0065;
        double y = bd_lat - 0.006;

        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double gg_lng = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);

        return new double[]{gg_lng, gg_lat};
    }

    /**
     * WGS84转GCJ02(火星坐标系)
     * :param lng:WGS84坐标系的经度
     * :param lat:WGS84坐标系的纬度
     * :return:
     */
    public static double[] wgs84_to_gcj02(double lng, double lat) {
        if (out_of_china(lng, lat)) {  // 判断是否在国内
            return new double[]{lng, lat};
        }
        double dlat = _transformlat(lng - 105.0, lat - 35.0);
        double dlng = _transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * pi;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * pi);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * pi);
        double mglat = lat + dlat;
        double mglng = lng + dlng;

        return new double[]{mglng, mglat};
    }

    /**
     * GCJ02(火星坐标系--高德、谷歌)转GPS84
     * :param lng:火星坐标系的经度
     * :param lat:火星坐标系纬度
     * :return:
     */
    public static double[] gcj02_to_wgs84(double lng, double lat) {
        if (out_of_china(lng, lat)) {  // 判断是否在国内
            return new double[]{lng, lat};
        }
        double dlat = _transformlat(lng - 105.0, lat - 35.0);
        double dlng = _transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * pi;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * pi);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * pi);
        double mglat = lat + dlat;
        double mglng = lng + dlng;

        return new double[]{lng * 2 - mglng, lat * 2 - mglat};
    }

    /**
     * 判断是否在国内，不在国内不做偏移
     * :param lng:
     * :param lat:
     * :return:     true:在国内
     *              false：不在国内
     */
    public static boolean out_of_china(double lng, double lat) {
        return !(lng > 73.66 && lng < 135.05 && lat > 3.86 && lat < 53.55);
    }

    public static double _transformlat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * pi) + 20.0 *
                Math.sin(2.0 * lng * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * pi) + 40.0 *
                Math.sin(lat / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * pi) + 320 *
                Math.sin(lat * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double _transformlng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * pi) + 20.0 *
                Math.sin(2.0 * lng * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * pi) + 40.0 *
                Math.sin(lng / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * pi) + 300.0 *
                Math.sin(lng / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }
}
