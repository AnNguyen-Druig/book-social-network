package com.devteria.profile.configuration;

import java.text.ParseException;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import com.nimbusds.jwt.SignedJWT;

/**
 * JwtDecoder tùy biến của profile-service.
 *
 * <p>Profile-service cần decoder này để Spring Security đọc được claim/scope từ JWT do
 * identity-service phát hành, từ đó áp dụng các rule như hasRole('ADMIN').
 */
@Component
public class CustomJwtDecoder implements JwtDecoder {
    /**
     * Parse chuỗi JWT thành Jwt object của Spring Security.
     */
    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            return new Jwt(
                    token,
                    signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
                    signedJWT.getHeader().toJSONObject(),
                    signedJWT.getJWTClaimsSet().getClaims());

        } catch (ParseException e) {
            throw new JwtException("Invalid token");
        }
    }
}
