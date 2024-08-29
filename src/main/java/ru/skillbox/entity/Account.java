package ru.skillbox.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "account")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private UUID id;
    private String email;
    private String city;
    private String country;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private LocalDateTime birthDate;
    @Column(name = "blocked")
    private Boolean isBlocked;
    @Column(name = "deleted")
    private Boolean isDeleted;
    @Column(name = "online")
    private Boolean isOnline;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

//    @Id
//    private UUID id;
//    private String email;
//    private String phone;
//    private String photo;
//    private String about;
//    private String city;
//    private String country;
//    private String token;
//    @Column(name = "status_code")
//    @Enumerated(value = EnumType.STRING)
//    private StatusCode statusCode;
//    @Column(name = "first_name")
//    private String firstName;
//    @Column(name = "last_name")
//    private String lastName;
//    @Column(name = "reg_date")
//    private LocalDateTime regDate;
//    @Column(name = "birth_date")
//    private LocalDateTime birthDate;
//    @Column(name = "message_permission")
//    private String messagePermission;
//    @Column(name = "last_online_time")
//    private LocalDateTime lastOnlineTime;
//    @Column(name = "is_online")
//    private boolean isOnline;
//    @Column(name = "blocked")
//    private boolean isBlocked;
//    @Column(name = "deleted")
//    private boolean isDeleted;
//    @Column(name = "photo_id")
//    private String photoId;
//    @Column(name = "photo_name")
//    private String photoName;
//    @Enumerated(value = EnumType.STRING)
//    private RoleType role; // не лист?
//    @Column(name = "created_on")
//    private LocalDateTime createdOn;
//    @Column(name = "updated_on")
//    private LocalDateTime updatedOn;
//    private String password;

}
