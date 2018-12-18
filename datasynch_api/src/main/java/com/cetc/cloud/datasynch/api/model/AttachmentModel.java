package com.cetc.cloud.datasynch.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * PackageName:   com.cetc.cloud.datasynch.api.model
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/14 18:06
 * Updater:     by luolinjie
 * Update_Date: 2018/12/14
 * Update_Description: luolinjie 补充
 **/
@Data
@NoArgsConstructor
public class AttachmentModel implements Serializable {
    public String object_id;
    public String system_id;
    public String fileid;
    public String biztype;
    public String bizid;
    public String filename;
    public String description;
    public String filetype;
    public String filesize;
    public String filepath;
    public String isimage;
    public String remote;
    public String filegroup;
    public String create_date;

}
