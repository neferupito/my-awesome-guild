package io.neferupito.myawesomeguild.core.service.wow;

import io.neferupito.myawesomeguild.api.Response;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import io.neferupito.myawesomeguild.data.repository.wow.RealmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class WowDataService {

    @Autowired
    private RealmRepository realmRepository;

    public Response<List<Realm>> getAllRealms(Region region) {
        List<Realm> realms = realmRepository.findByRegion(region);
        Collections.sort(realms);
        return Response.<List<Realm>>builder()
                .isError(false)
                .content(realms)
                .build();
    }

}
