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

    private UUID id; // Valid?
    private String email; // Valid?
    private String city;
    private String country;
    private String firstName; // Valid?
    private String lastName; // Valid?
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
