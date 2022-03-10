package com.github.stazxr.zblog.log.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志管理
 *
 * @author SunTao
 * @since 2021-12-19
 */
@Slf4j
@RestController
@RequestMapping("/api/logs")
@Api(tags = "日志管理")
public class LogController {

}
