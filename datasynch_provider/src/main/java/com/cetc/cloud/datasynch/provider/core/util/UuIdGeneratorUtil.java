package com.cetc.cloud.datasynch.provider.core.util; /**********************************************************************
 * Copyright (c) 2017 CETC Company
 * 中电科新型智慧城市研究院有限公司版权所有
 * <p>
 * PROPRIETARY RIGHTS of CETC Company are involved in the
 * subject matter of this material. All manufacturing, reproduction, use,
 * and sales rights pertaining to this subject matter are governed by the
 * license agreement. The recipient of this software implicitly accepts
 * the terms of the license.
 * 本软件文档资料是中电科新型智慧城市研究院有限公司的资产，任何人士阅读和
 * 使用本资料必须获得相应的书面授权，承担保密责任和接受相应的法律约束。
 *************************************************************************/

import java.util.UUID;

/**
 * 工程包名:   com.cetc.cloud.id_generator.provider.service
 * 项目名称:   id-generator
 * 创建描述:   zhangliang 补充
 * Creator:     zhangliang
 * Create_Date: 2017/9/25
 * Updater:     zhangliang
 * Update_Date：2017/9/25
 * 更新描述:    zhangliang 补充
 **/

public class UuIdGeneratorUtil {
    /**
     * ID生成器，单例模式
     * */
    private static IdGenerator idGenerator;

    public static final IdGenerator getIdGeneratorInstance() {
        if (null == idGenerator) {
            idGenerator = new IdGenerator(1L, 1L);
        }
        return idGenerator;
    }

    /**
     *  生成的随机唯一UUID，32位
     *  getRandomBusinessUuId()
     *  @return String UUId
     *  @throws null
     *  @author zhangliang
     * */
    public synchronized String getRandomUuId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     *  针对业务ID生成的唯一UUID
     *  getRandomBusinessUuId()
     *  @param businessName 业务名称
     *  @return String UUId
     *  @throws null
     *  @author zhangliang
     * */
    public synchronized String getBusinessUuId(String businessName) {
        return businessName + getRandomUuId();
    }

    /**
     *  中电科分布式架构内统一ID生成器，生成全局唯一ID
     *  getRandomBusinessUuId()
     *  @param applicationName 业务名称
     *  @return String UUId
     *  @throws null
     *  @author zhangliang
     * */
    public static synchronized String getCetcCloudUuid(String applicationName) {
        if (null == applicationName) {
            System.out.println("[error]: UuIdGeneratorUtil getCetcCloudUuid() applicationName input null");
        }
        long curLongId = getIdGeneratorInstance().nextId();
        return CetcFrameBase64Encoder.base64Encoder((applicationName + curLongId).getBytes());
    }

    public static class IdGenerator {
        private final static long TWEPOCH = 1288834974657L;
        private final static long WORKER_ID_BITS = 5L;                                                         // 机器标识位数
        private final static long DATA_CENTER_ID_BITS = 5L;                                                    // 数据中心标识位数
        private final static long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);                               // 机器ID最大值
        private final static long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);                     // 数据中心ID最大值
        private final static long SEQUENCE_BITS = 12L;                                                         // 毫秒内自增位
        private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;                                             // 机器ID偏左移12位
        private final static long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;                       // 数据中心ID左移17位
        private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS; // 时间毫秒左移22位
        private final static long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

        private static long lastTimestamp = -1L;
        private long sequence = 0L;
        private final long workerId;
        private final long dataCenterId;

        public IdGenerator(long workerId, long dataCenterId) {
            if (workerId > MAX_WORKER_ID || workerId < 0) {
                throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0");
            }
            if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
                throw new IllegalArgumentException("data center Id can't be greater than %d or less than 0");
            }
            this.workerId = workerId;
            this.dataCenterId = dataCenterId;
        }

        /**
         *  算法生成的全系唯一UUID
         *  nextId()
         *  @return long UUId
         *  @throws Exception
         *  @author zhangliang
         * */
        public synchronized long nextId() {
            long timestamp = timeGen();
            if (timestamp < lastTimestamp) {
                try {
                    throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & SEQUENCE_MASK;  /* 当前毫秒内，则+1 */
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp); /* 当前毫秒内计数满了，则等待下一秒 */
                }
            } else {
                sequence = 0;
            }
            lastTimestamp = timestamp;

            // ID偏移组合生成最终的ID，并返回ID
            return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT)
                    | (dataCenterId << DATA_CENTER_ID_SHIFT)
                    | (workerId << WORKER_ID_SHIFT) | sequence;
        }

        private long tilNextMillis(final long lastTimestamp) {
            long timestamp = this.timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = this.timeGen();
            }
            return timestamp;
        }

        private long timeGen() {
            return System.currentTimeMillis();
        }
    }
}
