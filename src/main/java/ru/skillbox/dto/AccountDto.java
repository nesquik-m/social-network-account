package ru.skillbox.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import ru.skillbox.mapper.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime birthDate;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private Boolean isOnline;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String phone;
    private String about;
    private String profileCover;
    private String emojiStatus;
    private String photo;
    private LocalDateTime lastOnlineTime;

}
