package com.example.demo.user;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDTO create(CreateUserRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstname(request.firstname());
        userEntity.setLastname(request.lastname());
        userEntity.setBalance(BigInteger.ZERO);
        userRepository.save(userEntity);
        return toDTO(userEntity);
    }

    public UserDTO edit(Long id, EditUserRequest request) {
        UserEntity userEntity = findById(id);
        userEntity.setFirstname(request.firstname());
        userEntity.setLastname(request.lastname());
        userRepository.save(userEntity);
        return toDTO(userEntity);
    }

    @Transactional
    public void debitBalance(Long userId, BigInteger amount) {
        UserEntity user = findById(userId);
        BigInteger balance = user.getBalance();

        if (amount.compareTo(balance) > 0)
            throw new UserInsufficientBalanceException();

        user.setBalance(balance.subtract(amount));
        userRepository.save(user);
    }

    @Transactional
    public void creditBalance(Long userId, BigInteger amount) {
        UserEntity user = findById(userId);
        BigInteger balance = user.getBalance();

        user.setBalance(balance.add(amount));

        userRepository.save(user);
    }

    public PagedModel<UserDTO> findAll(Pageable page) {
        return new PagedModel<>(userRepository.findAll(page)
                .map(this::toDTO)
        );
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private UserDTO toDTO(UserEntity entity) {
        return toDTO(entity, new UserDTO());
    }

    private UserDTO toDTO(UserEntity entity, UserDTO dto) {
        dto.setId(entity.getId());
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());
        dto.setBalance(entity.getBalance());
        return dto;
    }

    public Long countUsers() {
        return userRepository.count();
    }
}
