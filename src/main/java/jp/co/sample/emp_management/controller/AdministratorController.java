package jp.co.sample.emp_management.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.form.InsertAdministratorForm;
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

//	@Autowired
//	private HttpSession session;
//	
	@Autowired
	private PasswordEncoder encoder;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

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
//	@ModelAttribute
//	public LoginForm setUpLoginForm() {
//		return new LoginForm();
//	}

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
		String hash = encoder.encode(administrator.getPassword());
		administrator.setPassword(hash);
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
//	@RequestMapping("/")
//	public String toLogin() {
//		return "administrator/login";
//	}
	@RequestMapping("/")
	public String toLogin(Model model,@RequestParam(required = false) String error) {
		System.err.println("login error:" + error);
		if (error != null) {
			System.err.println("login failed");
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
		}
		return "administrator/login";
	}

//	/**
//	 * ログインします.
//	 * 
//	 * @param form   管理者情報用フォーム
//	 * @param result エラー情報格納用オブッジェクト
//	 * @return ログイン後の従業員一覧画面
//	 */
//	@RequestMapping("/login")
//	public String login(@Validated LoginForm form, BindingResult result, Model model) {
//		if(result.hasErrors()) {
//			return toLogin();			
//		}
//		//パスワードと保存してあったハッシュ値を照合
//		Administrator administrator = administratorService.searchByMailAddress(form.getMailAddress());
//		String hash = administrator.getPassword();
//		Boolean isLogin = encoder.matches(form.getPassword(), hash );
//		
//		//ログインするための管理者情報があるか確認
//		if (!isLogin) {
//			result.rejectValue("mailAddress", null,"メールアドレスまたはパスワードが不正です。");
//			return toLogin();
//		}
//		session.setAttribute("administratorName", administrator.getName());
//		return "forward:/employee/showList";
//	}
//	
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
//	@RequestMapping(value = "/logout")
//	public String logout() {
//		session.invalidate();
//		return "redirect:/";
//	}
}
