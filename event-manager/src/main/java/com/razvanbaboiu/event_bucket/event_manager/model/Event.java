package com.razvanbaboiu.event_bucket.event_manager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_generator")
    @SequenceGenerator(name = "events_generator", sequenceName = "events_id_seq", allocationSize = 1)
    private Long id;
    private String projectId;
    private String typeId;
    private String title;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identification_id")
    private Identification identification;

    @PrePersist
    private void onCreate() {
        createdAt = new Date(System.currentTimeMillis());
    }
}
