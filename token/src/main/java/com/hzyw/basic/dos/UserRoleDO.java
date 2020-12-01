package com.hzyw.basic.dos;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "au_user_role_t")
@Data
@ApiModel(value = "用户角色关系表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
/**
 * 用户角色关系
 * @author
 * @date 2019.9.17
 */
public class UserRoleDO implements Serializable {
	/**用户角色关系ID*/
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "用户ID")
	private Integer ID;

	/**用户ID*/
	@Column(name = "USER_ID")
	@ApiModelProperty(value = "用户ID")
	private Integer USERID;

	/**角色ID*/
	@Column(name = "ROLE_ID")
	@ApiModelProperty(value = "角色ID")
	private Integer ROLEID;

}
