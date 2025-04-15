package javafive.service.implement;



import java.util.Collection;


import javafive.dao.UserDAO;
import javafive.entity.User;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDAO  userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(identifier, identifier)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Collection<SimpleGrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
        	    .map(auth -> new SimpleGrantedAuthority("ROLE_" + auth.getRole().getId())) 
        	    .toList();
        System.out.println("User: " + user.getUsername());
        user.getAuthorities().forEach(auth -> {
            System.out.println("Authority: " + auth.getRole().getName());
        });

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            grantedAuthorities
        );
    }

}
