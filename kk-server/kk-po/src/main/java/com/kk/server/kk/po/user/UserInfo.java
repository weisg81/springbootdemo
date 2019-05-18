package com.kk.server.kk.po.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Description 这个类作用是什么
 * Author WEISANGENG
 * Date 2019/5/18
 **/
@Data
@TableName("t_user")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 8567907944098359918L;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;
    @TableField(value = "money")
    private BigDecimal money;
    @TableField(value = "create_date")
    private Date createDate;
    @TableField(value = "modify_date")
    private Date modifyDate;
    @TableField(value = "is_deleted")
    private Integer isDeleted;
    @TableField(value = "age")
    private Integer age;
}
