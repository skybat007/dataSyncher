package com.cetc.cloud.datasynch.provider.util;


import com.thoughtworks.xstream.core.util.Base64Encoder;

/**
 * 工程包名:   com.cetc.cloud.framework.client.util
 * 项目名称:   framework-plugin
 * 创建描述:   中电科智慧院分布式架构公共组件Base64编码组件工具类,工具包引用开源包com.thoughtworks.xstream.core.util.Base64Encoder
 * Creator:     zhangliang
 * Create_Date: 2017/10/13
 * Updater:     zhangliang
 * Update_Date：2017/10/13
 * 更新描述:    zhangliang 补充
 **/
public class CetcFrameBase64Encoder {

    /** 单例模式 */
    private static Base64Encoder base64Encoder;
    private static final Base64Encoder getBase64EncoderInstance()
    {
        if(null == base64Encoder)
        {
            base64Encoder = new Base64Encoder();
        }
        return base64Encoder;
    }

    /**
     *  加密字符方法
     *  base64Encoder()
     *  @param bytes 待加密字符数组
     *  @return String 已加密字符串结果集
     *  @throws null
     *  @author zhangliang
     * */
    public static String base64Encoder(byte[] bytes)
    {
        return getBase64EncoderInstance().encode(bytes).replaceAll("\n","");
    }

    /**
     *  解密字符方法
     *  base64Decoder()
     *  @param decoderStr 待解密字符数组
     *  @return byte 已加密字符串结果集
     *  @throws null
     *  @author zhangliang
     * */
    public static byte[] base64Decoder(String decoderStr)
    {
        return getBase64EncoderInstance().decode(decoderStr);
    }
}
