package com.model.user;

public class UserInfo {
static User user;

public UserInfo(User user1) {

	user = user1;
}

public static User getUser() {
	return user;
}

public static void setUser(User user1) {
	user = user1;
}


	
}
