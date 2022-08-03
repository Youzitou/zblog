package com.github.stazxr.zblog.base.mapper;

import com.github.stazxr.zblog.base.domain.entity.Interface;
import com.github.stazxr.zblog.core.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 接口数据持久层
 *
 * @author SunTao
 * @since 2022-06-23
 */
public interface InterfaceMapper extends BaseMapper<Interface> {
    /**
     * 移除所有的接口信息
     */
    void clearInterface();

    /**
     * 根据请求信息查询接口信息
     * @param requestUri    请求地址
     * @param requestMethod 请求方式
     * @return Interface
     */
    Interface selectOneByRequest(@Param("uri") String requestUri, @Param("method") String requestMethod);
}