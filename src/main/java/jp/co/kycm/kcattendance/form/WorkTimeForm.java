package jp.co.kycm.kcattendance.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkTimeForm implements Serializable {
	@Valid
	private List<CostItem> costItems;

	public WorkTimeForm(List<CostItem> costItems) {
        this.costItems = costItems;
    }

	private int           year;
	private int           month;
	private String        EmployeeId;
	private String        EmployeeName;

	@NotNull(message = "作業時間を入力して下さい")
	@Min(value = 0, message = "作業時間は0以上で入力してください")
	@Max(value = 300, message = "作業時間は300以下で入力してください")
	@Digits(integer=3, fraction=1)
	private BigDecimal    worktime;
	private String        workscheduleFlg;
	private String        fileName;
	private String        mineType;
	private String        data;
	private String        status;
	private String        readonly;
	private BigDecimal    sum;
	private String        action;
	private String        msg;
	private MultipartFile file;
}
