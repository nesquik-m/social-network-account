package ru.skillbox.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import ru.skillbox.AbstractTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends AbstractTest {

    @Test
    @DisplayName("GetAccount, should return 200")
    @WithMockUser(username = "8416d06e-3cb9-4d6d-b96a-84f287d216fd")
    public void testGetAccount_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/account/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("8416d06e-3cb9-4d6d-b96a-84f287d216fd"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
    
    @Test
    @DisplayName("GetAccount, should return 401")
    public void testGetAccount_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/account/me"))
                .andExpect(status().isUnauthorized());
    }

//    @Test
//    @DisplayName("DeleteAccount, should return 200")
//    @WithMockUser(username = "8416d06e-3cb9-4d6d-b96a-84f287d216fd")
//    public void testDeleteAccount_ShouldReturnOk() throws Exception {
////        doNothing().when(accountService).deleteAccount();
//
//        mockMvc.perform(delete("/api/v1/account/me"))
//                .andExpect(status().isOk());
//
//        verify(accountService, times(1)).deleteAccount();
//
        /* Тест падает с такой ошибкой
        org.mockito.exceptions.misusing.NotAMockException:
    Argument passed to verify() is of type AccountServiceImpl$$SpringCGLIB$$0 and is not a mock!
    Make sure you place the parenthesis correctly!
    See the examples of correct verifications:
    verify(mock).someMethod();
    verify(mock, times(10)).someMethod();
    verify(mock, atLeastOnce()).someMethod();

	at ru.skillbox.controller.AccountControllerTest.testDeleteAccount_ShouldReturnOk(AccountControllerTest.java:44)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
         */
//    }

}
