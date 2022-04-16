package sn.ept.git.seminaire.cicd.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import sn.ept.git.seminaire.cicd.utils.SizeMapping;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mc_societes")
@Where(clause = BaseEntity.CLAUSE)
public class Societe extends BaseEntity {

    @NotBlank
    @Size(min = SizeMapping.Name.MIN, max = SizeMapping.Name.MAX)
    @Column(unique = true)
    private String name;

    @Size(min = SizeMapping.Adresse.MIN, max = SizeMapping.Adresse.MAX)
    private String address;

    @Size(min = SizeMapping.Phone.MIN, max = SizeMapping.Phone.MAX)
    @Column(unique = true)
    private String phone;

    @Size(min = SizeMapping.Email.MIN, max = SizeMapping.Email.MAX)
    @Column(unique = true)
    private String email;

    private float longitude;

    private float latitude;

    @Where(clause = BaseEntity.CLAUSE)
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(
            mappedBy = "societe",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private Set<Site> sites = new HashSet<>();

    @Where(clause = BaseEntity.CLAUSE)
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(
            mappedBy = "societe",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private Set<Exercice> exercices = new HashSet<>();


    public Societe updateWith(Societe societe) {
        return Societe
                .builder()
                .id(this.getId())
                .address(societe.getAddress())
                .phone(societe.getPhone())
                .email(societe.getEmail())
                .longitude(societe.getLongitude())
                .latitude(societe.getLatitude())
                .build();

    }

}