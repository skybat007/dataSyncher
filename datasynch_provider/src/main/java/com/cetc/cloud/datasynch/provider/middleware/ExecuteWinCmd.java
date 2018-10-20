package com.cetc.cloud.datasynch.provider.middleware;

import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Description：通过java执行windows命令
 * Created by luolinjie on 2018/10/9.
 */
public class ExecuteWinCmd {

    Logger logger = LoggerFactory.getLogger(ExecuteWinCmd.class);

    public String executeCmd(String windowcmd) throws IOException, InterruptedException {
        logger.info("start");
        /**
         * String windowcmd = "cmd /c python datax.py D:\\Software\\install\\Environment\\DataX\\datax\\job\\mysql2mysql.json";
         */
        logger.info(windowcmd);
//      ***.exec("你的命令",null,new File("datax安装路径"));
        Process pr = Runtime.getRuntime().exec(windowcmd, null, new File(CommonInstance.DataXInstallPath));
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = null;
        while ((line = in.readLine()) != null) {
            logger.info(line);
        }
        in.close();
        pr.waitFor();
        logger.info("end");

        return line;
    }
}
