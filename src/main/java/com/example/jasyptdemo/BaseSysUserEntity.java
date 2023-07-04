package com.example.jasyptdemo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.ColumnComment;
import com.tangzc.mpe.actable.annotation.Table;
import com.tangzc.mpe.actable.annotation.Unique;
import lombok.Data;

/**
 * 系统用户表
 */
@Data
@Table(value = "base_sys_user", comment = "系统用户表")
public class BaseSysUserEntity {
    @Column(isKey = true, comment = "ID", length = 64, notNull = true)
    @ColumnComment("ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Unique
    @Column(comment = "username", length = 100, notNull = true)
    private String username;
}
