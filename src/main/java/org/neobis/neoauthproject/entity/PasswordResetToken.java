package org.neobis.neoauthproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String token;


    LocalDateTime createdAt;


    LocalDateTime expiresAt;

    LocalDateTime resetAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
