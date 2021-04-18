package com.account.common.sbUtil.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity for M_WORKER_NODE
 *
 * @author yutianbao
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
@TableName("worker_node")
public class WorkerNode implements Serializable {

    private static final long serialVersionUID = -6226338943380557332L;
    /**
     * Entity unique id (table unique)
     */
    @TableId(value = "id",type = IdType.AUTO)
    private long id;

    /**
     * Type of CONTAINER: HostName, ACTUAL : IP.
     */
    private String hostName;

    /**
     * Type of CONTAINER: Port, ACTUAL : Timestamp + Random(0-10000)
     */
    private String port;

    /**
     * type of {@link }
     */
    private int type;

    /**
     * Worker launch date, default now
     */
    private Date launchDate;

    /**
     * Created time
     */
    private Date created;

    /**
     * Last modified
     */
    private Date modified;
}
