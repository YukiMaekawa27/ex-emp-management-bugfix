package jp.co.sample.emp_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class AdministratorService {
	
	@Autowired
	private AdministratorRepository administratorRepository;

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param administrator　管理者情報
	 */
	public void insert(Administrator administrator) {
		administratorRepository.insert(administrator);
	}
	
	/**
	 * ログインをします.
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return 管理者情報　存在しない場合はnullが返ります
	 */
	public Administrator login(String mailAddress, String passward) {
		Administrator administrator = administratorRepository.findByMailAddressAndPassward(mailAddress, passward);
		return administrator;
	}
	
	
	/**
	 * メールアドレスの有無を調べる.
	 * メールアドレスがある場合、true
	 * ない場合はfalseを返す
	 * 
	 * @param mailAddress
	 * @return
	 */
	public Boolean isCheckByMailAddress(String mailAddress) {
		if(administratorRepository.findByMailAddress(mailAddress)!=null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Administrator searchByMailAddress(String mailAddress) {
		return administratorRepository.findByMailAddress(mailAddress);
	}
}
