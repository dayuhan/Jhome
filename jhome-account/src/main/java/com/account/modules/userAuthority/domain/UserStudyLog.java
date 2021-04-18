package com.account.modules.userAuthority.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Da xv
 * Create date: 2019.12.25
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
@TableName("user_study_log" )
public class UserStudyLog implements Serializable {
    private static final long serialVersionUID = 9154648142568437190L;


    private Long id;

    private Date date;

//    @TableField(value = "totaltime" )
    private Integer totalTime;

    private Long userId;
}
