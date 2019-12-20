package io.neferupito.myawesomeguild.data.domain.wow.character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Specialization {

    @Id
    private Integer id;
    private String name;
    @ManyToOne
    private WowClass wowClass;
    @Enumerated(EnumType.STRING)
    private Role role;

}
