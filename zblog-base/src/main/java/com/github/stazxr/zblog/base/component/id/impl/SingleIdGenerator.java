package com.github.stazxr.zblog.base.component.id.impl;

import com.github.stazxr.zblog.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 单机部署全局唯一ID生成器
 *
 * @author SunTao
 * @since 2021-12-12
 */
@Slf4j
@Service("IdGeneratorService")
@ConditionalOnProperty(name = "zblog.deploy-type", havingValue = "single")
public class SingleIdGenerator extends BaseWorkIdIdGeneratorImpl {
    public SingleIdGenerator() {
        log.info("The id generate model: single");
    }

    /**
     * 根据机器ID获取机器IP，单机部署，返回本地ID就可以
     *
     * @param workId 机器Id
     * @return WorkIp
     */
    @Override
    protected String parseWorkerIp(Long workId) {
        return Constants.LOCAL_HOST_V4;
    }

    /**
     * 获取机器ID，单机部署，直接返回0L
     *
     * @return Long workId
     */
    @Override
    protected Long getWorkId() {
        return 0L;
    }
}
