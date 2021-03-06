package com.jll.sys.security;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.jll.sys.log.LoginService;

@Component
public class LoginFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	private Logger logger = Logger.getLogger(LoginFailureListener.class);
	@Resource
	LoginService loginService;
	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		// TODO Auto-generated method stub
		logger.debug("login failure!!!");
		UsernamePasswordAuthenticationToken UPAT=(UsernamePasswordAuthenticationToken) event.getSource();
		String userName=(String) UPAT.getPrincipal();
//		String userName = user.getUsername();
		loginService.updateFailLogin(userName);
	}

}
