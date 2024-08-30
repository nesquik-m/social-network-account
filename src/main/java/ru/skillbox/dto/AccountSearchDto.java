package ru.skillbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountSearchDto {

    private List<UUID> ids;
    private String author;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDateFrom;
    private LocalDateTime birthDateTo;
    private String city;
    private String country;
//    private boolean isBlocked;
    private boolean isDeleted;
    private Integer ageTo;
    private Integer ageFrom;

}
