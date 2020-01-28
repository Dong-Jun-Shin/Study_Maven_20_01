package model;

public class EmailVO {
	private String googleId;
	private String googlePwd;
	private String emailTo;
	private String emailToName;
	private String emailFrom;
	private String emailFromName;
	private String emailSubject;
	private String emailMsg;

	public EmailVO() {
	}

	public EmailVO(String googleId, String googlePwd, String emailTo, String emailToName, String emailFrom,
			String emailFromName, String emailSubject, String emailMsg) {
		super();
		this.googleId = googleId;
		this.googlePwd = googlePwd;
		this.emailTo = emailTo;
		this.emailToName = emailToName;
		this.emailFrom = emailFrom;
		this.emailFromName = emailFromName;
		this.emailSubject = emailSubject;
		this.emailMsg = emailMsg;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getGooglePwd() {
		return googlePwd;
	}

	public void setGooglePwd(String googlePwd) {
		this.googlePwd = googlePwd;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailToName() {
		return emailToName;
	}

	public void setEmailToName(String emailToName) {
		this.emailToName = emailToName;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailFromName() {
		return emailFromName;
	}

	public void setEmailFromName(String emailFromName) {
		this.emailFromName = emailFromName;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailMsg() {
		return emailMsg;
	}

	public void setEmailMsg(String emailMsg) {
		this.emailMsg = emailMsg;
	}

	@Override
	public String toString() {
		return "EmailVO [googleId=" + googleId + ", googlePwd=" + googlePwd + ", emailTo=" + emailTo + ", emailToName="
				+ emailToName + ", emailFrom=" + emailFrom + ", emailFromName=" + emailFromName + ", emailSubject="
				+ emailSubject + ", emailMsg=" + emailMsg + "]";
	}

}
