package com.kenko.kenko.Repository;

import com.kenko.kenko.entity.ERole;
import com.kenko.kenko.entity.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Optional;

@RepositoryRestResource
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);

    @Configuration
    class RepositoryConfig implements RepositoryRestConfigurer {
        @Override
        public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
            config.exposeIdsFor(Role.class);
        }
    }

}
