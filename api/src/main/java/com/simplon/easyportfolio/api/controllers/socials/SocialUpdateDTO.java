package com.simplon.easyportfolio.api.controllers.socials;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialUpdateDTO {
    private Optional<Long> id;
    @Pattern(regexp = ".{2,500}", message = "Le titre doit avoir entre 2 et 500 caractères")
    private String link;
    @Positive(message="Vous devez choisir une catégorie")
    private Long categorySocialId;
    private Long portfolioId;
}
