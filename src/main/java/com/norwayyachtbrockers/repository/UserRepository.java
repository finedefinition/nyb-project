package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.favouriteYachts WHERE u.id = :id")
    Optional<User> findByIdAndFetchYachtsEagerly(@Param("id") Long id);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.favouriteYachts")
    List<User> findAllAndFetchYachtsEagerly();

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Long findUserIdByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.cognitoSub = :cognitoSub")
Optional<User> findByCognitoSub(@Param("cognitoSub") String cognitoSub);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
