package com.github.stazxr.zblog.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.github.stazxr.zblog.log.domain.dto.LogQueryDto;
import com.github.stazxr.zblog.log.domain.entity.Log;
import com.github.stazxr.zblog.log.domain.vo.LogVo;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 日志业务层
 *
 * @author SunTao
 * @since 2021-05-16
 */
public interface LogService extends IService<Log> {
    /**
     * 保存日志信息
     *
     * @param joinPoint 切点信息
     * @param log       日志信息
     * @param result    执行结果
     * @param e         错误信息
     */
    void saveLog(ProceedingJoinPoint joinPoint, Log log, Object result, Throwable e);

    /**
     * 查询用户日志列表
     *
     * @param queryDto 查询参数
     * @return userLog
     */
    PageInfo<LogVo> queryUserLog(LogQueryDto queryDto);
}
