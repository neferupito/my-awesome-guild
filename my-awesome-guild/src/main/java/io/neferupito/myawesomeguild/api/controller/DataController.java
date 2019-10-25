package io.neferupito.myawesomeguild.api.controller;

import io.neferupito.myawesomeguild.core.blizzard.client.ConstantDataImportBlizzardClient;
import io.neferupito.myawesomeguild.data.domain.wow.character.Race;
import io.neferupito.myawesomeguild.data.domain.wow.character.Specialization;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.repository.wow.RaceRepository;
import io.neferupito.myawesomeguild.data.repository.wow.RealmRepository;
import io.neferupito.myawesomeguild.data.repository.wow.SpecializationRepository;
import io.neferupito.myawesomeguild.data.repository.wow.WowClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {

    @Autowired
    private ConstantDataImportBlizzardClient client;

    @Autowired
    RaceRepository raceRepository;
    @Autowired
    SpecializationRepository specializationRepository;
    @Autowired
    RealmRepository realmRepository;

    @GetMapping("/data/gen")
    public ResponseEntity generate() {
        client.importAllData();
        return ResponseEntity.ok().build();
    }


    @GetMapping("/data/confirm")
    public ResponseEntity confirm() {
        for (Race race:
        raceRepository.findAll()) {
            System.err.println(race.toString());
        }
        System.err.println("===================================================");
        System.err.println("===================================================");
        for (Specialization spec :
                specializationRepository.findAll()) {
            System.err.println(spec.toString());
        }
        System.err.println("===================================================");
        System.err.println("===================================================");
        for (Realm realm:
             realmRepository.findAll()) {
            System.err.println(realm.toString());
        }

        return ResponseEntity.ok().build();
    }

}
