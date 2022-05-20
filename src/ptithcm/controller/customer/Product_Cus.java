package ptithcm.controller.customer;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.entity.*;

@Controller
@Transactional
@RequestMapping("/product")
public class Product_Cus {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "{batchName}", method = RequestMethod.GET)
	public String get_Batch(ModelMap model, @PathVariable("batchName") String batchName, HttpServletRequest request) {
		Batch batch = getBatch(batchName);
		if(batch!=null) {
			HttpSession session = request.getSession();
			model.addAttribute("role",(String) session.getAttribute("role"));
			model.addAttribute("batch", batch);
			return "product";
		}
		return "redirect:/trangchu.htm";
	}
	
	@SuppressWarnings("unchecked")
	private Batch getBatch(String batchName) {
		List<Batch> listBatch = null;
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Batch");
		listBatch = query.list();
		return findByBatchId(listBatch, batchName);
	}
	
	private Batch findByBatchId(List<Batch> list, String batchName) {
		for(Batch item: list) {
			if(item.batchNameToURL().equals(batchName)) return item;
		}
		return null;
	}
}
