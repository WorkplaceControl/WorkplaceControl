package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.UserRole;
import br.com.gft.MentorsDAO.UserRoleDAO;

public class UserRoleService {
	
	public UserRole getUserRole(int id){
		return new UserRoleDAO().findUserById(id);
	}
	
	public List<UserRole> getUserRoles(){
		return new UserRoleDAO().findUserRoles();
	}
	
}
