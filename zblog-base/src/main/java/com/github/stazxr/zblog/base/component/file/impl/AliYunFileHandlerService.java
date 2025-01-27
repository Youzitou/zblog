package com.github.stazxr.zblog.base.component.file.impl;

import com.github.stazxr.zblog.base.component.file.model.FileInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 阿里云文件存储
 *
 * @author SunTao
 * @since 2022-07-27
 */
@Component("AliYunFileHandlerService")
public class AliYunFileHandlerService extends BaseFileService {
    /**
     * 文件上传
     *
     * @param files 文件列表
     * @return 上传的文件列表
     */
    @Override
    public List<FileInfo> uploadFile(MultipartFile[] files) {
        return null;
    }
}
