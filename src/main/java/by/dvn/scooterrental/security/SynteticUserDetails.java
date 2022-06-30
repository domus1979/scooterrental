package by.dvn.scooterrental.security;

import by.dvn.scooterrental.model.account.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class SynteticUserDetails implements UserDetails {
    private String login;
    private String password;
    Collection<? extends GrantedAuthority> grantedAuthorities;

    public SynteticUserDetails(String login, String password, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.login = login;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }

    public static SynteticUserDetails fromUserToSynteticUserDetail(User user) {
        Collection<? extends GrantedAuthority> authorities= user.getRoleList().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
        return new SynteticUserDetails(user.getLogin(), user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
