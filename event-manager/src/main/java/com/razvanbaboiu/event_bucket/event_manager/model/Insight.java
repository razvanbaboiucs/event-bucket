package com.razvanbaboiu.event_bucket.event_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "insights")
public class Insight {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insights_generator")
    @SequenceGenerator(name = "insights_generator", sequenceName = "insights_id_seq", allocationSize = 1)
    private Long id;
    private String projectId;
    private String typeId;
    private String title;
    private BigDecimal value;
}
