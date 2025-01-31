package com.razvanbaboiu.event_bucket.account_manager.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "projects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projects_generator")
    @SequenceGenerator(name = "projects_generator", sequenceName = "projects_id_seq", allocationSize = 1)
    private Long id;
    private String projectId;
    private String projectName;
    private String apiKey;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
