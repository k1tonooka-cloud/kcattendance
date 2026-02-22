package jp.co.kycm.kcattendance.form;

import java.io.Serializable;

import org.thymeleaf.util.StringUtils;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SetPwdForm implements Serializable {

	private String mailaddr;						/**** メールアドレス      */
	private String passwd;							/**** 現在のパスワード    */
	@NotEmpty(message = "旧パスワードを入力して下さい")
	private String oldpasswd;
	@NotEmpty(message = "新パスワード１を入力して下さい")
	private String newpasswd1;
	@NotEmpty(message = "新パスワード２を入力して下さい")
	private String newpasswd2;

	private String msg;
	@AssertTrue(message = "旧パスワード違います")
	public boolean isPasswdCheck1() {
		 if( StringUtils.isEmpty(this.mailaddr) 
				 || StringUtils.isEmpty(this.oldpasswd)
				 || StringUtils.isEmpty(this.newpasswd1)
				 || StringUtils.isEmpty(this.newpasswd2)
				 ) {
			 return true;
		 }
		if( this.passwd.equals(this.oldpasswd)) {
			return true;
		}
		return false;
	}
	
	@AssertTrue(message = "新パスワード１と新パスワード２が異なります")
	public boolean isSamePasswdCheck2() {
		 if( StringUtils.isEmpty(this.mailaddr) 
				 || StringUtils.isEmpty(this.oldpasswd)
				 || StringUtils.isEmpty(this.newpasswd1)
				 || StringUtils.isEmpty(this.newpasswd2)
				 ) {
			 return true;
		 }
		 return newpasswd1.equals(newpasswd2);
	}
}
