package com.github.stazxr.zblog.core.base;

import com.github.stazxr.zblog.util.Assert;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * PageParam
 *
 * @author SunTao
 * @since 2021-12-21
 */
@Getter
@Setter
public class PageParam implements Serializable {
    private Integer page;

    private Integer pageSize;

    public void checkPage() {
        Assert.notNull(page, "参数page不能为空");
        Assert.notNull(pageSize, "参数pageSize不能为空");
    }
}
