package com.cetc.cloud.datasynch.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SanxiaoModel {

    private String id;
    private String status;

    public SanxiaoModel(String id, String status) {
        this.id = id;
        this.status = status;
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