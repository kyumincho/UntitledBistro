package com.bit.UntitledBistro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bit.UntitledBistro.model.jumun.IngredientDTO;
import com.bit.UntitledBistro.model.jumun.MenuDTO;
import com.bit.UntitledBistro.model.jumun.MenuTypeDTO;
import com.bit.UntitledBistro.model.jumun.OrdersDetailsDTO;
import com.bit.UntitledBistro.model.jumun.SalesDTO;
import com.bit.UntitledBistro.model.jumun.SalesDetailsDTO;
import com.bit.UntitledBistro.model.jumun.TableSaveDTO;
import com.bit.UntitledBistro.service.jumun.JumunService;

@Controller
@RequestMapping("/jumun")
public class JumunController {
	
	@Autowired
	private JumunService jumunService;
	private static final Logger logger = LoggerFactory.getLogger(JumunController.class);
	
	@RequestMapping(value = "/menuSetting.do")
	public String menuSettingSearch(@ModelAttribute("mt_Code") String mt_Code, Model model) {
		model.addAttribute("menuTypeList", jumunService.menuTypeSearch("all"));
		model.addAttribute("menuList", jumunService.menuSearch(mt_Code, "미판매"));

		return "jumun/menuSetting";
	}
	
	@RequestMapping(value = "/menuTypeAddForm.do")
	public String menuTypeAddForm() {
		return "views/jumun/menuTypeAddForm";
	}
	
	@RequestMapping(value = "/menuTypeAdd.do", method = RequestMethod.POST)
	@ResponseBody
	public int menuTypeAdd(@RequestBody MenuTypeDTO menuTypeDTO, RedirectAttributes attr) {
		return jumunService.menuTypeAdd(menuTypeDTO);
	}
	
	@RequestMapping(value = "/menuTypeList.do")
	public String menuTypeList(Model model) {
		model.addAttribute("menuTypeList", jumunService.menuTypeSearch("all"));
		
		return "views/jumun/menuTypeList";
	}
	
	@RequestMapping(value = "/menuTypeRemove.do", method = RequestMethod.POST)
	@ResponseBody
	public int menuTypeRemove(HttpServletRequest request) {
		String[] mt_CodeList = request.getParameterValues("mt_Code");
		return jumunService.menuTypeRemove(mt_CodeList);
	}
	
	@RequestMapping(value = "/menuTypeModi.do", method = RequestMethod.POST)
	@ResponseBody
	public int menuTypeModi(@RequestBody MenuTypeDTO menuTypeDTO) {
		return jumunService.menuTypeModi(menuTypeDTO);
	}
	
	@RequestMapping(value = "/menuAddForm.do")
	public String menuAddForm(Model model) {
		model.addAttribute("menuTypeList", jumunService.menuTypeSearch("all"));
		
		return "views/jumun/menuAddForm";
	}
	
	@RequestMapping(value = "/menuAdd.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public int menuAdd(MenuDTO menuDTO, MultipartHttpServletRequest mRequest) {
		String fileName = jumunService.imgUpload(mRequest);
		menuDTO.setMenu_Image(fileName);
		
		return jumunService.menuAdd(menuDTO);
	}
	
	@RequestMapping(value = "/menuRemove.do", method = RequestMethod.POST)
	public String menuRemove(RedirectAttributes attr, HttpServletRequest request) {
		String[] list = request.getParameterValues("menu_Code");
		attr.addFlashAttribute("result", jumunService.menuRemove(list));
		
		return "redirect:menuSetting.do";
	}
	
	@RequestMapping(value = "/menuModiForm.do")
	public String menuModiForm(HttpServletRequest request, Model model) {
		String menu_Code = request.getParameter("menu_Code");
		model.addAttribute("menuTypeList", jumunService.menuTypeSearch("all"));
		model.addAttribute("menuDTO", jumunService.menuSearchByMenuCode(menu_Code));
		
		return "views/jumun/menuModiForm";
	}
	
