package com.hzyw.basic.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hao yuan
 */

@MappedSuperclass
@Data
@DynamicInsert
@DynamicUpdate
public class DateDO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @TableField(fill = FieldFill.UPDATE)
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @Column
    @ApiModelProperty(value="创建人名字")
    private String updateByName;

    @Column
    @ApiModelProperty(value="创建人id")
    private String updateById;


    @TableField(fill = FieldFill.INSERT)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @Column
    @ApiModelProperty(value = "创建人名字")
    private String createByName;

    @Column
    @ApiModelProperty(value = "创建人id")
    private String createById;



}
