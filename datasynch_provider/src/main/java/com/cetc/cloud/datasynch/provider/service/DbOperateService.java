package com.cetc.cloud.datasynch.provider.service;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Description：
 * Created by luolinjie on 2018/10/19.
 */
public interface DbOperateService {

    boolean checkIfTableExists(String tbName);
}
