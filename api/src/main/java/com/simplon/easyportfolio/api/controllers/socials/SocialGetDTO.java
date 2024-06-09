package com.simplon.easyportfolio.api.controllers.socials;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialGetDTO {
    private Long Id;
    private String link;
    private Long categorySocialId;
}
