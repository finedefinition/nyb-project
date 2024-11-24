package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.UserMapper;
import com.norwayyachtbrockers.dto.response.UserFavouriteYachtsResponseDto;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.UserRepository;
import com.norwayyachtbrockers.repository.YachtRepository;
import com.norwayyachtbrockers.service.UserService;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final YachtRepository yachtRepository;

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto findId(Long id) {
        User user = userRepository.findByIdAndFetchYachtsEagerly(id)
                .orElseThrow(() -> new AppEntityNotFoundException("User not found with id " + id));
        return UserMapper.convertUserToDto(user);
    }

    @Override
    public Long findUserIdByEmail(String email) {
        return userRepository.findUserIdByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAllProjections()
                .stream()
                .map(UserMapper::convertUserToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User updateUser(User user, Long id) {
    User existingUser = userRepository.findByIdAndFetchYachtsEagerly(id)
            .orElseThrow(() -> new AppEntityNotFoundException("User not found with id " + id));

    // Update the existing user's fields with the new values
    existingUser.setFirstName(user.getFirstName());
    existingUser.setLastName(user.getLastName());
    existingUser.setEmail(user.getEmail());
    existingUser.setUserRoles(user.getUserRoles());
    existingUser.setCreatedAt(user.getCreatedAt());
    // Preserve cognitoSub and other fields
    existingUser.setCognitoSub(user.getCognitoSub());

    return userRepository.save(existingUser);
}

    @Override
    @Transactional
    public void deleteById(Long id) {
        User userNew = userRepository.findByIdAndFetchYachtsEagerly(id)
                .orElseThrow(() -> new AppEntityNotFoundException("User not found with id " + id));
        userRepository.delete(userNew);

    }

    // Favourites
    @Override
    @Transactional
    public void addFavouriteYachtToUser(String cognitoSub, Long yachtId) {
        User user = userRepository.findByCognitoSub(cognitoSub)
                .orElseThrow(() -> new AppEntityNotFoundException("User not found"));
        Yacht yacht = yachtRepository.findById(yachtId)
                .orElseThrow(() -> new AppEntityNotFoundException("Yacht not found with id " + yachtId));
        // Check if the yacht is already in the user's favorites
        if (!user.getFavouriteYachts().contains(yacht)) {
            user.getFavouriteYachts().add(yacht);
            userRepository.save(user);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserFavouriteYachtsResponseDto getFavouriteYachts(String cognitoSub) {
        User user = userRepository.findByCognitoSub(cognitoSub)
                .orElseThrow(() -> new AppEntityNotFoundException("User not found"));

        Set<Long> yachtIds = user.getFavouriteYachts().stream()
                .map(Yacht::getId)
                .collect(Collectors.toSet());

        UserFavouriteYachtsResponseDto dto = new UserFavouriteYachtsResponseDto();
        dto.setUserId(user.getId());
        dto.setFavouriteYachtIds(yachtIds);
        dto.setCount(yachtIds.size());

        return dto;
    }

    @Override
    @Transactional
    public void removeFavouriteYacht(String cognitoSub, Long yachtId) {
        User user = userRepository.findByCognitoSub(cognitoSub)
                .orElseThrow(() -> new AppEntityNotFoundException("User not found"));

        Yacht yacht = yachtRepository.findById(yachtId)
                .orElseThrow(() -> new AppEntityNotFoundException("Yacht not found with id " + yachtId));

        boolean removed = user.getFavouriteYachts().removeIf(y -> y.getId().equals(yachtId));
        if (!removed) {
            // Throw an exception if the yacht was not in the user's favourites
            throw new AppEntityNotFoundException("Yacht not found in user's favourites");
        }

        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
