package com.cetc.datasynch.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cetc.datasynch.common.CommonConfig;
import com.cetc.datasynch.core.util.HttpUtil;
import com.cetc.datasynch.core.util.JsonUtil;
import com.cetc.datasynch.model.ScheduleModel;
import com.cetc.datasynch.model.Token;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http在线请求服务
 * Created by llj on 2018/10/11.
 */
@Service
public class HttpOperateService {

    public List<HashMap> doHttpQuery(ScheduleModel model, int pageNum) {
        //获取URL
        String URL = model.getSource();
        List<HashMap> listData = null;
        //组装参数 pageNum和pageSize
        JSONObject params = new JSONObject();
        params.put(CommonConfig.PAGE_NUM_NAME, String.valueOf(pageNum));
        int pageSize = model.getPageSize();
        params.put(CommonConfig.PAGE_SIZE_NAME, String.valueOf(pageSize));

        //获取token
        Token token = model.getToken();

        //获取json解析规则
        String jsonExtractRule = model.getJsonExtractRule();

        JSONObject httpResult = HttpUtil.doGetWithAuthoration(URL, params, token);
        if (200==Integer.parseInt((String)httpResult.get("code"))){
            String data = (String) httpResult.get("data");

            JSONObject jsonResultData = JSONObject.parseObject(data);

            listData = getListData(jsonResultData,jsonExtractRule);
        }
        return listData;
    }

    /**
     * 根据传入json解析规则，获取JsonArray形式的Data主体
     */
    private List<HashMap> getListData(JSONObject jsonResultData, String jsonExtractRule) {
        String[] splits = jsonExtractRule.split("\\.");

        int size = splits.length;

        JSONObject extractJson = jsonResultData;
        JSONArray arrData = null;

        for (int i=0;i<size;i++){
            if (i==size-1){
                arrData = extractJson.getJSONArray(splits[i]);
            }else {
                extractJson = extractJson.getJSONObject(splits[i]);
            }
        }
        List<HashMap> list = JsonUtil.parseArray2List(arrData);
        return list;
    }

