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
@Table(name = "servers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Realm {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String slugName;
    //    @ManyToOne
    private Locale locale;

}
