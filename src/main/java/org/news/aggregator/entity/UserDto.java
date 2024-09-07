package org.news.aggregator.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private Integer userId;
    private String username;
    private String password;
    private String email;
}
