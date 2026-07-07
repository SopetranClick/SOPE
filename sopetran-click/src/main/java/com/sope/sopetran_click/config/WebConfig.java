package com.sope.sopetran_click.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración personalizada de Spring MVC para resolver la carga de recursos estáticos
 * de SopetranClick (imágenes, CSS, JS y vistas HTML secundarias).
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 1. SOLUCIÓN AL PREFIJO /static/:
        // Mapea peticiones que comiencen con "/static/**" directamente a "classpath:/static/"
        // Esto soluciona los errores de NoResourceFoundException para css, js e imágenes.
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        

        // 3. SOLUCIÓN PARA LA CARPETA /mennu/:
        // Mapea peticiones de sub-vistas HTML (como /mennu/MenuAlojamiento.html)
        // Busca tanto en "static/mennu/" como en una carpeta "mennu/" fuera de static.
        registry.addResourceHandler("/mennu/**")
                .addResourceLocations("classpath:/static/mennu/", "classpath:/mennu/");



        // 3. MAPEO ESTÁNDAR DE APOYO (Opcional pero recomendado):
        // Permite acceder a recursos omitiendo el prefijo "static" si el frontend lo requiere.
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
    }
}