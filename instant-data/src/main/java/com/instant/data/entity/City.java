package com.instant.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * Entity representing a city containing {@link ParkingLot}s.
 */
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NaturalId // not ideal here since 2 cities could have the same name but ok for now
    @NotNull
    private String name;

    private Position position; // always null for now (should get it for enhanced perfs: narrowing it down the search to find nearest lots)

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private Set<ParkingLot> parkingLots;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant modifiedAt;

    /**
     * Constructs a new instance with the given name.
     *
     * @param name the name of the city
     */
    public City(String name) {
        this.name = name;
    }

    @PrePersist
    @PreUpdate
    private void uppercaseName() {
        name = name.toUpperCase();
    }
}
