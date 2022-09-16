package com.moon.joyce.example.functionality.entity.doma;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/09/16-- 15:46
 * @describe:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auth {

    private String sessionId;
    private String ip;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auth auth = (Auth) o;
        return  Objects.equals(sessionId, auth.sessionId) && Objects.equals(ip, auth.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash( sessionId, ip);
    }
}
