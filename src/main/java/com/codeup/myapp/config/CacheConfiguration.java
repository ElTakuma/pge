package com.codeup.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.codeup.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.codeup.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.codeup.myapp.domain.User.class.getName());
            createCache(cm, com.codeup.myapp.domain.Authority.class.getName());
            createCache(cm, com.codeup.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.codeup.myapp.domain.PersistentToken.class.getName());
            createCache(cm, com.codeup.myapp.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, com.codeup.myapp.domain.ClasseLt.class.getName());
            createCache(cm, com.codeup.myapp.domain.MatiereLt.class.getName());
            createCache(cm, com.codeup.myapp.domain.Professeur.class.getName());
            createCache(cm, com.codeup.myapp.domain.Eleve.class.getName());
            createCache(cm, com.codeup.myapp.domain.Note.class.getName());
            createCache(cm, com.codeup.myapp.domain.Matiere.class.getName());
            createCache(cm, com.codeup.myapp.domain.Bulettin.class.getName());
            createCache(cm, com.codeup.myapp.domain.Classe.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
