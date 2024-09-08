package org.news.aggregator.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class RegistrationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String token;

    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userId")
    private UserInfo user;

    public RegistrationToken(String token, UserInfo user) {
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationTime();
    }

    public static Date calculateExpirationTime() {
        long expirationTimeInMinutes = 10;
        long expirationTimeInMilliseconds = expirationTimeInMinutes * 60 * 1000;
        return new Date(System.currentTimeMillis() + expirationTimeInMilliseconds);
    }

}
