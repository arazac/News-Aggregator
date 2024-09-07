package org.news.aggregator.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {

    private String jwtToken;

    private long expiresIn;
}
