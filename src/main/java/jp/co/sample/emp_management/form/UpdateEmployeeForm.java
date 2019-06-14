package jp.co.sample.emp_management.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

/**
 * 従業員情報更新時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class UpdateEmployeeForm {
	/** id */
	private String id;
	/** 従業員名 */
	@NotBlank(message="名前を入力してください")
	private String name;
	/** 画像 */
	private MultipartFile image;
	/** 性別 */
	@NotBlank(message="性別をを選択してください")
	private String gender;
	/** 入社日 */
	private String hireDate;
	/** メールアドレス */
	@NotBlank(message="メールアドレスを入力してください")
	private String mailAddress;
	/** 郵便番号 */
	@NotBlank(message="郵便番号を入力してください")
	private String zipCode;
	/** 住所 */
	@NotBlank(message="住所を入力してください")
	private String address;
	/** 電話番号 */
	@NotBlank(message="電話番号を入力してください")
	private String telephone;
	/** 給料 */
	@NotBlank(message="給料を入力してください")
	private  String salary;
	/** 特性 */
	@NotBlank(message="特性を入力してください")
	private String characteristics;
	/** 扶養人数 */
	@NotBlank(message="扶養人数を入力してください")
	@Pattern(regexp="^[0-9]+$", message="扶養人数は数値で入力してください")
	private String dependentsCount;
	
	/**
	 * IDを数値として返します.
	 * 
	 * @return 数値のID
	 */
	public Integer getIntId() {
		return Integer.parseInt(this.id);
	}
	
	/**
	 * 給料を数値として返します。
	 * 
	 * @return 数値の給料
	 */
	public Integer getIntSalary() {
		return Integer.parseInt(this.salary);
	}
	
	/**
	 * 扶養人数を数値として返します.
	 * 
	 * @return 数値の扶養人数
	 */
	public Integer getIntDependentsCount() {
		return Integer.parseInt(this.dependentsCount);
	}
	
	public String getStringImage() {
		return String.valueOf(image);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public String getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

	
}
