package io.neferupito.myawesomeguild.core.service.wow;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import io.neferupito.myawesomeguild.data.repository.wow.RealmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class WowDataService {

    @Autowired
    private RealmRepository realmRepository;

    public List<Region> getAllRegions() {
        return Arrays.asList(Region.values());
    }

    public List<Realm> getAllRealms(String regionString) throws AwesomeException {
        Region region;
        try {
            region = Region.findValue(regionString);
        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.BAD_REQUEST, "Region " + regionString + " non reconnue");
        }
        List<Realm> realms = realmRepository.findByRegion(region);
        Collections.sort(realms);
        return realms;
    }

}
