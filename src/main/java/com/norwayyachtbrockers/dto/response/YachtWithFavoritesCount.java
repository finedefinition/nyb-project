package com.norwayyachtbrockers.dto.response;

import com.norwayyachtbrockers.model.Yacht;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YachtWithFavoritesCount {
    private Yacht yacht;
    private Long favoritesCount;

    public YachtWithFavoritesCount(Yacht yacht, Long favoritesCount) {
        this.yacht = yacht;
        this.favoritesCount = favoritesCount;
    }
}
