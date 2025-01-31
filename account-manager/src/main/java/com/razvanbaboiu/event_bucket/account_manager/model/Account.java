package com.razvanbaboiu.event_bucket.account_manager.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_generator")
    @SequenceGenerator(name = "accounts_generator", sequenceName = "accounts_id_seq", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String email;
    private String teamName;
}
