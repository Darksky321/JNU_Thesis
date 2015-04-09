package com.jnu.thesis.dao;

import java.util.Map;

public interface UserDao {

	public boolean addUser(String[] params);

	public boolean deleteUser(String[] params);

	public boolean updateUser(String id, String[] params);

	public Map<String, String> viewPerson(String[] selectionArgs);

	public Map<String, String> findAllUser();

	public String getCurrentUserId();
}
