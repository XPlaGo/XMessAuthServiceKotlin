package com.xplago.xmessauthservicekotlin

import org.modelmapper.ModelMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
class XMessAuthServiceKotlinApplication

fun main(args: Array<String>) {

    runApplication<XMessAuthServiceKotlinApplication>(*args)

    @Bean
    fun modelMapper(): ModelMapper = ModelMapper()
}
