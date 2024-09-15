package ru.skillbox.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "accounts")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private UUID id;
    @Column(nullable = false)
    private String email;
    private String city;
    private String country;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "birth_date")
    private LocalDateTime birthDate;
    @Column(name = "blocked", nullable = false)
    private Boolean isBlocked;
    @Column(name = "deleted", nullable = false)
    private Boolean isDeleted;
    @Column(name = "online", nullable = false)
    private Boolean isOnline;
    private String phone;
    private String about;
    @Column(name = "profile_cover")
    private String profileCover;
    @Column(name = "emoji_status")
    private String emojiStatus;
    @Column(name = "photo")
    private String photo;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    @Column(name = "last_online_time")
    private LocalDateTime lastOnlineTime;

    //    @Transient
//    @Builder.Default
//    private Set<RoleType> roles = new HashSet<>();
    @Transient
//    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<RoleType> roles = new ArrayList<>();

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
