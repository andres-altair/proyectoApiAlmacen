package com.example.api_gestion_almacen.configuracion;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * Configuración de la base de datos de usuarios.
 * Proporciona la configuración para la base de datos de usuarios.
 * @author andres
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.example.api_gestion_almacen.repositorios.usuarios",
    entityManagerFactoryRef = "usuariosEntityManagerFactory",
    transactionManagerRef = "usuariosTransactionManager"
)   
/**
 * Configuración de la base de datos de usuarios.
 * Proporciona la configuración para la base de datos de usuarios.
 * @author andres
 */
public class ConfiguracionBaseDatosUsuarios {

    @Primary
    @Bean(name = "usuariosDataSource")
    @ConfigurationProperties(prefix = "gestion-usuarios.datasource")
    public DataSource origenDeDatosUsuarios() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "usuariosEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean fabricaEntidadUsuarios(
            EntityManagerFactoryBuilder constructor,
            @Qualifier("usuariosDataSource") DataSource origenDeDatos) {
        return constructor
                .dataSource(origenDeDatos)
                .packages("com.example.api_gestion_almacen.entidades.usuarios")
                .persistenceUnit("usuarios")
                .build();
    }

    @Primary
    @Bean(name = "usuariosTransactionManager")
    public PlatformTransactionManager gestorTransaccionesUsuarios(
            @Qualifier("usuariosEntityManagerFactory") EntityManagerFactory fabrica) {
        return new JpaTransactionManager(fabrica);
    }
}