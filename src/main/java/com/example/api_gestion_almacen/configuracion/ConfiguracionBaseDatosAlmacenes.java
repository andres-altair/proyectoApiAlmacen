package com.example.api_gestion_almacen.configuracion;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuración de la base de datos de almacenes.
 * Proporciona la configuración necesaria para conectar y gestionar la base de datos de almacenes.
 * @author andres
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.example.api_gestion_almacen.repositorios.almacenes",
    entityManagerFactoryRef = "almacenesEntityManagerFactory",
    transactionManagerRef = "almacenesTransactionManager"
)
public class ConfiguracionBaseDatosAlmacenes {

    /**
     * Define el DataSource para la base de datos de almacenes.
     * La configuración se obtiene del prefijo 'gestion-almacenes.datasource' en application.properties.
     */
    @Bean(name = "almacenesDataSource")
    @ConfigurationProperties(prefix = "gestion-almacenes.datasource")
    public DataSource origenDeDatosAlmacenes() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Define el EntityManagerFactory para la base de datos de almacenes.
     * Utiliza el DataSource configurado anteriormente y escanea las entidades del paquete correspondiente.
     */
    @Bean(name = "almacenesEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean fabricaEntidadAlmacenes(
            EntityManagerFactoryBuilder constructor,
            @Qualifier("almacenesDataSource") DataSource origenDeDatos) {
        return constructor
                .dataSource(origenDeDatos)
                .packages("com.example.api_gestion_almacen.entidades.almacenes")
                .persistenceUnit("almacenes")
                .build();
    }

    /**
     * Define el TransactionManager para la base de datos de almacenes.
     * Permite la gestión de transacciones JPA sobre este origen de datos.
     */
    @Bean(name = "almacenesTransactionManager")
    public PlatformTransactionManager gestorTransaccionesAlmacenes(
            @Qualifier("almacenesEntityManagerFactory") EntityManagerFactory fabrica) {
        return new JpaTransactionManager(fabrica);
    }
}