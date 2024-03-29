package com.cjw.toy.resolver;

import com.cjw.toy.annotation.SocialUser;
import com.cjw.toy.domain.User;
import com.cjw.toy.repository.UserRepository;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.cjw.toy.domain.enums.SocialType;

import static com.cjw.toy.domain.enums.SocialType.*;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private UserRepository userRepository;

    public UserArgumentResolver(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(SocialUser.class) != null &&
               parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        User user = (User) session.getAttribute("user");
        return getUser(user, session);
    }

    private User getUser(User user, HttpSession session) {
        if(user == null){
            try{
                OAuth2AuthenticationToken authentication =
                        (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                Map<String, Object> map = authentication.getPrincipal().getAttributes();
                User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(), map);
                user = userRepository.findByEmail(convertUser.getEmail());
                if(user==null){user = userRepository.save(convertUser);}
                setRoleIfNotSame(user, authentication, map);
                session.setAttribute("user",user);
            }catch (ClassCastException e){
                return user;
            }
        }

        return user;
    }

    private void setRoleIfNotSame(User user, OAuth2AuthenticationToken authentication, Map<String, Object> map) {
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))){
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(map, "N/A", AuthorityUtils.createAuthorityList(
                            user.getSocialType().getRoleType()
                    ))
            );
        }
    }

    private User convertUser(String authority, Map<String, Object> map){
        System.out.println(authority);
        if(FACEBOOK.getValue().equals(authority)) return getModernUser(FACEBOOK,map);
        else if(GOOGLE.getValue().equals(authority)) return getModernUser(GOOGLE,map);
        else if(KAKAO.getValue().equals(authority)) return getKakaoUser(map);
        return null;
    }

    private User getKakaoUser(Map<String, Object> map) {
        HashMap<String, String> propertyMap = (HashMap<String, String>)(Object) map.get("properties");
        System.out.println(propertyMap.get("nickname"));
        return User.builder().name(propertyMap.get("nickname"))
                .email(String.valueOf(map.get("kaccount_email")))
                .principal(String.valueOf(map.get("id")))
                .socialType(KAKAO)
                .createdDate(LocalDateTime.now())
                .build();
    }

    private User getModernUser(SocialType socialType, Map<String, Object> map) {
        System.out.println(map.toString());
        return User.builder()
                .name(String.valueOf(map.get("name")))
                .email(String.valueOf(map.get("email")))
                .principal(String.valueOf(map.get("id")))
                .socialType(socialType)
                .createdDate(LocalDateTime.now())
                .build();
    }


}
