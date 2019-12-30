package io.neferupito.myawesomeguild.core.service.wow;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.data.domain.wow.guild.Guild;
import io.neferupito.myawesomeguild.data.domain.wow.guild.GuildAuthConfig;
import io.neferupito.myawesomeguild.data.repository.wow.GuildAuthConfigRepository;
import io.neferupito.myawesomeguild.data.repository.wow.GuildRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GuildAuthConfigService {

    @Autowired
    private GuildService guildService;
    @Autowired
    private GuildAuthConfigRepository authConfigRepository;
    @Autowired
    private GuildRepository guildRepository;

    public void addGuildAuthConfig(Long guildId, GuildAuthConfig guildAuthConfig) throws AwesomeException {
        Guild guild = guildService.getGuildById(guildId);
        guildAuthConfig = authConfigRepository.save(guildAuthConfig);
        guild.setAuthConfig(guildAuthConfig);
        guildRepository.save(guild);
    }

    public GuildAuthConfig getGuildAuthConfig(Long guildId) throws AwesomeException {
        Guild guild = guildService.getGuildById(guildId);
        if (guild.getAuthConfig() != null) {
            return guild.getAuthConfig();
        } else {
            throw new AwesomeException(HttpStatus.NOT_FOUND, "Pas de Auth Config");
        }
    }

    public GuildAuthConfig modifyGuildAuthConfig(Long guildId, GuildAuthConfig guildAuthConfig) throws AwesomeException {
        Guild guild = guildService.getGuildById(guildId);
        if (guild != null && guild.getAuthConfig() != null) {
            return authConfigRepository.save(guildAuthConfig);
        } else {
            throw new AwesomeException(HttpStatus.NOT_FOUND, "Pas de Auth Config");
        }
    }

}
