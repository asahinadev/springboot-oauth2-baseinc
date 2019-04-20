package com.example.spring.baseinc.oauth.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseincUser
		implements OAuth2User {

	@JsonProperty("user")
	Map<String, Object> user = new HashMap<>();

	@JsonAnySetter
	Map<String, Object> extraParameters = new HashMap<>();

	@Override
	@JsonIgnore
	public String getName() {
		return String.valueOf(getUser().get("shop_id"));
	}

	public String getEmail() {
		return String.valueOf(getUser().get("mail_address"));
	}

	@Override
	@JsonIgnore
	public List<GrantedAuthority> getAuthorities() {
		return Arrays.asList(
				new OAuth2UserAuthority("USER", getAttributes()),
				new SimpleGrantedAuthority("USER"));
	}

	@Override
	@JsonIgnore
	public Map<String, Object> getAttributes() {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("user", user);
		attributes.putAll(this.getExtraParameters());
		return this.getExtraParameters();
	}

	public String toString() {
		return getExtraParameters().toString();
	}

}
