package jp.co.sample.emp_management.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.UpdateEmployeeForm;
import jp.co.sample.emp_management.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}


	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}
	
	/**
	 * 曖昧検索を行います.
	 * 
	 * @param キーワード
	 * @param リクエストスコープ
	 * @return 従業員リスト
	 */
	@RequestMapping("/searchWithKeyword")
	public String searchWithKeyword(String keyword, Model model) {
		List<Employee> employeeList = employeeService.searchWithKeyword(keyword);
		if(employeeList.size() == 0) {
			model.addAttribute("cantFind", "従業員が見つかりませんでした。キーワードを変えてお探し下さい。");
			employeeList = employeeService.showList();
		}
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	
	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}
	
	@RequestMapping("/delete")
	public String delete(String id, Model model) {
		System.out.println(id);
		employeeService.delete(id);
		System.out.println(id);
		return showList(model);
	}
	
	/**
	 * 従業員追加画面へ遷移する.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/addemployee")
	public String addEmployee(Model model) {
		Integer newId =	employeeService.getMaxId() + 1;
		System.out.println(newId);
		model.addAttribute("id", String.valueOf(newId));
 		System.out.println(model);
		return "employee/addemployee";
	}
	
	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form
	 *            従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/update")
	public String update(
			@Validated UpdateEmployeeForm form,
			BindingResult result,
			Model model) {
		if(form.getImage().isEmpty()) {
			result.rejectValue("image", null, "画像を選択してください " );
		}
		if(result.hasErrors()) {
			//return addEmployee(model);
		}
		
		//従業員情報をformからdomainへコピー
		Employee employee = new Employee();
		BeanUtils.copyProperties(form, employee);
		employee.setId(Integer.parseInt(form.getId()));
		employee.setSalary(form.getIntSalary());
		employee.setDependentsCount(form.getIntDependentsCount());
		employee.setImage(form.getImage().getOriginalFilename());
		employee.setHireDate(Date.valueOf(form.getHireDate()));
		
		//画像ファイルをIMGフォルダに保存する
		  String filename = employee.getImage();
		  Path uploadfile = Paths.get("src/main/resources/static/img/" + filename);
		  try (OutputStream os = Files.newOutputStream(uploadfile, StandardOpenOption.CREATE)) {
		    byte[] bytes = form.getImage().getBytes();
		    os.write(bytes);
		  } catch (IOException ex) {
		    System.err.println(ex);
		  }
		  employeeService.insert(employee);
		  
		return "redirect:/employee/showList";
	}
}
