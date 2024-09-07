package ru.skillbox.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.mapper.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private UUID id;
    private String email;
    private String city;
    private String country;
    private String firstName;
    private String lastName;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime birthDate;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private Boolean isOnline;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String phone; // приходит так -> phone: "79806372969"
    private String about;
    private String profileCover;
    private String emojiStatus;
    private String photo;
    private LocalDateTime lastOnlineTime;

//    private UUID id;
//    @NotNull(message = "Поле 'email' не должно быть пустым")
//    private String email;
//    @NotNull(message = "Поле 'phone' не должно быть пустым")
//    private String phone;
//    @NotNull(message = "Поле 'photo' не должно быть пустым")
//    private String photo;
//    @NotNull(message = "Поле 'about' не должно быть пустым")
//    private String about; // *
//    @NotNull(message = "Поле 'city' не должно быть пустым")
//    private String city;
//    @NotNull(message = "Поле 'country' не должно быть пустым")
//    private String country;
//    @NotNull(message = "Поле 'token' не должно быть пустым")
//    private String token;
//    @NotNull(message = "Поле 'statusCode' не должно быть пустым")
//    private String statusCode;
//    @NotNull(message = "Поле 'firstName' не должно быть пустым")
//    private String firstName;
//    @NotNull(message = "Поле 'lastName' не должно быть пустым")
//    private String lastName;
//    @NotNull(message = "Поле 'regDate' не должно быть пустым")
//    private LocalDateTime regDate; // String
//    @NotNull(message = "Поле 'birthDate' не должно быть пустым")
//    private LocalDateTime birthDate; // **
//    @NotNull(message = "Поле 'messagePermission' не должно быть пустым")
//    private String messagePermission;
//    @NotNull(message = "Поле 'lastOnlineTime' не должно быть пустым")
//    private LocalDateTime lastOnlineTime; // String
//    @NotNull(message = "Поле 'isOnline' не должно быть пустым")
//    private boolean isOnline;
//    @NotNull(message = "Поле 'isBlocked' не должно быть пустым")
//    private boolean isBlocked;
//    @NotNull(message = "Поле 'isDeleted' не должно быть пустым")
//    private boolean isDeleted;
//    @NotNull(message = "Поле 'photoId' не должно быть пустым")
//    private String photoId;
//    @NotNull(message = "Поле 'photoName' не должно быть пустым")
//    private String photoName;
//    @NotNull(message = "Поле 'role' не должно быть пустым")
//    private RoleType role;
//    @NotNull(message = "Поле 'createdOn' не должно быть пустым")
//    private LocalDateTime createdOn; // String
//    @NotNull(message = "Поле 'updatedOn' не должно быть пустым")
//    private LocalDateTime updatedOn; // String
//    @NotNull(message = "Поле 'password' не должно быть пустым")
//    private String password;

}
