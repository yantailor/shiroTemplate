package com.example.shirotemplate.utils.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.shirotemplate.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by yantailor
 * on 2022/8/1 22:42 @Version 1.0
 */
@Component
public class TokenUtil {
    /** Token 有效时长 多少秒 **/
    private static final Long EXPIRATION = 60 * 60L  * 1000;
    //私钥
    private static final String TOKEN_SECRET = "privateKey";

    /*
    * 创建token
    * */
    public String createToken(User user) {
        try {
            // Token 的过期时间
            Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            String token = JWT.create()
                              .withArrayClaim("permission",user.getPermission())
                              .withClaim("role",user.getRole())
                              .withExpiresAt(expirationDate)
                              .sign(algorithm);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    * 验证token是否正确
    * */
    public User verifyToken(String token){
        try {
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String[] permissions = jwt.getClaim("permission").asArray(String.class);
            String role = jwt.getClaim("role").asString();

            User user = new User();
            user.setRole(role);
            user.setPermission(permissions);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
