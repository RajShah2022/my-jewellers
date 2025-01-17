package com.my_jewellers.my_jewellers.image;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {
    private Integer id;
    private String imageName;
    private String imageType;
}
