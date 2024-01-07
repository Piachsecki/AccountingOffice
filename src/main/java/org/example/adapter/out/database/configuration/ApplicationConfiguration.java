package org.example.adapter.out.database.configuration;

import org.example.ComponentScanMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackageClasses = ComponentScanMarker.class)
@Import(PersistenceJPAConfiguration.class)
public class ApplicationConfiguration {
}