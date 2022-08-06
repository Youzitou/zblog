package com.github.stazxr.zblog.base.component.security.handler;

import com.github.stazxr.zblog.base.component.security.jwt.ZblogToken;
import com.github.stazxr.zblog.base.domain.entity.Permission;
import com.github.stazxr.zblog.base.domain.entity.User;
import com.github.stazxr.zblog.base.component.security.filter.CustomRememberMeFilter;
import com.github.stazxr.zblog.base.component.security.jwt.JwtTokenGenerator;
import com.github.stazxr.zblog.base.service.PermissionService;
import com.github.stazxr.zblog.base.service.UserService;
import com.github.stazxr.zblog.core.model.Result;
import com.github.stazxr.zblog.core.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * CustomAuthenticationSuccessHandler
 *
 * @author SunTao
 * @since 2020-11-15
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenGenerator jwtTokenGenerator;

    private final PermissionService permissionService;

    private final UserService userService;

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // get user
        User principal = (User) authentication.getPrincipal();

        // get permissions
        Long userId = principal.getId();
        List<Permission> userPerms = permissionService.queryPermsByUserId(userId);

        // generate token
        ZblogToken token = jwtTokenGenerator.getTokenResponse(principal);
        ZblogToken.AccessToken accessToken = token.getAccessToken();
        ZblogToken.RefreshToken refreshToken = token.getRefreshToken();

        // additional
        Map<String, Object> additional = token.getAdditional();
        additional.put("user", principal);
        additional.put("userPerms", userPerms);

        Map<String, Object> data = new HashMap<>(2);
        data.put("access_token", accessToken.getTokenType().concat(" ").concat(accessToken.getTokenValue()));
        data.put("refresh_token", refreshToken.getTokenValue());
        data.put("additional", additional);

        Boolean fromRememberMe = (Boolean) request.getAttribute(CustomRememberMeFilter.FROM_REMEMBER_ME);
        if (fromRememberMe != null && fromRememberMe) {
            // TODO 记住我认证成功
        }

        // set login time
        userService.updateUserLoginInfo(request, userId);

        // return
        ResponseUtils.responseJsonWriter(response, Result.success("登录成功").data(data));
    }
}
