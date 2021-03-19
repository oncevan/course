package com.course.business.config;

import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
//prefix+name通过application.yml文件配置是否启动swagger在线生成文档
@ConditionalOnProperty(prefix = "swagger", name = "open", havingValue = "true")
public class SwaggerConfig  extends WebMvcConfigurationSupport {

    /**
     * 创建获取api应用
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //这里采用包含注解的方式来确定要显示的接口(建议使用这种)
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//错误路径不监控
                .build();
    }

    /**
     * 配置swagger文档显示的相关内容标识(信息会显示到swagger页面)
     *
     * @return
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("xxxxx有限公司", "www.sss.com", "666666666@qq.com");
        return new ApiInfo("xxx项目",
                "xxx项目,此接口文档仅供内部查看",
                "v1.0",
                "www.xxx.com",
                contact, "xxx执照",
                "xxx执照URL",
                new ArrayList<>()
        );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        super.addResourceHandlers(registry);
    }
}


