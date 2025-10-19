package com.instant.data.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

/**
 * Entity representing a parking lot in a {@link City}.
 */
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true)
@Table(name = "parkingLot")
@EntityListeners(AuditingEntityListener.class)
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    private Position position; // not annotated as not null since it can be null in some cases -> should we keep these lots ?

    @NaturalId // not ideal since 2 parking lots can have the same name in the same city (also @NaturalId on city), but good enough for now
    @NotNull
    @NotEmpty
    private String name;

    @Embedded
    private ParkingLotDetails details;

    @NaturalId
    @ManyToOne
    @JoinColumn(name = "city_name", referencedColumnName = "name", nullable = false)
    private City city;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant modifiedAt;

    @PrePersist
    @PreUpdate
    private void uppercaseName() {
        name = name.toUpperCase();
    }
}
