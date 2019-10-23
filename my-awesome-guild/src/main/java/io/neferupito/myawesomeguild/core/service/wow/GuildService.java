package io.neferupito.myawesomeguild.core.service;

import io.neferupito.myawesomeguild.api.Response;
import io.neferupito.myawesomeguild.data.domain.wow.guild.Guild;
import io.neferupito.myawesomeguild.data.repository.GuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuildService {

    @Autowired
    private GuildRepository repository;

    public Response<Guild> getGuildByName(String region, String lang, String server, String slugName) {
//        Optional<GuildEntity> optional = repository.findBySlugName(slugName);
        List<Guild> list = repository.findBySlugName(slugName);
//        if (optional.isPresent()) {
        if (!list.isEmpty()) {
            return Response.<Guild>builder()
                    .isError(false)
//                    .content(transformer.transformFromEntity(optional.get()))
                    .content(list.get(0))
                    .build();
        } else {
            return Response.<Guild>builder()
                    .isError(true)
                    .errorHttpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

}
