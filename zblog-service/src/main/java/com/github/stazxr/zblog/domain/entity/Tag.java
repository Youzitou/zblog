package com.github.stazxr.zblog.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.stazxr.zblog.core.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 文章标签
 *
 * @author SunTao
 * @since 2020-12-20
 */
@Getter
@Setter
@TableName("tag")
public class Tag extends BaseEntity {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 标签名称
     */
    @TableField(value = "`NAME`")
    private String name;

    /**
     * 首页推荐
     */
    private Boolean good;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 是否已删除（逻辑操作，保护数据）
     */
    @TableLogic
    private Boolean deleted;
}
