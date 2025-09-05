package com.bia.banco.starbank.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgenciaResponse {
    private Long id;
    private Double posX;
    private Double posY;
}