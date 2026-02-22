package jp.co.kycm.kcattendance.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm implements Serializable {

	@NotEmpty(message = "メールアドレスを入力して下さい")
	private String     login;
	@NotEmpty(message = "パスワードを入力して下さい")
	private String     passwd;
	private boolean   savePasswd;
}
