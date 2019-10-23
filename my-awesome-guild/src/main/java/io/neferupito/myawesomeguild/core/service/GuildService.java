package io.neferupito.myawesomeguild.core.service;

import io.neferupito.myawesomeguild.api.domain.api.Response;
import io.neferupito.myawesomeguild.api.domain.wow.Guild;
import io.neferupito.myawesomeguild.core.transformer.GuildTransformer;
import io.neferupito.myawesomeguild.data.entity.GuildEntity;
import io.neferupito.myawesomeguild.data.repository.GuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuildService {

    @Autowired
    private GuildTransformer transformer;
    @Autowired
    private GuildRepository repository;

    public Response<Guild> getGuildByName(String region, String lang, String server, String slugName) {
//        Optional<GuildEntity> optional = repository.findBySlugName(slugName);
        List<GuildEntity> list = repository.findBySlugName(slugName);
//        if (optional.isPresent()) {
        if (!list.isEmpty()) {
            return Response.<Guild>builder()
                    .isError(false)
//                    .content(transformer.transformFromEntity(optional.get()))
                    .content(transformer.transformFromEntity(list.get(0)))
                    .build();
        } else {
            return Response.<Guild>builder()
                    .isError(true)
                    .errorHttpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

}
