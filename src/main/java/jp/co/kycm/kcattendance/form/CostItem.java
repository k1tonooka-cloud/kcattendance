package jp.co.kycm.kcattendance.form;

import java.io.Serializable;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CostItem  implements Serializable {
	private boolean	Checkbox;
	private Integer		LineNo;
	@NotNull(message = "金額を入力して下さい")
	@Min(value = 0, message = "金額は0以上で入力してください")
	@Digits(integer=6, fraction=0)
	private Integer		Cost;
	@NotEmpty(message = "備考を入力して下さい")
	private String  	Comment;
	private String  	Status;
}
