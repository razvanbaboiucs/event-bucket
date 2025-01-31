package com.razvanbaboiu.event_bucket.event_manager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "identifications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "user_id"})
)
public class Identification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "identifications_generator")
    @SequenceGenerator(name = "identifications_generator", sequenceName = "identifications_id_seq", allocationSize = 1)
    private Long id;
    private String projectId;
    private String userId;
}
