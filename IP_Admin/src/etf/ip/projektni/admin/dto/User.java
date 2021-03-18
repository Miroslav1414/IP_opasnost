package etf.ip.projektni.admin.dto;

import java.io.Serializable;

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String profilePicturex;
    private String state;
    private String region;
    private String city;
    private String flag;
	private String emailNotification;
    private String appNotification;
    private long loginCounter;
    private boolean blocked;
    private boolean registrationApproved;
	
	
	
	public User(String username, String password, String firstName, String lastName, String email) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

    
    public User(String username, String password, String firstName, String lastName, String email,
			String profilePicturex, String state, String region, String city, String flag, String emailNotification,
			String appNotification, long loginCounter,boolean blocked,boolean registrationApproved) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.profilePicturex = profilePicturex;
		this.state = state;
		this.region = region;
		this.city = city;
		this.flag = flag;
		this.emailNotification = emailNotification;
		this.appNotification = appNotification;
		this.loginCounter = loginCounter;
		this.blocked = blocked;
		this.registrationApproved = registrationApproved;
	}


	

	public String getProfilePicturex() {
		return profilePicturex;
	}


	public void setProfilePicturex(String profilePicturex) {
		this.profilePicturex = profilePicturex;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getEmailNotification() {
		return emailNotification;
	}


	public void setEmailNotification(String emailNotification) {
		this.emailNotification = emailNotification;
	}


	public String getAppNotification() {
		return appNotification;
	}


	public void setAppNotification(String appNotification) {
		this.appNotification = appNotification;
	}


	public long getLoginCounter() {
		return loginCounter;
	}
	public void setLoginCounter(long loginCounter) {
		this.loginCounter = loginCounter;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isBlocked() {
		return blocked;
	}


	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}


	public boolean isRegistrationApproved() {
		return registrationApproved;
	}


	public void setRegistrationApproved(boolean registrationApproved) {
		this.registrationApproved = registrationApproved;
	}
	
	
}