    public static void main(String[] args){
//        HttpOperateService httpOperateService = new HttpOperateService();
//        String json = "{\n" +
//                "\t\"rows\": {\n" +
//                "\t\t\"data\": [{\n" +
//                "\t\t\t\"clktk\": [\"https://stat.lianmeng.360.cn/s2/clk.gif?lm_extend=ctype:22|lmbid:26&qid=105a260699460487.0&nu=4&ls=sn2254685&ifr=0&ir=1&m=AQAFCgIGAAYJCQQGAAQIB3rF19YLIOklrAwHBQ&wp=AAAAAFvCtrEAAAAAAAx76svsoz3kAfd3YFNsmA&index=0&txt=cpaè\u0080\u0083è¯\u0095æ\u0097¶é\u0097´&_r=1539487409799\", \"https://max-l.mediav.com/rtb?type=3&d=127&b=12a7280c47e0b668&p=2254685&l=1154840&s=1&k=VIf2igAAAAA=&i=5cNqzr6yFDNH&v=00266380435182105010017&x=__OFFSET_X__&y=__OFFSET_Y__&st=__EVENT_TIME_START__&et=__EVENT_TIME_END__&tc=&turl=\"],\n" +
//                "\t\t\t\"curl\": \"http://spro.so.com/searchthrow/api/midpage/throw?ls=sn2254685&q=cpaè\u0080\u0083è¯\u0095æ\u0097¶é\u0097´&lmid=105a260699460487.0&lm_extend=ctype:22|lmbid:26&ctype=22&rurl=https://hao.360.cn/&bucket_id=26\",\n" +
//                "\t\t\t\"desc\": \"cpa考试时间，跟随名师，开启注会赛程；制动学习计划，稳扎稳打\",\n" +
//                "\t\t\t\"img\": \"https://p3.ssl.qhimgs0.com/sdm/280_200_100/t0110652f9bc344d95e.jpg\",\n" +
//                "\t\t\t\"imgh\": 200,\n" +
//                "\t\t\t\"imgw\": 200,\n" +
//                "\t\t\t\"imptk\": [\"https://stat.lianmeng.360.cn/s2/srp.gif?lm_extend=ctype:22|lmbid:26&qid=105a260699460487.0&nu=4&ls=sn2254685&ifr=0&ir=1&m=AQAFCgIGAAYJCQQGAAQIB3rF19YLIOklrAwHBQ&wp=AAAAAFvCtrEAAAAAAAx76svsoz3kAfd3YFNsmA&_r=1539487409799\", \"https://max-l.mediav.com/rtb?type=2&d=127&b=12a7280c47e0b668&p=2254685&l=1154840&s=1&z=1&w=AAAAAFvCtrEAAAAAAAx8E+dZP9bhYxHbl64JYg==&k=VIf2igAAAAA=&i=5cNqzr6yFDNH&v=00266380435182105010017\"],\n" +
//                "\t\t\t\"slot\": 1,\n" +
//                "\t\t\t\"src\": \"360搜索\",\n" +
//                "\t\t\t\"title\": \"cpa考试时间重大调整须知\",\n" +
//                "\t\t\t\"type\": 1\n" +
//                "\t\t}, {\n" +
//                "\t\t\t\"clktk\": [\"https://stat.lianmeng.360.cn/s2/clk.gif?lm_extend=ctype:22|lmbid:26&qid=105a260699460487.1&nu=4&ls=sn2254685&ifr=0&ir=1&m=AQAFCgIGAAYJCQQGAAQIB3rF19YLItG09E3vHA&wp=AAAAAFvCtrEAAAAAAAx8wZ32IQgVbtziRiPclA&index=1&txt=cpaæ\u008A¥å\u0090\u008Dæ\u009D¡ä»¶&_r=1539487409799\", \"https://max-l.mediav.com/rtb?type=3&d=127&b=12a7280c47e0b668&p=2254685&l=1154840&s=2&k=VIf2igAAAAA=&i=5cQqzr6yFDNU&v=00266380435182105010017&x=__OFFSET_X__&y=__OFFSET_Y__&st=__EVENT_TIME_START__&et=__EVENT_TIME_END__&tc=&turl=\"],\n" +
//                "\t\t\t\"curl\": \"http://spro.so.com/searchthrow/api/midpage/throw?ls=sn2254685&q=cpaæ\u008A¥å\u0090\u008Dæ\u009D¡ä»¶&lmid=105a260699460487.1&lm_extend=ctype:22|lmbid:26&ctype=22&rurl=https://hao.360.cn/&bucket_id=26\",\n" +
//                "\t\t\t\"desc\": \"cpa报名条件如下1.学历要求或将调整.非财会专业考生可能将取.\",\n" +
//                "\t\t\t\"img\": \"https://p3.ssl.qhimgs0.com/sdm/280_200_100/t01d617bf71795877a2.jpg\",\n" +
//                "\t\t\t\"imgh\": 200,\n" +
//                "\t\t\t\"imgw\": 200,\n" +
//                "\t\t\t\"imptk\": [\"https://stat.lianmeng.360.cn/s2/srp.gif?lm_extend=ctype:22|lmbid:26&qid=105a260699460487.1&nu=4&ls=sn2254685&ifr=0&ir=1&m=AQAFCgIGAAYJCQQGAAQIB3rF19YLItG09E3vHA&wp=AAAAAFvCtrEAAAAAAAx8wZ32IQgVbtziRiPclA&_r=1539487409799\", \"https://max-l.mediav.com/rtb?type=2&d=127&b=12a7280c47e0b668&p=2254685&l=1154840&s=2&z=1&w=AAAAAFvCtrEAAAAAAAx87LvYlwq+WYdcC2PjYQ==&k=VIf2igAAAAA=&i=5cQqzr6yFDNU&v=00266380435182105010017\"],\n" +
//                "\t\t\t\"slot\": 2,\n" +
//                "\t\t\t\"src\": \"360搜索\",\n" +
//                "\t\t\t\"title\": \"cpa报名条件-报名条件有哪些.\",\n" +
//                "\t\t\t\"type\": 1\n" +
//                "\t\t}, {\n" +
//                "\t\t\t\"clktk\": [\"https://stat.lianmeng.360.cn/s2/clk.gif?lm_extend=ctype:22|lmbid:26&qid=105a260699460487.2&nu=4&ls=sn2254685&ifr=0&ir=1&m=AQAFCgIGAAYJCQQGAAQIB3rF19YLMSOt8PxtVg&wp=AAAAAFvCtrEAAAAAAAx9hZiPXiriaen3MuFSbw&index=2&txt=å¤©ç\u008C«&_r=1539487409799\", \"https://max-l.mediav.com/rtb?type=3&d=127&b=12a7280c47e0b668&p=2254685&l=1154840&s=3&k=VIf2igAAAAA=&i=5cxqzr6yFDNK&v=00266380435182105010017&x=__OFFSET_X__&y=__OFFSET_Y__&st=__EVENT_TIME_START__&et=__EVENT_TIME_END__&tc=&turl=\"],\n" +
//                "\t\t\t\"curl\": \"http://spro.so.com/searchthrow/api/midpage/throw?ls=sn2254685&q=å¤©ç\u008C«&lmid=105a260699460487.2&lm_extend=ctype:22|lmbid:26&ctype=22&rurl=https://hao.360.cn/&bucket_id=26\",\n" +
//                "\t\t\t\"desc\": \"天猫-上天猫。专注品质，大牌护航。一站齐全，嗨翻惠购，乐享高品质生活！\",\n" +
//                "\t\t\t\"img\": \"https://p3.ssl.qhimgs0.com/sdm/280_200_100/t01685bfa73552ff7f6.jpg\",\n" +
//                "\t\t\t\"imgh\": 200,\n" +
//                "\t\t\t\"imgw\": 200,\n" +
//                "\t\t\t\"imptk\": [\"https://stat.lianmeng.360.cn/s2/srp.gif?lm_extend=ctype:22|lmbid:26&qid=105a260699460487.2&nu=4&ls=sn2254685&ifr=0&ir=1&m=AQAFCgIGAAYJCQQGAAQIB3rF19YLMSOt8PxtVg&wp=AAAAAFvCtrEAAAAAAAx9hZiPXiriaen3MuFSbw&_r=1539487409799\", \"https://max-l.mediav.com/rtb?type=2&d=127&b=12a7280c47e0b668&p=2254685&l=1154840&s=3&z=1&w=AAAAAFvCtrEAAAAAAAx9ryc6HSItzXLJEg56GQ==&k=VIf2igAAAAA=&i=5cxqzr6yFDNK&v=00266380435182105010017\"],\n" +
//                "\t\t\t\"slot\": 3,\n" +
//                "\t\t\t\"src\": \"360搜索\",\n" +
//                "\t\t\t\"title\": \"天猫上天猫纵享生活品质！\",\n" +
//                "\t\t\t\"type\": 1\n" +
//                "\t\t}, {\n" +
//                "\t\t\t\"clktk\": [\"https://stat.lianmeng.360.cn/s2/clk.gif?lm_extend=ctype:22|lmbid:26&qid=105a260699460487.3&nu=4&ls=sn2254685&ifr=0&ir=1&m=AQAFCgIGAAYJCQQGAAQIB3rF19YLqgYVbbRglQ&wp=AAAAAFvCtrEAAAAAAAx-VInLFn0XNq-dSgb-8w&index=3&txt=cpaæ\u008A¥è\u0080\u0083æ\u009D¡ä»¶&_r=1539487409799\", \"https://max-l.mediav.com/rtb?type=3&d=127&b=12a7280c47e0b668&p=2254685&l=1154840&s=4&k=VIf2igAAAAA=&i=5cMqzr6yFD4c&v=00266380435182105010017&x=__OFFSET_X__&y=__OFFSET_Y__&st=__EVENT_TIME_START__&et=__EVENT_TIME_END__&tc=&turl=\"],\n" +
//                "\t\t\t\"curl\": \"http://spro.so.com/searchthrow/api/midpage/throw?ls=sn2254685&q=cpaæ\u008A¥è\u0080\u0083æ\u009D¡ä»¶&lmid=105a260699460487.3&lm_extend=ctype:22|lmbid:26&ctype=22&rurl=https://hao.360.cn/&bucket_id=26\",\n" +
//                "\t\t\t\"desc\": \"cpa报考条件，报名时间，报名入口，实时发布报考资讯。名师辅导，专家答疑，顺利考试。\",\n" +
//                "\t\t\t\"img\": \"https://p3.ssl.qhimgs0.com/sdm/280_200_100/t015bfaef944c174a65.jpg\",\n" +
//                "\t\t\t\"imgh\": 200,\n" +
//                "\t\t\t\"imgw\": 200,\n" +
//                "\t\t\t\"imptk\": [\"https://stat.lianmeng.360.cn/s2/srp.gif?lm_extend=ctype:22|lmbid:26&qid=105a260699460487.3&nu=4&ls=sn2254685&ifr=0&ir=1&m=AQAFCgIGAAYJCQQGAAQIB3rF19YLqgYVbbRglQ&wp=AAAAAFvCtrEAAAAAAAx-VInLFn0XNq-dSgb-8w&_r=1539487409799\", \"https://max-l.mediav.com/rtb?type=2&d=127&b=12a7280c47e0b668&p=2254685&l=1154840&s=4&z=1&w=AAAAAFvCtrEAAAAAAAx+fjMWLVConeqemTdopg==&k=VIf2igAAAAA=&i=5cMqzr6yFD4c&v=00266380435182105010017\"],\n" +
//                "\t\t\t\"slot\": 4,\n" +
//                "\t\t\t\"src\": \"360搜索\",\n" +
//                "\t\t\t\"title\": \"cpa报考条件_条件变更\",\n" +
//                "\t\t\t\"type\": 1\n" +
//                "\t\t}]\n" +
//                "\t},\n" +
//                "\t\"reqtimes\": \"1\"\n" +
//                "}";
//        JSONObject data = new JSONObject();
//        JSONObject parsedData = data.parseObject(json);
//
//        String rule = "rows.data";
//
//
//        httpOperateService.getListData(parsedData,rule);
        List list= new ArrayList<String>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        System.out.println(list.toString());
    }
}
