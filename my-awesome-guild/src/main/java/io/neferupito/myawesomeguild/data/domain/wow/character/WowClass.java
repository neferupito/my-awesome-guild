package io.neferupito.myawesomeguild.data.domain.wow.character;

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
public class WowClass {

    @Id
    private Integer id;
    private String name;

}
