package io.neferupito.myawesomeguild.data.domain.wow.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Battlegroup {

    @Id
    @GeneratedValue
    private Long id;
    private Region region;
    private String name;
    private String slug;

}
