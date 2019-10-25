package io.neferupito.myawesomeguild.data.domain.wow.character;

import lombok.Getter;

@Getter
public enum Role {

    HEALER("Soins"),
    DAMAGE("DPS"),
    TANK("Tank");

    private String name;

    Role(String name) {
        this.name = name;
    }
}
