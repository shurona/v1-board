package shurona.board.config.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String JWT_AUTH_KEY = "jwt-auth";
    private final String secret;
    private final long tokenValidityMilliseconds;
    private Key key;

    /**
     * jwt 초기 value 설정
     */
//    @Autowired
    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") String tokenValidityMilliseconds) {
        this.secret = secret;
        this.tokenValidityMilliseconds = Long.parseLong(tokenValidityMilliseconds) * 1000;
    }

    /**
     * jwt key 설정
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date untilValidate = new Date(now + this.tokenValidityMilliseconds);

        return Jwts.builder()
                // jwt 토큰의 주제를 설정한다. 토큰이 누구에 의해 발급이 되었는지 확인한다.
                .setSubject(authentication.getName())
                // 사용자 정의 claim
                .claim(JWT_AUTH_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(untilValidate)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(JWT_AUTH_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        //TODO: principal 생성 부분 확인
        System.out.println("서브젝 : " + claims.getSubject());
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validatedToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            this.log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            this.log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            this.log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            this.log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;

    }
}
