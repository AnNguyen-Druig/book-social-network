package com.devteria.identity.configuration;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.devteria.identity.service.AuthenticationService;
import com.nimbusds.jwt.SignedJWT;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signerKey}")
    private String signerKey;

    private final AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    public CustomJwtDecoder(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // dùng để chuyển một JWT dạng chuỗi thành object Jwt
    // JWT has 3 parts:     'header.payload/claims.signature' ->
    // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbiIsImlzcyI6Im15LWFwcCJ9.signature'
    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            // dùng thư viện Nimbus JOSE JWT để parse chuỗi token thành object SignedJWT
            SignedJWT signedJWT = SignedJWT.parse(token);

            return new Jwt(
                    token, // String tokenValue
                    signedJWT.getJWTClaimsSet().getIssueTime().toInstant(), // Instant issuedAt
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(), // Instant expiresAt
                    signedJWT.getHeader().toJSONObject(), // Map<String, Object> headers
                    signedJWT.getJWTClaimsSet().getClaims()); // Map<String, Object> claims
        } catch (ParseException e) {
            throw new JwtException("Invalid JWT Token");
        }
    }
}
