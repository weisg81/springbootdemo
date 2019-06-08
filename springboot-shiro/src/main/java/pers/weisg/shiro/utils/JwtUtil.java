package pers.weisg.shiro.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 这个类作用是什么
 * Author WEISANGENG
 * Date 2019/5/19
 **/
public class JwtUtil {
    // 过期时间24小时
    private static final long EXPIRE_TIME = 60 * 60 * 1000;

    //WEISG_SHIRO_DEMO  MessageDigest.getInstance("MD5") new BigInteger(1, md.digest()).toString(16)
    private static final String SECRET = "79d0b15834dc293eae65cc52dcb7c27a";

    /**
     * 初始化head部分的数据为 { "alg":"HS256", "type":"JWT" }
     */
    /*private static final JWSHeader header = new JWSHeader(JWSAlgorithm.HS256, JOSEObjectType.JWT, null, null, null,
            null, null, null, null, null, null, null, null);*/

    private static final Map<String,Object> header = new HashMap<>();
    static {
        header.put("alg", "HS512");
        //header.put("alg", "HS256");
        //header.put("type", "JWT");
    }

    /**
     * 生成签名,5min后过期
     *
     * @param userId 用户id
     * @return 加密的token
     */
    public static String createToken(Long userId, String username, String password) {
        long currentTimeMillis = System.currentTimeMillis();
        Date expiresAt = new Date(currentTimeMillis + EXPIRE_TIME);
        Date issuedAt = new Date(currentTimeMillis);
        //Algorithm algorithm = Algorithm.HMAC256(secret);
        Algorithm algorithm = Algorithm.HMAC512(SECRET+userId);

        String userToken = JWT.create().withHeader(header)//header
                //Payload
                .withClaim("userId", userId)//转换成String
                .withClaim("username", username)
                .withClaim("password", password)
                .withExpiresAt(expiresAt)//exp：到期时间
                .withIssuedAt(issuedAt)//iat：发布时间
                .sign(algorithm);//创建一个新的JWT，并使用给定的算法签名
        DecodedJWT jwt = JWT.decode(userToken);
        Long userId2 = jwt.getClaim("userId").asLong();

        //Algorithm algorithm = Algorithm.HMAC512(SECRET+userId);
        /*JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("userId", userId+"")
                .build();
        //效验TOKEN
        DecodedJWT verify = verifier.verify(userToken);
        String payload = verify.getPayload();*/

        return userToken;

    }

    public static void main(String[] args) throws Exception {
        /*String token = createToken(1L);
        System.out.println(token);
        verify(token, 1L);*/
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update("WEISG_SHIRO_DEMO".getBytes());
        String md5Str = new BigInteger(1, md.digest()).toString(16);
        System.out.println(md5Str);//79d0b15834dc293eae65cc52dcb7c27a
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @return 是否正确
     */
    public static boolean verifyToken(String token, Long userId) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC512(SECRET+userId);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", userId)
                    .build();
            //效验TOKEN
            verifier.verify(token);
            /*JwtParser jwtParser = Jwts.parser().setSigningKey((SECRET).getBytes());
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
            Map<String,Object> payload = jwtParser.parseClaimsJws(token).getBody();*/
            //claims
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static Claim getClaim(String token, String claimName) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Claim claim = jwt.getClaim(claimName);
            return claim;
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }


}
