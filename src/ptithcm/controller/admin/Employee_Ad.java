package ptithcm.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.entity.Account;

@Controller
@Transactional
@RequestMapping(value = "admin")
public class Employee_Ad {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "listuser")
	public String getListUser(ModelMap model) {
		model.addAttribute("listUser", getListUser());
		return "admin/list_account";
	}
	
	@RequestMapping(value = "newaccount", method = RequestMethod.GET)
	public String loadpage_NewAccount(ModelMap model) {
		return "admin/add_account";
	}
	
	@RequestMapping(value = "newaccount", method = RequestMethod.POST)
	public String addNewAccount(ModelMap model, @RequestParam("username")String accountId,
			@RequestParam("email")String email, @RequestParam("name")String fullname,
			@RequestParam("phone")String phone, @RequestParam("address")String address,
			@RequestParam("password")String password) {
		
		return "redirect:/admin/listuser.htm";
	}
	
	@RequestMapping(value = "profile")
	public String showProfile(ModelMap model, HttpServletRequest request) {
		List<Account> list = getListUser();
		HttpSession session = request.getSession();
		String accountId = (String) session.getAttribute("username");
		model.addAttribute("myaccount", getAccount(list, accountId));
		return "admin/profile";
	}
	
	@RequestMapping(value = "editprofile", method = RequestMethod.GET)
	public String loadpage_editProfile(ModelMap model, HttpServletRequest request) {
		List<Account> list = getListUser();
		HttpSession session = request.getSession();
		String accountId = (String) session.getAttribute("username");
		model.addAttribute("myaccount", getAccount(list, accountId));
		return "admin/edit_profile";
	}
	
	@RequestMapping(value = "changepass", method = RequestMethod.GET)
	public String loadpage_changePass(ModelMap model) {
		return "admin/changepassword";
	}
	
	Account getAccount(List<Account> accounts ,String accountId) {
		for(Account item: accounts)
			if(item.getAccountId().equals(accountId)) return item;
		return null;
	}
	
	private int check_username() {
		return 1;
	}
	
	private int check_email() {
		return 1;
	}
	
	private int check_name() {
		return 1;
	}
	
	private int check_phone() {
		return 1;
	}
	
	private int check_address() {
		return 1;
	}
	
	private int check_password() {
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	private List<Account> getListUser() {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Account");
		List<Account> list = query.list();
		return list;
	}
}
