package ru.skillbox.mapper;

import ru.skillbox.dto.AccountDto;
import ru.skillbox.entity.Account;

public class AccountUpdateFactory {

    public static void updateFields(Account updatedAccount, AccountDto accountDto) {

        // id
        // isBlocked;
        // isDeleted;
        // isOnline;
        // createdOn;
        // updatedOn;

        if (accountDto.getEmail() != null && !accountDto.getEmail().isEmpty()) {
            updatedAccount.setEmail(accountDto.getEmail());
        }
        if (accountDto.getCity() != null && !accountDto.getCity().isEmpty()) {
            updatedAccount.setCity(accountDto.getCity());
        }
        if (accountDto.getCountry() != null && !accountDto.getCountry().isEmpty()) {
            updatedAccount.setCountry(accountDto.getCountry());
        }
        if (accountDto.getFirstName() != null && !accountDto.getFirstName().isEmpty()) {
            updatedAccount.setFirstName(accountDto.getFirstName().trim().toUpperCase());
        }
        if (accountDto.getLastName() != null && !accountDto.getLastName().isEmpty()) {
            updatedAccount.setLastName(accountDto.getLastName().trim().toUpperCase());
        }
        if (accountDto.getBirthDate() != null) {
            updatedAccount.setBirthDate(accountDto.getBirthDate());
        }
        if (accountDto.getEmojiStatus() != null) {
            updatedAccount.setEmojiStatus(accountDto.getEmojiStatus());
        }
        if (accountDto.getPhone() != null && !accountDto.getPhone().isEmpty()) {
            updatedAccount.setPhone(accountDto.getPhone());
        }
        if (accountDto.getAbout() != null && !accountDto.getAbout().isEmpty()) {
            updatedAccount.setAbout(accountDto.getAbout());
        }
        if (accountDto.getPhoto() != null && !accountDto.getPhoto().isEmpty()) {
            updatedAccount.setPhoto(accountDto.getPhoto());
        }
        if (accountDto.getProfileCover() != null && !accountDto.getProfileCover().isEmpty()) {
            updatedAccount.setProfileCover(accountDto.getProfileCover());
        }
    }
}
