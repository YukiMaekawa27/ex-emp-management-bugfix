package jp.co.sample.emp_management.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 管理者情報登録時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class InsertAdministratorForm {
	/** 名前 */
	@NotBlank(message="名前を入力してください")
	private String name;
	/** メールアドレス */
	@NotBlank(message="メールアドレスを入力してください")
	@Email(message="メールアドレスの形式ではありません")
	private String mailAddress;
	/** パスワード */
	@NotBlank(message="パスワードを入力してください")
	@Size(min = 6, max = 12, message="パスワードは６〜１２文字で入力してください")
	private String password;
	/** 確認用パスワード */
	@NotBlank(message="パスワードを入力してください")
	@Size(min = 6, max = 12, message="パスワードは６〜１２文字で入力してください")
	private String checkedpassword;
	
	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ ", checkedpassword=" + checkedpassword + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckedpassword() {
		return checkedpassword;
	}

	public void setCheckedpassword(String checkedpassword) {
		this.checkedpassword = checkedpassword;
	}
	
	
}