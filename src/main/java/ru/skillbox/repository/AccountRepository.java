package ru.skillbox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.entity.Account;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {

    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE Account a SET a.isBlocked = :blocked WHERE a.id = :accountId")
    int updateBlocked(UUID accountId, boolean blocked);

    @Modifying
    @Query("UPDATE Account a SET a.isDeleted = :deleted WHERE a.id = :accountId")
    void updateDeleted(UUID accountId, boolean deleted);

    @Query("SELECT a.id FROM Account a")
    List<UUID> findAllIds();

    @Query("SELECT a FROM Account a WHERE a.id IN :ids")
    Page<Account> findAccountsByIds(List<UUID> ids, Pageable pageable);

}
