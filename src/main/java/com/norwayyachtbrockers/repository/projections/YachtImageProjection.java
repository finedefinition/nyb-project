package com.norwayyachtbrockers.repository.projections;

public class YachtImageProjection {
    private Long id;
    private String imageKey;
    private Integer imageIndex;
    private Long yachtId;

    public YachtImageProjection(Long id, String imageKey, Integer imageIndex, Long yachtId) {
        this.id = id;
        this.imageKey = imageKey;
        this.imageIndex = imageIndex;
        this.yachtId = yachtId;
    }

    public YachtImageProjection() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public Integer getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(Integer imageIndex) {
        this.imageIndex = imageIndex;
    }

    public Long getYachtId() {
        return yachtId;
    }

    public void setYacht(Long yachtId) {
        this.yachtId = yachtId;
    }
}
