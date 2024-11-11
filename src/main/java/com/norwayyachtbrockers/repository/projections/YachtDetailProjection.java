package com.norwayyachtbrockers.repository.projections;

public class YachtDetailProjection {
    private Long id;
    private Integer cabin;
    private Integer berth;
    private Integer heads;
    private Integer shower;
    private String description;

    public YachtDetailProjection(Long id, Integer cabin, Integer berth,
                                 Integer heads, Integer shower, String description) {
        this.id = id;
        this.cabin = cabin;
        this.berth = berth;
        this.heads = heads;
        this.shower = shower;
        this.description = description;
    }

    public YachtDetailProjection() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCabin() {
        return cabin;
    }

    public void setCabin(Integer cabin) {
        this.cabin = cabin;
    }

    public Integer getBerth() {
        return berth;
    }

    public void setBerth(Integer berth) {
        this.berth = berth;
    }

    public Integer getHeads() {
        return heads;
    }

    public void setHeads(Integer heads) {
        this.heads = heads;
    }

    public Integer getShower() {
        return shower;
    }

    public void setShower(Integer shower) {
        this.shower = shower;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
