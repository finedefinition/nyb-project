package com.norwayyachtbrockers.dto.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserFavouriteYachtsResponseDto {
    private Long userId;
    private Set<Long> favouriteYachtIds = new HashSet<>();
    private int count;

}
