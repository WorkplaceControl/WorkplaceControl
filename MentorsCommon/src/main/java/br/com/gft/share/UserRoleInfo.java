package br.com.gft.share;

public enum UserRoleInfo {

	Admin("ROLE_ADMIN", 1),
	User("ROLE_USER", 2);

	private final String value;
	private Integer index;

	UserRoleInfo(String value, Integer index) {
		this.value = value;
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public Integer getIndex() {
		return index;
	}

}
