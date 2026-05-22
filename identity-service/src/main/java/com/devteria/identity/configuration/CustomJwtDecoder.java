package com.devteria.identity.configuration;

import java.text.ParseException;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import com.nimbusds.jwt.SignedJWT;

/**
 * JwtDecoder tùy biến để chuyển chuỗi JWT thành đối tượng Jwt của Spring Security.
 *
 * <p>Spring Security cần Jwt object để đọc claim như subject, scope và thời hạn token trong
 * các bước xác thực/phân quyền.
 */
@Component
public class CustomJwtDecoder implements JwtDecoder {
    /**
     * Parse token bằng Nimbus JWT rồi map header/claim sang Jwt chuẩn của Spring.
     *
     * <p>Nếu token không đúng định dạng JWT, method ném JwtException để Spring Security
     * xử lý như một request không hợp lệ.
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
