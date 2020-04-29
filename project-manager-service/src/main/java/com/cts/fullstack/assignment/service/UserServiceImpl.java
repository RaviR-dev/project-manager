package com.cts.fullstack.assignment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cts.fullstack.assignment.dto.UserDto;
import com.cts.fullstack.assignment.entities.User;
import com.cts.fullstack.assignment.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserFromUserDto(UserDto userDto) {
        User user;
        Optional<User> optionalUser;
        if (userDto.getUserId() != null && (optionalUser = userRepository.findById(userDto.getUserId())).isPresent()) {
            user = optionalUser.get();
        } else {
            user = new User();
        }
        user.setEmployeeId(userDto.getEmployeeId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        return user;
    }

    @Override
    public UserDto getUserDtoFromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setEmployeeId(user.getEmployeeId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }
}
