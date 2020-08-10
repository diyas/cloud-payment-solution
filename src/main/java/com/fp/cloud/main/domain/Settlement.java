package com.fp.cloud.main.domain;

import com.fp.cloud.main.global.TrStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "settlement")
public class Settlement {
    @Id
    @Column
    private String id;
    @Column
    @CreationTimestamp
    @ApiModelProperty(notes = "Request Date")
    private Date requestDate;
    @Column
    private String topic;
    @Column
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(notes = "status")
    private TrStatusEnum status ;

}
