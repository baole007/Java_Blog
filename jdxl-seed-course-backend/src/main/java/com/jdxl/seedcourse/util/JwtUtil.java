package com.jdxl.seedcourse.util;

import com.alibaba.fastjson.JSON;
import com.cloud.utils.DateUtils;
import com.google.common.base.CharMatcher;
import com.google.common.io.BaseEncoding;
import com.jdxl.bean.commonBean.JwtUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

    @PostConstruct
    public void init() {
        java.security.Security.addProvider(
                new org.bouncycastle.jce.provider.BouncyCastleProvider()
        );

        String rsaPrivateKey = readResourceKey("rsa_private_key.pem");
        String rsaPublicKey = readResourceKey("rsa_public_key.pem");

        if (StringUtils.isEmpty(rsaPrivateKey) || StringUtils.isEmpty(rsaPublicKey)) {

            log.info("RSA private key or public key empty");
            return;
        }

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            byte[] keyBytes = BaseEncoding.base64().decode(CharMatcher.whitespace().removeFrom(rsaPrivateKey));
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            privateKey = keyFactory.generatePrivate(keySpec);

            byte[] pubKeyBytes = BaseEncoding.base64().decode(CharMatcher.whitespace().removeFrom(rsaPublicKey));
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pubKeyBytes);
            publicKey = keyFactory.generatePublic(pubKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成JWT token
     */
    public String generateToken(JwtUserInfo user) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(JSON.toJSONString(user))
                .setIssuedAt(DateUtils.nowDate()) // JWT 的生成时间
                .setExpiration(new Date(DateUtils.plus("1d"))) // JWT 的过期时间
                .signWith(signatureAlgorithm, privateKey)
                .compact();
    }

    /**
     * refresh token
     */
    public String generateRefreshToken(JwtUserInfo user) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(JSON.toJSONString(user))
                .setIssuedAt(DateUtils.nowDate()) // JWT 的生成时间
                .setExpiration(new Date(DateUtils.plus("1y"))) // JWT 的过期时间
                .signWith(signatureAlgorithm, privateKey)
                .compact();
    }

    /**
     * 解析 claim
     *
     * @param token
     * @return
     */
    public Claims getClaimByToken(String token) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    }

    /**
     * JWT 解析 UserInfo
     *
     * @param claims
     * @return
     */
    public JwtUserInfo getJwtUserInfo(Claims claims) {
        return JSON.parseObject(claims.getSubject(), JwtUserInfo.class);
    }

    public JwtUserInfo getJwtUserInfo(String token) {
        Claims claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        return JSON.parseObject(claims.getSubject(), JwtUserInfo.class);
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public boolean isTokenExpired(String token) {
        Claims claims = this.getClaimByToken(token);
        return claims.getExpiration().before(DateUtils.nowDate());
    }

    private String readResourceKey(String fileName) {

        String key = null;

        try {

            ClassPathResource classPathResource = new ClassPathResource(fileName);
            InputStream inputStream = classPathResource.getInputStream();
            File file = File.createTempFile(fileName, ".pem");

            try {
                FileUtils.copyInputStreamToFile(inputStream, file);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }

            List<String> lines = FileUtils.readLines(file, Charset.defaultCharset());
            lines = lines.subList(1, lines.size() - 1);
            key = lines.stream().collect(Collectors.joining());
        } catch (IOException e) {

            if (e instanceof FileNotFoundException) {
                log.warn("JWT RSA file load error: {}", e.getMessage());
            } else {
                log.error("JWT read file error!", e);
            }
        }

        return key;
    }

    private String expire;
    private String refreshExpire;
    private String header;

    private PrivateKey privateKey;
    private PublicKey publicKey;

}