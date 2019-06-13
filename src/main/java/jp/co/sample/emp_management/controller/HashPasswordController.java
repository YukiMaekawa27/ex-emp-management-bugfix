package jp.co.sample.emp_management.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

public class HashPasswordController {

	// BCrypt
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public ModelAndView HashPassword(ModelAndView mv, String password) {
		mv.setViewName("hash");

		// ハッシュ値取得
		String hash = GetHashedPassword(password);

		// 新規登録時はハッシュ値を保存する。

		// パスワードと保存してあったハッシュ値をチェック
		CheckHashedPassword(password, hash);

		return mv;
	}
	
    // ハッシュ値取得
    public String GetHashedPassword(String password) {
        String hash = passwordEncoder.encode(password);
        
        System.out.println("ハッシュ値 : " + hash);
        
        return hash;
    }
    
    // パスワードチェック
    public Boolean CheckHashedPassword(String password, String hash) {
    	if (passwordEncoder.matches(password, hash)) {
    		System.out.println("パスワードが一致しました。");
    		return true;
    	} else {
    		System.out.println("パスワードが一致しません。");
    		return false;
    	}
    }

}
