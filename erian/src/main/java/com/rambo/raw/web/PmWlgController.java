
package com.rambo.raw.web;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rambo.common.utils.ListUtils;
import com.rambo.erian.entity.User;
import com.rambo.erian.service.account.ShiroDbRealm.ShiroUser;
import com.rambo.raw.entity.PmWlgRaw;
import com.rambo.raw.service.PmWlgRawService;

import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;

/**
 * Controller for pmWlg management, we utilize the Restful Apis:
 * 
 * List page : GET /pmWlg/
 * Create page : GET /pmWlg/create
 * Create action : POST /pmWlg/create
 * Update page : GET /pmWlg/update/{id}
 * Update action : POST /pmWlg/update
 * Delete action : GET /pmWlg/delete/{id}
 * 
 * @author yuanbo  
 */
@Controller
@RequestMapping(value = "/pmWlg")
public class PmWlgController {

	private static final String PAGE_SIZE = "12";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "auto");
		sortTypes.put("NO", "NO");
	}

	@Autowired
	private PmWlgRawService pmWlgService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<PmWlgRaw> pmWlgs = pmWlgService.getUserPmWlg(searchParams, pageNumber, pageSize, sortType);
//		ListUtils.output(pmWlgs.getContent());
		model.addAttribute("pmWlgs", pmWlgs);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "pmWlg/pmWlgList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("pmWlg", new PmWlgRaw());
		model.addAttribute("action", "create");
		return "pmWlg/pmWlgForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid PmWlgRaw newpmWlg, RedirectAttributes redirectAttributes) {


		pmWlgService.savePmWlg(newpmWlg);
		redirectAttributes.addFlashAttribute("message", "创建pmWlg Success");
		return "redirect:/pmWlg/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("pmWlg", pmWlgService.getPmWlg(id));
		model.addAttribute("action", "update");
		return "pmWlg/pmWlgForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("pmWlg") PmWlgRaw pmWlg, RedirectAttributes redirectAttributes) {
		pmWlgService.savePmWlg(pmWlg);
		redirectAttributes.addFlashAttribute("message", "update pmWlg Success");
		return "redirect:/pmWlg/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		pmWlgService.deletePmWlg(id);
		redirectAttributes.addFlashAttribute("message", "delete pmWlg success");
		return "redirect:/pmWlg/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出pmWlg对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getpmWlg(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("pmWlg", pmWlgService.getPmWlg(id));
		}
	}

}
