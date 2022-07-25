package com.moon.joyce.config;



import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Xing Dao Rong
 * @date 2021/9/14 15:21
 * @desc 用户拦截器配置
 */
@Configuration
public class JoyceInterceptorConfig implements WebMvcConfigurer, ErrorPageRegistrar {

    @Value("${file.upload.relative}")
    private String relative;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new UserInterceptor());
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns(
                relative,
                Constant.USER_LOGIN_PATH,
                Constant.USER_REGIST_PATH,
                Constant.APP_INDEX_PATH,
                "/example/sysMeun/doGetIndexMeun",
                "/example/user/doLogin",
                "/example/user/doSaveUser",
                "/example/user/getEmailCode",
                "/css/**",
                "/fontses/**",
                "/js/**",
                "/img/**",
                "/bootstrap-3.4.4/**",
                "/**/*.map",
                "/**/*.html",
                "/**/*.js",
                "/**/*.css",
                "/**/*.jpg",
                "/**/*.png",
                "/**/*.ttf",
                "/**/*.woff",
                "/**/*.map",
                "/**/*.eot",
                "/**/*.svg",
                "/example/source/getIndexVideo",
                "/static/video/indexVideo/video0default0name.mp4"
        );
    }

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage errorPage = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
        ErrorPage notFoundPage = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        registry.addErrorPages(notFoundPage);
        registry.addErrorPages(errorPage,notFoundPage);

    }
}
