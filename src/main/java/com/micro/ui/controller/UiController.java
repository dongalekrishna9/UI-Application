package com.micro.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.micro.ui.model.CallDetailsDTO;
import com.micro.ui.model.CustomerProfileDTO;
import com.micro.ui.model.FriendDTO;
import com.micro.ui.model.LoginBean;
import com.micro.ui.model.PlanDTO;
import com.micro.ui.model.RegisterBean;

@Controller
public class UiController {
	private static String ALL_PLANS_URL="http://localhost:7272/PlanDetails/browsePlans";
	private static String REGISTER_URL="http://localhost:7474/CustomerDetails/register";
	private static String LOGIN_URL="http://localhost:7474/CustomerDetails/login";
	private static String PROFILE_URL="http://localhost:7474/CustomerDetails/viewProfile/{phoneNumber}";
	private static String CALL_DETAILS_URL="http://localhost:7171/CallDetails/{phoneNumber}";
	private static String FRIEND_URL="http://localhost:7373/FriendDetails/addFriend";
	
	@Autowired
	RestTemplate restTemplate;
	
	//get index page
	@GetMapping(value="/index", produces = "application/json")
	public String getIndexPage()
	{
		return "index";
	}
	
	//get PlanDTOList from PlanDeatils-Microservice
	private List<PlanDTO> getAllPlans()
	{
		ParameterizedTypeReference<List<PlanDTO>> typeRef=new ParameterizedTypeReference<List<PlanDTO>>() {};
		ResponseEntity<List<PlanDTO>> exchange = this.restTemplate.exchange(ALL_PLANS_URL, HttpMethod.GET, null, typeRef);
		List<PlanDTO> plansDTOList = exchange.getBody();
		return plansDTOList;
	}
	
	//get register page with all PlanDetailsList
	@GetMapping(value = "/registerPage")
	public String getRegisterPage(Model model)
	{
		RegisterBean registerBean=new RegisterBean();
		registerBean.setPlansList(getAllPlans());
		model.addAttribute("registerBean", registerBean);
		return "register";
	}
	
	//add customer into customerDB
	@PostMapping(value = "/addCustomer")
	public String addCustomer(@ModelAttribute RegisterBean registerBean,Model model)
	{
		//calling customer-microservice
		Boolean flag = this.restTemplate.postForObject(REGISTER_URL, registerBean, Boolean.class);
		if(flag)
		{
			model.addAttribute("message", "Customer is successfully registered");
			return "index";
		}
		else
		{
			model.addAttribute("message","Another Customer is already registered with this phone number, try again..!");
			registerBean.setPlansList(getAllPlans());
			model.addAttribute("registerBean", registerBean);
			return "register";
		}
	}
	
	//get login page
	@GetMapping("/loginPage")
	public String getLoginPage(Model model)
	{
		LoginBean loginBean=new LoginBean();
		model.addAttribute("loginBean", loginBean);
		return "login";
	}
	
	//Login process from customer-Microservice
	@PostMapping("/loginCustomer")
	public String loginCustomer(@ModelAttribute LoginBean loginBean, Model model, HttpServletRequest request)
	{
		//calling CustomerDetails-microservice
		Boolean flag = this.restTemplate.postForObject(LOGIN_URL, loginBean, Boolean.class);
		if(flag)
		{
			HttpSession session = request.getSession();
			session.setAttribute("phoneNumber", loginBean.getPhoneNumber());
			return "Home";
		}
		else
		{
			model.addAttribute("message","Bad Credentials..!");
			return "login";
		}
	}
	
	//View profile
	@GetMapping(value = "/profile")
	public String viewProfile(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();
		Long phoneNumber = (Long) session.getAttribute("phoneNumber");
		
		//calling CustomerDetails-Microservice
		CustomerProfileDTO customerProfileDTO = this.restTemplate.getForObject(PROFILE_URL, CustomerProfileDTO.class, phoneNumber);
		model.addAttribute("customer",customerProfileDTO);
		return "customerProfile";
	}
	
	//Show all plans
	@GetMapping("/plans")
	public String showAllPlans(Model model)
	{
		//calling PlanDetails-Microservice
		ParameterizedTypeReference<List<PlanDTO>> typeRef=new ParameterizedTypeReference<List<PlanDTO>>() {};		
		ResponseEntity<List<PlanDTO>> exchange = this.restTemplate.exchange(ALL_PLANS_URL, HttpMethod.GET, null, typeRef);
		List<PlanDTO> planDTOList = exchange.getBody();
		
		model.addAttribute("planDtoList", planDTOList);
		return "showPlans";
	}
	
	//Call Details of signin person
	@GetMapping("/calldetails")
	public String showCallDetails(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();
		Long phoneNumber= (Long)session.getAttribute("phoneNumber");
	
		//calling CallDetails-Microservice
		ParameterizedTypeReference<List<CallDetailsDTO>> typeRef=new ParameterizedTypeReference<List<CallDetailsDTO>>() {};
		ResponseEntity<List<CallDetailsDTO>> exchange = this.restTemplate.exchange(CALL_DETAILS_URL, HttpMethod.GET, null,typeRef,phoneNumber);
		List<CallDetailsDTO> callDTOList = exchange.getBody();
		
		model.addAttribute("callDetailsDtoList", callDTOList);
		return "showCallDetails";
	}
	
	//add Friend
	@GetMapping("/addfriend")
	public String getAddFriendPage()
	{
		return "addContact";
	}
	
	//add friend contact
	@GetMapping("/addContact")
	public String addFriendContact(@RequestParam Long friendNumber, HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();
		Long phoneNumber=(Long)session.getAttribute("phoneNumber");
	
		FriendDTO friendDTO=new FriendDTO();
		friendDTO.setPhoneNumber(phoneNumber);
		friendDTO.setFriendNumber(friendNumber);
		
		//calling Friend-Microservice
		String message = this.restTemplate.postForObject(FRIEND_URL, friendDTO, String.class);
		model.addAttribute("message", message);
		return "friendAdded";
	}
	
	//go to home page
	@GetMapping("/gotohome")
	public String goToHomePage()
	{
		return "Home";
	}
	
	
}
