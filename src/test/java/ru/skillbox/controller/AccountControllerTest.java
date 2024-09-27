package ru.skillbox.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.skillbox.AbstractTest;
import ru.skillbox.dto.AccountDto;
import ru.skillbox.entity.Account;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerTest extends AbstractTest {

    @Test
    @DisplayName("GetAccount, should return 200")
    @WithMockUser(username = UUID_200_1)
    public void testGetAccount_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/account/me"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(UUID_200_1))
                .andExpect(jsonPath("$.email").value("test1@example.com"));
    }

    @Test
    @DisplayName("GetAccount, should return 404")
    @WithMockUser(username = UUID_404)
    public void testGetAccount_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/account/me"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GetAccount, should return 401")
    public void testGetAccount_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/account/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpServletResponse.SC_UNAUTHORIZED))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("DeleteAccount, should return 200")
    @WithMockUser(username = UUID_200_1)
    public void testDeleteAccount_shouldReturnOk() throws Exception {

        Account accountBeforeDeletion = accountRepository.findById(UUID.fromString(UUID_200_1)).get();

        assertFalse(accountBeforeDeletion.getIsDeleted());

        mockMvc.perform(delete("/api/v1/account/me"))
                .andExpect(status().isOk())
                .andExpect(content().string(SUCCESSFULLY));

        entityManager.clear();

        Account accountAfterDeletion = accountRepository.findById(UUID.fromString(UUID_200_1)).get();

        assertEquals(accountBeforeDeletion.getId(), accountAfterDeletion.getId());
        assertNotNull(accountAfterDeletion);
        assertTrue(accountAfterDeletion.getIsDeleted());
        assertFalse(accountAfterDeletion.getIsOnline());

    }

    @Test
    @DisplayName("DeleteAccount, should return 404")
    @WithMockUser(username = UUID_404)
    public void testDeleteAccount_shouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/account/me"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DeleteAccount, should return 401")
    public void testDeleteAccount_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/account/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpServletResponse.SC_UNAUTHORIZED))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("GetAllAccounts, should return 200")
    @WithMockUser(username = UUID_200_1)
    public void testGetAllAccounts_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/account")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(UUID_200_1))
                .andExpect(jsonPath("$.content[0].email").value("test1@example.com"))
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.number").value(0));
    }

    @Test
    @DisplayName("GetAllAccounts, should return 401")
    public void testGetAllAccounts_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/account")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpServletResponse.SC_UNAUTHORIZED))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("UpdateAccount, should return 200")
    @WithMockUser(username = UUID_200_1)
    public void testUpdateAccount_shouldReturnOk() throws Exception {

        Account accountBeforeUpdate = accountRepository.findById(UUID.fromString(UUID_200_1)).get();

        assertFalse(accountBeforeUpdate.getIsDeleted());
        assertNull(accountBeforeUpdate.getCountry());
        assertNull(accountBeforeUpdate.getCity());
        assertEquals("TEST1", accountBeforeUpdate.getFirstName());
        assertEquals("USER1", accountBeforeUpdate.getLastName());
        assertNull(accountBeforeUpdate.getAbout());
        assertNull(accountBeforeUpdate.getProfileCover());
        assertNull(accountBeforeUpdate.getEmojiStatus());
        assertNull(accountBeforeUpdate.getPhoto());

        AccountDto accountDto = AccountDto.builder()
                .id(accountBeforeUpdate.getId())
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .city("UpdatedCity")
                .country("UpdatedCountry")
                .about("UpdatedAbout")
                .profileCover("UpdatedProfileCover")
                .emojiStatus("1")
                .photo("UpdatedPhoto")
                .build();

        mockMvc.perform(put("/api/v1/account/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isOk());

        Account accountAfterUpdate = accountRepository.findById(accountBeforeUpdate.getId()).get();

        assertEquals(accountBeforeUpdate.getId(), accountAfterUpdate.getId());
        assertEquals(accountBeforeUpdate.getEmail(), accountAfterUpdate.getEmail());
        assertEquals("UPDATEDFIRSTNAME", accountAfterUpdate.getFirstName());
        assertEquals("UPDATEDLASTNAME", accountAfterUpdate.getLastName());
        assertEquals("UpdatedCity", accountAfterUpdate.getCity());
        assertEquals("UpdatedCountry", accountAfterUpdate.getCountry());
        assertEquals("UpdatedAbout", accountAfterUpdate.getAbout());
        assertEquals("UpdatedProfileCover", accountAfterUpdate.getProfileCover());
        assertEquals("1", accountAfterUpdate.getEmojiStatus());
        assertEquals("UpdatedPhoto", accountAfterUpdate.getPhoto());

    }

    @Test
    @DisplayName("UpdateAccount, should return 401")
    public void testUpdateAccount_shouldReturnUnauthorized() throws Exception {

        AccountDto accountDto = AccountDto.builder()
                .id(UUID.fromString(UUID_404))
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .city("UpdatedCity")
                .country("UpdatedCountry")
                .about("UpdatedAbout")
                .profileCover("UpdatedProfileCover")
                .emojiStatus("1")
                .photo("UpdatedPhoto")
                .build();

        mockMvc.perform(put("/api/v1/account/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpServletResponse.SC_UNAUTHORIZED))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("UpdateAccount, correct phone, should return 200")
    @WithMockUser(username = UUID_200_1)
    public void testUpdatePhone_correctPhone_shouldReturnOk() throws Exception {

        Account accountBeforeUpdate = accountRepository.findById(UUID.fromString(UUID_200_1)).get();

        assertNull(accountBeforeUpdate.getPhone());

        AccountDto accountDto = AccountDto.builder()
                .id(accountBeforeUpdate.getId())
                .phone("71111111111")
                .build();

        mockMvc.perform(put("/api/v1/account/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.phone").value("71111111111"));
    }

    @Test
    @DisplayName("UpdateAccount, correct phone without 7, should return 200")
    @WithMockUser(username = UUID_200_1)
    public void testUpdatePhone_correctPhoneWithout7_shouldReturnOk() throws Exception {

        Account accountBeforeUpdate = accountRepository.findById(UUID.fromString(UUID_200_1)).get();

        assertNull(accountBeforeUpdate.getPhone());

        AccountDto accountDto = AccountDto.builder()
                .id(accountBeforeUpdate.getId())
                .phone("1111111111")
                .build();

        mockMvc.perform(put("/api/v1/account/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.phone").value("71111111111"));
    }

    @Test
    @DisplayName("UpdateAccount, incorrect phone, should return 200")
    @WithMockUser(username = UUID_200_1)
    public void testUpdatePhone_incorrectPhone_shouldReturnOk() throws Exception {

        Account accountBeforeUpdate = accountRepository.findById(UUID.fromString(UUID_200_1)).get();

        assertNull(accountBeforeUpdate.getPhone());

        AccountDto accountDto = AccountDto.builder()
                .id(accountBeforeUpdate.getId())
                .phone("711111111")
                .build();

        mockMvc.perform(put("/api/v1/account/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isOk());

        Account accountAfterUpdate = accountRepository.findById(UUID.fromString(UUID_200_1)).get();

        assertNull(accountAfterUpdate.getPhone());

    }

    @Test
    @DisplayName("GetAccountById, should return 200")
    @WithMockUser(username = UUID_200_1)
    public void testGetAccountById_shouldReturnOk() throws Exception {

        UUID accountId = UUID.fromString(UUID_200_1);

        mockMvc.perform(get("/api/v1/account/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(accountId.toString()))
                .andExpect(jsonPath("$.email").value("test1@example.com"));
    }

    @Test
    @DisplayName("GetAccountById, should return 401")
    public void testGetAccountById_shouldReturnUnauthorized() throws Exception {

        UUID accountId = UUID.fromString(UUID_200_1);

        mockMvc.perform(get("/api/v1/account/{id}", accountId))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpServletResponse.SC_UNAUTHORIZED))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("GetAccountById, should return 404")
    @WithMockUser(username = UUID_200_1)
    public void testGetAccountById_shouldReturnNotFound() throws Exception {

        UUID accountId = UUID.fromString(UUID_404);

        mockMvc.perform(get("/api/v1/account/{id}", accountId))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("BlockAndUnblockAccount, should return 200")
    @WithMockUser(username = UUID_200_1)
    public void testBlockAndUnblockAccount_shouldReturnOk() throws Exception {
        UUID accountId = UUID.fromString(UUID_200_1);

        Account accountBeforeBlock = accountRepository.findById(accountId).get();

        assertFalse(accountBeforeBlock.getIsBlocked());

        mockMvc.perform(put("/api/v1/account/block/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(content().string(SUCCESSFULLY));

        entityManager.clear();

        Account accountAfterBlock = accountRepository.findById(accountId).get();

        assertEquals(accountBeforeBlock.getId(), accountAfterBlock.getId());
        assertNotNull(accountAfterBlock.getIsBlocked());
        assertTrue(accountAfterBlock.getIsBlocked());

        mockMvc.perform(delete("/api/v1/account/block/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(content().string(SUCCESSFULLY));

        entityManager.clear();

        Account accountAfterUnblock = accountRepository.findById(accountId).get();

        assertEquals(accountBeforeBlock.getId(), accountAfterUnblock.getId());
        assertNotNull(accountAfterUnblock.getIsBlocked());
        assertFalse(accountAfterUnblock.getIsBlocked());
    }

    @Test
    @DisplayName("BlockAccount, should return 404")
    @WithMockUser(username = UUID_200_1)
    public void testBlockAccount_shouldReturnNotFound() throws Exception {

        UUID accountId = UUID.fromString(UUID_404);

        mockMvc.perform(put("/api/v1/account/block/{id}", accountId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("UnblockAccount, should return 404")
    @WithMockUser(username = UUID_200_1)
    public void testUnblockAccount_shouldReturnNotFound() throws Exception {

        UUID accountId = UUID.fromString(UUID_404);

        mockMvc.perform(delete("/api/v1/account/block/{id}", accountId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("BlockAccount, should return 401")
    public void testBlockAccount_shouldReturnUnouthorized() throws Exception {

        UUID accountId = UUID.fromString(UUID_200_1);

        Account accountBeforeBlock = accountRepository.findById(accountId).get();

        assertFalse(accountBeforeBlock.getIsBlocked());

        mockMvc.perform(put("/api/v1/account/block/{id}", accountId))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpServletResponse.SC_UNAUTHORIZED))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("UnblockAccount, should return 401")
    public void testUnblockAccount_shouldReturnUnouthorized() throws Exception {

        UUID accountId = UUID.fromString(UUID_200_1);

        mockMvc.perform(delete("/api/v1/account/block/{id}", accountId))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpServletResponse.SC_UNAUTHORIZED))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").exists());
    }


}
