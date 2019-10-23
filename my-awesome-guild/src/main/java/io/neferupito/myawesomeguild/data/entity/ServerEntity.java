package io.neferupito.myawesomeguild.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="servers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String slugName;
    @ManyToOne
    private LangEntity lang;

}
