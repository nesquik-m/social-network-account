package ru.skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.entity.BlockAccount;

import java.util.UUID;

@Repository
public interface BlockAccountRepository extends JpaRepository<BlockAccount, UUID>  {

    @Modifying
    @Query("DELETE FROM BlockAccount ba WHERE ba.accountId = :accountId AND ba.blockedAccountId = :blockedAccountId")
    int unblockAccount(UUID accountId, UUID blockedAccountId);

}
