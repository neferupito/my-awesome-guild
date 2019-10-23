package io.neferupito.myawesomeguild.data.entity;

import io.neferupito.myawesomeguild.api.domain.wow.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="langs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LangEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String slugName;
    private Region region;

}
