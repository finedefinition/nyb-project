package com.norwayyachtbrockers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "keel_types", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@JsonPropertyOrder({"keel_type_id", "keel_type_name"})
@Data
@NoArgsConstructor
public class Keel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("keel_type_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 40, unique = true)
    @JsonProperty("keel_type_name")
    private String name;

    @OneToMany(mappedBy = "keelType",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    private Set<YachtModel> yachtModels = new HashSet<>();

    public Keel(String name) {
        this.name = name;
    }

    // Convenience method to add a YachtModel to the Keel type
    public void addYachtModel(YachtModel yachtModel) {
        yachtModels.add(yachtModel);
        yachtModel.setKeelType(this);
    }

    // Convenience method to remove a YachtModel from the Keel type
    public void removeYachtModel(YachtModel yachtModel) {
        yachtModels.remove(yachtModel);
        yachtModel.setKeelType(null);
    }
}
