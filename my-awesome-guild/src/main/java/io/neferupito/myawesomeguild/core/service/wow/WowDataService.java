package io.neferupito.myawesomeguild.core.service.wow;

import io.neferupito.myawesomeguild.api.Response;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import io.neferupito.myawesomeguild.data.repository.wow.RealmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class WowDataService {

    @Autowired
    private RealmRepository realmRepository;

    public Response<List<Region>> getAllRegions() {
        return Response.<List<Region>>builder()
                .isError(false)
                .content(Arrays.asList(Region.values()))
                .build();
    }

    public Response<List<Realm>> getAllRealms(String regionS) {
        Region region = Region.valueOf(regionS);
        List<Realm> realms = realmRepository.findByRegion(region);
        Collections.sort(realms);
        return Response.<List<Realm>>builder()
                .isError(false)
                .content(realms)
                .build();
    }

}
