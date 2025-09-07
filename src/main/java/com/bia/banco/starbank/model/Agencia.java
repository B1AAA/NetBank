package com.bia.banco.starbank.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "position_x", nullable = false)
    private Double positionX
;

    @Column(name = "position_y", nullable = false)
    private Double positionY
;

    @Column(name = "resultado", nullable = true)
    private Double resultado;
}
