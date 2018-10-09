package com.cetc.datasynch.config;

public interface CommonInstance {

    /** Elasticsearch ip */
    String ElasticsearchIp = "192.168.16.220";

    /** Elasticsearch Client Port */
    String ElasticsearchPort = "9200";

    /** Server ip */
    String ServerIp = "192.168.16.173";

    /** Server Port */
    String ServerPort = "8080";

    /** Index alias*
     *  多个索引映射到一个别名：用于全域搜索中指定index集合进行搜索，缩减搜索范围
     *  包含的索引：
     "binanchangsuo_001@31project_april",
     "dixiandian_001@31project_april",
     "gongchengjianzhu_001@31project_april",
     "haidi_001@31project_april",
     "hedao_001@31project_april",
     "hedianzhan_001@31project_april",
     "huapodian_001@31project_april",
     "jianzhuwu_001@31project_april",
     "jilaodian_001@31project_april",
     "jilaozaiqing_001@31project_april",
     "qiangxianduiwu_001@31project_april",
     "ranqizhan_001@31project_april",
     "sanfangwuzi_001@31project_april",
     "shuiku_001@31project_april",
     "shuizha_001@31project_april",
     "weihuadian_001@31project_april",
     "xiaohuoshuan_001@31project_april",
     "xiaofangzhan_001@31project_april",
     "youku_001@31project_april",
     "youqizhan_001@31project_april",
     "zhongdaweixianyuan_001@31project_april"
     *
     * */
    String Alias = "/global_map_search";


    String ShenzhenGISURL = "http://192.168.37.134:8080/ConvertCoord/servlet/local2wgs";

}
