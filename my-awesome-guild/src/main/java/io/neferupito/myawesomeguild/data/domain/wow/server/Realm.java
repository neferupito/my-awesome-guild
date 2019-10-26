package io.neferupito.myawesomeguild.data.domain.wow.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Realm implements Comparable<Realm> {

    @Id
    private Integer id;
    private Region region;
    private String name;
    private String slug;

    @Override
    public int compareTo(Realm realm) {
        return this.slug.compareTo(realm.slug);
    }
}
