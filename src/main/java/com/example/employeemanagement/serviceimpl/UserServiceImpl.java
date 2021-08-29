package com.example.employeemanagement.serviceimpl;

import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.model.UserDetailsImpl;
import com.example.employeemanagement.repository.RoleRepository;
import com.example.employeemanagement.repository.UserRepository;
import com.example.employeemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Long saveUser(User user) {
        Set<Role> userRoles = new HashSet<>();

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        for (Role role : user.getRoles()) {

            Optional<Role> roleInDataBase = roleRepository.findByName(role.getName());

            if (roleInDataBase.isPresent()) {
                userRoles.add(roleInDataBase.get());
            } else {
                userRoles.add(role);
            }
        }
        user.setRoles(userRoles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    @Override
    public Optional<User> findUserByName(String userName) {
        return userRepository.findByName(userName);
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInstance = findUserByName(username);

        if (!userInstance.isPresent()) {
            throw new UsernameNotFoundException("User not exist");
        }

        return new UserDetailsImpl(userInstance.get());
    }
}
