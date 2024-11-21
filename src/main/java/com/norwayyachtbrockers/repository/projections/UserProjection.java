package com.norwayyachtbrockers.repository.projections;

import com.norwayyachtbrockers.model.enums.UserRoles;

public class UserProjection {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRoles userRoles;
    private String cognitoSub;

    public UserProjection(Long id, String firstName, String lastName, String email, UserRoles userRoles, String cognitoSub) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRoles = userRoles;
        this.cognitoSub = cognitoSub;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoles getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }

    public String getCognitoSub() {
        return cognitoSub;
    }

    public void setCognitoSub(String cognitoSub) {
        this.cognitoSub = cognitoSub;
    }
}
