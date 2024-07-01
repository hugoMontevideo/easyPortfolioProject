package com.simplon.easyportfolio.api.controllers.socials;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialUpdateDTO {
    private Optional<Long> id;
    @Pattern(regexp = "^https://.*", message = "Le lien doit commencer par 'https://'")
    @Size(min = 10, max = 255, message = "Le lien doit comporter entre 10 et 255 caractères")
    private String link;
    private Long categorySocialId;
    private Long portfolioId;
}
