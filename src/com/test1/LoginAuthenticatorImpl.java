package com.test1;

public class LoginAuthenticatorImpl implements Authenticator{

	@Override
	public void authen(String userId, String userPwd) throws UserException {
		
		if(!userId.equals("kim")||!userPwd.equals("123")){
			throw new UserException("invaild id : " + userId);
				
		}
		
	}
}
