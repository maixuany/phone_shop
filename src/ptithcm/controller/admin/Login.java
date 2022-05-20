package ptithcm.controller.admin;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.entity.*;

@Controller
@Transactional
public class Login {
	private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");
	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "dangnhap", method = RequestMethod.GET)
	public String getpage_Login(ModelMap model) {
		return "admin/login";
	}
	
	@RequestMapping(value = "dangnhap", method = RequestMethod.POST)
	public String login(ModelMap model, HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session_web = request.getSession();
		Session session = factory.getCurrentSession();
		Account account = (Account) session.get(Account.class, username);
		if(account!=null) {
			if(account.getPassword().equals(getMd5(password))) {
				session_web.setAttribute("role", account.getRole().getRoleName());
				session_web.setAttribute("username", account.getAccountId());
				session_web.setAttribute("fullname", account.getName());
				if(account.getRole().getRoleName().equalsIgnoreCase("admin")) {
					return "redirect:/admin/index.htm";
				}
				else
					return "redirect:/trangchu.htm";
			}
		}
		session_web.setAttribute("status_login", 0);
		return "admin/login";
	}
	
	@RequestMapping(value = "dangxuat")
	public String logout(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("role", null);
		session.setAttribute("username", null);
		session.setAttribute("fullname", null);
		return "redirect:/trangchu.htm";
	}
	
	@RequestMapping(value = "dangki", method = RequestMethod.GET)
	public String getpage_dangki(ModelMap model) {
		
		return "admin/dangki";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "dangki", method = RequestMethod.POST)
	public String dangki(ModelMap model, @RequestParam("username") String accountId, 
			@RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("phone") String phone,
			@RequestParam("address")String address, @RequestParam("password") String password, HttpServletRequest request) {
		name = name.trim();
		email = email.trim().toLowerCase();
		phone = phone.trim();
		address = address.trim();
		accountId = accountId.trim();
		if(check_Username(accountId)==0) {
			model.addAttribute("status_dangki_username", 0);
			return "admin/dangki";
		}
		if (check_Email(email) == 0) {
			model.addAttribute("status_dangki_email", 0);
			return "admin/dangki";
		}
		if (check_Name(name) == 0) {
			model.addAttribute("status_dangki_name", 0);
			return "admin/dangki";
		}
		if (check_SDT(phone) == 0) {
			model.addAttribute("status_dangki_phone", 0);
			return "admin/dangki";
		}
		if (check_Address(address) == 0) {
			model.addAttribute("status_dangki_address", 0);
			return "admin/dangki";
		}
		if (check_PassWord(password) == 0) {
			model.addAttribute("status_dangki_password", 0);
			return "admin/dangki";
		}
		
		Account account = new Account();
		ArrayList<Account> listAccount = (ArrayList<Account>) getListAccount();
		if(check_Trung_Username(listAccount, accountId)) {
			model.addAttribute("errorUsername",1);
			return "admin/dangki";
		}
		if(check_Trung_Email(listAccount, email)) {
			model.addAttribute("errorEmail",1);
			return "admin/dangki";
		}
		if(check_trung_sdt(listAccount, phone)) {
			model.addAttribute("errorPhone",1);
			return "admin/dangki";
		}
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		account.setAccountId(accountId);
		Role role = (Role) session.get(Role.class, 2);
		account.setRole(role);
		account.setAddress(address);
		account.setEmail(email);
		account.setName(name);
		account.setPassword(getMd5(password));
		account.setPhone(phone);
		try {
			session.save(account);
			transaction.commit();
			
		}catch (Exception e) {
			// TODO: handle exception
			transaction.rollback();
			return "admin/dangki";
		}finally {
			session.close();
		}
		HttpSession web_session = request.getSession();
		web_session.setAttribute("status_add_account",1);
		return "redirect:/dangnhap.htm";
	}
	
	@SuppressWarnings("unchecked")
	private Object getListAccount() {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Account");
		List<Account> list = query.list();
		return list;
	}
	
	private boolean check_Trung_Username(ArrayList<Account> list, String username) {
		for(Account item: list) {
			if(item.getAccountId().equals(username)) return true;
		}
		return false;
	}
	
	private boolean check_Trung_Email(ArrayList<Account> list, String email) {
		for(Account item: list) {
			if(item.getEmail().equals(email)) return true;
		}
		return false;
	}
	
	private boolean check_trung_sdt(ArrayList<Account> list, String phone) {
		for(Account item: list) {
			if(item.getPhone().equals(phone)) return true;
		}
		return false;
	}
	
	@RequestMapping(value = "quenmatkhau", method = RequestMethod.GET)
	public String getpage_quenmatkhau(ModelMap model) {
		return "admin/forgot_password";
	}
	
	@RequestMapping(value = "quenmatkhau", method = RequestMethod.POST)
	public String quenmatkhau(ModelMap model, HttpServletRequest request) {
//		HttpSession session = request.getSession();
		return "admin/submitCode";
	}
	
	@RequestMapping(value = "submitCode", method = RequestMethod.POST)
	public String submitCode(ModelMap model, HttpServletRequest request) {
//		HttpSession session = request.getSession();
		return "redirect:/dangnhap.htm";
	}
	
	private int check_Name(String name) {
		if(name.length()!=0) return 1;
		return 0;
	}
	
	private int check_Username(String username) {
		String regex = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(username);
		if(matcher.matches()==false) return 0;
		return 1;
		
	}
	
	private int check_Address(String address) {
		if(address.length()!=0) return 1;
		return 0;
	}
	
	private int check_Email(String email) {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()==false) return 0;
		return 1;
	}
	
	private int check_SDT( String phone) {
		if(phone.length()!=10) return 0;
		return 1;
	}
	
	private int check_PassWord(String password){
        if(password.isEmpty()){
            return 0;
        }else if(!PASSWORD_PATTERN.matcher(password).matches()){
            return 0;
        }
        else {
            return 1;
        }
    }
	
	 public static String getMd5(String input)
	    {
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            byte[] messageDigest = md.digest(input.getBytes());
	            BigInteger no = new BigInteger(1, messageDigest);
	            String hashtext = no.toString(16);
	            while (hashtext.length() < 32) {
	                hashtext = "0" + hashtext;
	            }
	            return hashtext;
	        }
	        catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }
}
