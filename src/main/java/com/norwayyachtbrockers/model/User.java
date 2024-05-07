package com.norwayyachtbrockers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.norwayyachtbrockers.model.enums.UserRoles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 30)
    @NotBlank(message="First name is required.")
    @Size(max = 30, message="First name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "First name must start with a capital letter and can" +
            " include letters, spaces, and hyphens.")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    @NotBlank(message="Last name is required.")
    @Size(max = 30, message="Last name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Last name must start with a capital letter and can" +
            " include letters, spaces, and hyphens.")
    private String lastName;

    @Column(name = "email", nullable = false, length = 30)
    @NotBlank(message = "User email is required.")
    @Email(message = "Please provide a valid email format.")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_roles", nullable = false)
    private UserRoles userRoles;

    @Column(name = "cognito_sub", nullable = false, length = 48)
    private String cognitoSub;

    @ManyToMany
    @JoinTable(
            name = "user_favourite_yachts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "yacht_id")
    )
    @JsonIgnore
    private Set<Yacht> favouriteYachts = new HashSet<>();

    // Convenience method to add a Yacht to the User's list of favourite yachts
    public void addFavouriteYacht(Yacht yacht) {
        this.favouriteYachts.add(yacht);
        yacht.getFavouritedByUsers().add(this);
    }

    // Convenience method to remove a Yacht from the User's list of favourite yachts
    public void removeFavouriteYacht(Yacht yacht) {
        this.favouriteYachts.remove(yacht);
        yacht.getFavouritedByUsers().remove(this);
    }

}
