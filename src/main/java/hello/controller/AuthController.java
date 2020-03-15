package hello.controller;

import hello.entity.LoginResult;
import hello.service.AuthService;
import hello.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@Controller
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @Inject
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          AuthService authService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @GetMapping("/auth")
    @ResponseBody
    public LoginResult auth() {
        return authService.getCurrentUser()
                .map(LoginResult::success)
                .orElse(LoginResult.success("用户没有登录", false));
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public LoginResult logout() {
        LoginResult ret = authService.getCurrentUser()
                .map(user -> LoginResult.success("success", false))
                .orElse(LoginResult.failure("用户没有登录"));
        SecurityContextHolder.clearContext();
        return ret;
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public LoginResult register(@RequestBody Map<String, Object> usernameAndPassword, HttpServletRequest request) {
        String username = (String) usernameAndPassword.get("username");
        String password = (String) usernameAndPassword.get("password");
        if (username == null || password == null) {
            return LoginResult.failure("username/password == null");
        }
        if (username.length() < 1 || username.length() > 15) {
            return LoginResult.failure("invalid username");
        }
        if (password.length() < 1 || password.length() > 15) {
            return LoginResult.failure("invalid password");
        }

        try {
            userService.save(username, password);
        } catch (DuplicateKeyException e) {
            return LoginResult.failure("user already exists");
        }
        login(usernameAndPassword, request);
        return LoginResult.success("注册成功", userService.getUserByUsername(username));
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Object login(@RequestBody Map<String, Object> usernameAndPassword, HttpServletRequest request) {
        if (request.getHeader("user-agent") == null || !request.getHeader("user-agent").contains("Mozilla")) {
            return "死爬虫去死吧";
        }

        String username = usernameAndPassword.get("username").toString();
        String password = usernameAndPassword.get("password").toString();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());

        try {
            authenticationManager.authenticate(token);
            // 把用户信息保存在一个地方
            //   Cookie
            SecurityContextHolder.getContext().setAuthentication(token);

            return LoginResult.success("登录成功", userService.getUserByUsername(username));
        } catch (UsernameNotFoundException e) {
            return LoginResult.failure("用户不存在");
        } catch (BadCredentialsException e) {
            return LoginResult.failure("密码不正确");
        }
    }
}