	@RequestMapping(value = "/menuModi.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public int menuModi(MenuDTO menuDTO, MultipartHttpServletRequest mRequest) {
		String fileName = jumunService.imgUpload(mRequest);
		if(fileName == null) fileName = "없음.jpg";
		menuDTO.setMenu_Image(fileName);
		
		return jumunService.menuModi(menuDTO);
	}
	
	@RequestMapping(value = "/ingreSearchByMenuCode.do", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<IngredientDTO> ingreSearchByMenuCode(HttpServletRequest request) {
		String menu_Code = request.getParameter("menu_Code");

		return jumunService.ingreSearchByMenuCode(menu_Code);
	}
	
	@RequestMapping(value = "/tableSetting.do")
	public String tableSetting(Model model) {
		model.addAttribute("tableList", jumunService.tableSearch());
		
		return "jumun/tableSetting";
	}
	
	@RequestMapping(value = "/tableSave.do", method = RequestMethod.POST)
	@ResponseBody
	public int tableSave(@RequestBody List<TableSaveDTO> list) {
		return jumunService.tableAdd(list);
	}
	
	@RequestMapping(value = "/posMain.do")
	public String posMain(Model model) {
		model.addAttribute("tableList", jumunService.tableSearch());
		model.addAttribute("posMainList", jumunService.orderListAll());
		model.addAttribute("tableUseList", jumunService.tableInfo());
		
		return "views/jumun/posMain";
	}
	
	@RequestMapping(value = "/ordersList.do", method = RequestMethod.POST)
	public String orderList(@ModelAttribute("orders_No") String orders_No, Model model, @ModelAttribute("mt_Code") String mt_Code) {
		model.addAttribute("ordersList", jumunService.ordersList(orders_No));
		model.addAttribute("allPrice", jumunService.odAllPrice(orders_No));
		model.addAttribute("menuTypeList", jumunService.menuTypeSearch("all"));
		model.addAttribute("menuList", jumunService.menuSearch(mt_Code, "판매"));
		
		return "views/jumun/ordersList";
	}

	@RequestMapping(value = "/ordersRemove.do", method = RequestMethod.POST)
	@ResponseBody
	public int ordersRemove(@RequestBody OrdersDetailsDTO ordersDetailDTO) {
		return jumunService.ordersRemove(ordersDetailDTO);
	}
	
	@RequestMapping(value = "/ordersPlus.do", method = RequestMethod.POST)
	@ResponseBody
	public int ordersPlus(@RequestBody OrdersDetailsDTO ordersDetailDTO) {
		return jumunService.ordersPlus(ordersDetailDTO);
	}

	@RequestMapping(value = "/ordersMinus.do", method = RequestMethod.POST)
	@ResponseBody
	public int ordersMinus(@RequestBody OrdersDetailsDTO ordersDetailDTO) {
		return jumunService.ordersMinus(ordersDetailDTO);
	}
	
	@RequestMapping(value = "/tableControl.do")
	public String tableControl(Model model) {
		model.addAttribute("tableList", jumunService.tableControl());
		
		return "views/jumun/tableControl";
	}
	
	@RequestMapping(value = "/tableControl.do", method = RequestMethod.POST)
	@ResponseBody
	public int tableControl(@RequestBody Map<String, Object> table) {
		return jumunService.tableControl(table);
	}
	
	@RequestMapping(value = "/ordersRemoveAll.do", method = RequestMethod.POST)
	@ResponseBody
	public int ordersRemoveAll(@RequestBody OrdersDetailsDTO ordersDetailDTO) {
		return jumunService.ordersRemoveAll(ordersDetailDTO);
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/salesList.do")
	public String salesList(Model model) {
		model.addAttribute("salesList", jumunService.paymentSearch());
		
		return "views/jumun/salesList";
	}
	
	@RequestMapping(value = "/salesDetailsByNo.do", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<SalesDetailsDTO> salesDetailsByNo(@RequestBody SalesDTO salesDTO) {
		return jumunService.salesDetailsSearch(salesDTO);
	}
}