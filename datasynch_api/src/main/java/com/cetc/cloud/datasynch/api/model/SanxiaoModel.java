package com.cetc.cloud.datasynch.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该类用来比较Model是否需要更新，字段为参考标准，如果对比后不一致则进行更新
 */
@Data
@NoArgsConstructor
public class SanxiaoModel {

    private String id;
    private String status;  //STATUS字段需要定时更新
    private String lddm;    //LDDM字段需要定时更新
    private String yhCount; //YHCOUNT字段需要定时更新

    public SanxiaoModel(String id, String status,String lddm,String yhCount) {
        this.id = id;
        this.status = status;
        this.lddm = lddm;
        this.yhCount = yhCount;
    }

//    @Override
//    public int compareTo(Object o) {
//        if (this.id.equals(((SanxiaoModel) o).getId()) && this.status.equals(((SanxiaoModel) o).getStatus())) {
//            return 0;
//        } else {
//            return 1;
//        }
//    }
}