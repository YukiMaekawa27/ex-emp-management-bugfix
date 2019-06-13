package jp.co.sample.emp_management.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.form.InsertAdministratorForm;
import jp.co.sample.emp_management.form.LoginForm;
import jp.co.sample.emp_management.service.AdministratorService;

/**
 * 管理者情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private HttpSession session;
	
	// BCrypt
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	// (SpringSecurityに任せるためコメントアウトしました)
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：管理者を登録する
	/////////////////////////////////////////////////////
	/**
	 * 管理者登録画面を出力します.
	 * 
	 * @return 管理者登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert(Model model) {
		return "administrator/insert";
	}

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param form 管理者情報用フォーム
	 * @return ログイン画面へリダイレクト
	 */
	@RequestMapping(value = "/insert", method=RequestMethod.POST)
	public String insert(@Validated InsertAdministratorForm form, BindingResult result, Model model, String checkedpassword) {
		//メールアドレスのダブりがないかチェック
		Boolean hasMailAddress = administratorService.isCheckByMailAddress(form.getMailAddress());
		if (hasMailAddress) {
			result.rejectValue("mailAddress", null,  "すでに使われているメールアドレスです。");
		}
		//エラーがあるかチェック
		if (result.hasErrors()) {
			return toInsert(model);
		}
		Administrator administrator = new Administrator();
		// フォームからドメインにプロパティ値をコピー
		BeanUtils.copyProperties(form, administrator);
		//確認用パスワードとパスワードが一致するかチェック
		if (!administrator.getPassword().equals(checkedpassword)) {
			result.rejectValue("password", null,  "パスワードが一致しません。");
			return toInsert(model);
		}
		//パスワードの暗号化
		HashPasswordController hashPass = new HashPasswordController();
		administrator.setPassword(hashPass.GetHashedPassword(administrator.getPassword()));
		System.out.println(administrator.getPassword());
		administratorService.insert(administrator);
		
		
		return "redirect:/";
	}

	/////////////////////////////////////////////////////
	// ユースケース：ログインをする
	/////////////////////////////////////////////////////
	/**
	 * ログイン画面を出力します.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	/**
	 * ログインします.
	 * 
	 * @param form   管理者情報用フォーム
	 * @param result エラー情報格納用オブッジェクト
	 * @return ログイン後の従業員一覧画面
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, BindingResult result, Model model) {
		//パスワードと保存してあったハッシュ値を照合
		HashPasswordController hashPass = new HashPasswordController();
		Administrator administrator = administratorService.searchByMailAddress(form.getMailAddress());
		String hash = administrator.getPassword();
		hashPass.CheckHashedPassword(form.getPassword(), hash);
		
		//ログインするための管理者情報があるか確認
		administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		if (administrator == null) {
			result.addError(new ObjectError("loginError", "メールアドレスまたはパスワードが不正です。"));
			return toLogin();
		}
		session.setAttribute("administratorName", administrator.getName());
		return "forward:/employee/showList";
	}
	
	/**
	 * 500エラー時にエラー画面へ遷移する.
	 * 
	 * @return エラー画面
	 */
	@RequestMapping("/maintenance")
	public String maintenance() {
		return "error/maintenance";
	}

	/////////////////////////////////////////////////////
	// ユースケース：ログアウトをする
	/////////////////////////////////////////////////////
	/**
	 * ログアウトをします. (SpringSecurityに任せるためコメントアウトしました)
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping(value = "/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
}
