package ru.skillbox.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "blocked_accounts")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BlockAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_id")
    private UUID accountId;
    @Column(name = "blocked_account_id")
    private UUID blockedAccountId;

}
