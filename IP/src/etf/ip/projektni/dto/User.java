package etf.ip.projektni.dto;

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


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appNotification == null) ? 0 : appNotification.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((emailNotification == null) ? 0 : emailNotification.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + (int) (loginCounter ^ (loginCounter >>> 32));
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((profilePicturex == null) ? 0 : profilePicturex.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (appNotification == null) {
			if (other.appNotification != null)
				return false;
		} else if (!appNotification.equals(other.appNotification))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (emailNotification == null) {
			if (other.emailNotification != null)
				return false;
		} else if (!emailNotification.equals(other.emailNotification))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (loginCounter != other.loginCounter)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (profilePicturex == null) {
			if (other.profilePicturex != null)
				return false;
		} else if (!profilePicturex.equals(other.profilePicturex))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
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
