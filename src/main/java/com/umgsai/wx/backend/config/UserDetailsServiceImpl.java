package com.umgsai.wx.backend.config;

import com.google.common.collect.Lists;
import com.umgsai.wx.backend.dao.data.UserRoleConfigDO;
import com.umgsai.wx.backend.manager.AuthManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Shang
 * 2020/5/8 20:48
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private AuthManager authManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
                List<UserRoleConfigDO> userRoleConfigDOS = authManager.queryByUsername(username);
                for (UserRoleConfigDO userRoleConfigDO : userRoleConfigDOS) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(userRoleConfigDO.getRoleName()));
                }
                return grantedAuthorities;
            }

            @Override
            public String getPassword() {
                return "xxxx";
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
    }
}
