package com.web.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Table(name = "sys_user_security")
@NameStyle(Style.camelhumpAndLowercase)
public class UserSecurity extends BaseModel implements UserDetails {
	
	private static final long serialVersionUID = 4066982294991357430L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "ID", hidden = true)
	private Long id;
	
	@ApiModelProperty(value = "用户名")
    private Long username;
	
	@JsonIgnore
	@ApiModelProperty(value = "密码", hidden = true)
    private String password;
	
	@Column(name = "account_non_expired")
	@ApiModelProperty(value = "账户是否没有过期")
    private boolean accountNonExpired;
	
	@Column(name = "account_non_locked")
	@ApiModelProperty(value = "账户是否没有被锁定")
    private boolean accountNonLocked;
    
	@Column(name = "credentia_isn_on_expired")
	@ApiModelProperty(value = "密码是否没有过期")
    private boolean credentialsNonExpired;
    
	@Column(name = "enabled")
	@ApiModelProperty(value = "账户是否可用")
    private boolean enabled;
    
	@JsonIgnore
	@ApiModelProperty(value = "权限列表")
    private List<Role> roles;

	/**
	 * 返回用户的角色信息
	 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
	
}
