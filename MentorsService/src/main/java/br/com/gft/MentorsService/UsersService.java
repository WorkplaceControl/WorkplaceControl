package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.UserRole;
import br.com.gft.MentorsCommon.Users;
import br.com.gft.MentorsDAO.UsersDAO;

public class UsersService {
	
	
	public void addUser(Users user) throws Exception {
		UsersDAO.setup();
		UsersDAO usersdao = new UsersDAO();
		usersdao.insertUsers(user);
		
	}
	
	public void addUserRole(UserRole userrole) throws Exception {
		UsersDAO.setup();
		UsersDAO usersdao = new UsersDAO();
		usersdao.insertUserRole(userrole);
		
	}
	
	public void alterUser(Users user){
		UsersDAO.setup();
		UsersDAO usersdao = new UsersDAO();
		usersdao.updateUsers(user);
	}
	
	public Users getUser(String userId){
		Users user = new Users();
		UsersDAO.setup();
		UsersDAO userdao = new UsersDAO();
		user = userdao.findUser(userId);
		return user;
	}
	
	public List<Users> getUsers(){
		UsersDAO.setup();
		UsersDAO userdao = new UsersDAO();
		List<Users> user = userdao.findUsers();
		return user;
	}
}
