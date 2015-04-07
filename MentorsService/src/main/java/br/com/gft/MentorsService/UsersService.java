package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.Users;
import br.com.gft.MentorsDAO.UsersDAO;

public class UsersService {

	public void addUser(Users user) throws Exception {
		new UsersDAO().insertUsers(user);
	}
	
	public void alterUser(Users user){
		new UsersDAO().updateUsers(user);
	}
	
	public Users getUser(String username){
		return new UsersDAO().findUser(username);
	}
	
	public List<Users> getUsers(){
		return new UsersDAO().findUsers();
	}
	
	public List<Users> getUsersInactive(){
		return new UsersDAO().findUsersInactive();
	}
	
	public List<Users> getUsers(int begin, int quantity){
		return new UsersDAO().findUsers(begin, quantity);
	}
	
	public List<Users> getUsersInactive(int begin, int quantity){
		return new UsersDAO().findUsersInactive(begin, quantity);
	}
	
}
