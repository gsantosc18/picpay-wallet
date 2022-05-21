package com.picpay.wallet.config

import com.picpay.wallet.WalletApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    companion object {
        const val SERVICE_TITLE = "Desafio técnico PicPay"
        const val DESCRIPTION = "Desenvolvedor Backend"
        const val VERSION = "0.0.1"
    }

    @Bean
    fun greetingApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(WalletApplication::class.java.packageName))
            .build()
            .pathMapping("/")
            .apiInfo(metaData())
            .useDefaultResponseMessages(false)
    }

    private fun metaData(): ApiInfo {
        return ApiInfoBuilder()
            .title(SERVICE_TITLE)
            .description(DESCRIPTION)
            .version(VERSION)
            .build()
    }
}