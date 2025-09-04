package com.samika.quiz_api.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity @Table(name="users")
@Data @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String username;
    @Column(nullable=false) private String firstName;
    private String lastName;
    @Column(nullable=false, unique=true) private String email;
    @Column(nullable=false) private String passwordHash;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role = Role.PLAYER;
    private String phone;
    private LocalDate dateOfBirth;
    private String avatarUrl;
    @Column(nullable=false) private Instant createdAt;

    public enum Role { ADMIN, PLAYER }
    @PrePersist void pre() { if (createdAt==null) createdAt = Instant.now(); }
}
