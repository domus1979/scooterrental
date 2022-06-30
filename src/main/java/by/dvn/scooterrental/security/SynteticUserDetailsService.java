package by.dvn.scooterrental.security;

import by.dvn.scooterrental.model.account.User;
import by.dvn.scooterrental.repository.account.MySqlRepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SynteticUserDetailsService implements UserDetailsService {
    private MySqlRepoUser repoUser;

    @Autowired
    public SynteticUserDetailsService(MySqlRepoUser repoUser) {
        this.repoUser = repoUser;
    }

    @Override
    public SynteticUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repoUser.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found user with user name: " + username);
        }
        return SynteticUserDetails.fromUserToSynteticUserDetail(user);
    }

}
