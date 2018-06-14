package com.robert.controller;


import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.robert.model.Usersecurity;
import com.robert.repository.UsersecurityRepository;
import com.robert.service.UsersecurityService;



@Controller
public class MainController {
	
		private String errorMessage;      
	
		@Autowired
		private UsersecurityService usersecurityService;	
		@Autowired
		private UsersecurityRepository usersecurityRepository;
		
		// =====================================================
		// page d'accueil de l'application
		// =====================================================
		@RequestMapping(value="/", method=RequestMethod.GET)  
	    public String root() {
	        return "index";
	    }
		// =====================================================
		// page d'accueil de l'utilisateur
		// =====================================================
	    @RequestMapping(value="/user", method=RequestMethod.GET)  
	    public String userIndex() {
	        return "user/index";
	    }
	    // =====================================================
	 	// page login
	 	// =====================================================
	    //@GetMapping("/login")
	    @RequestMapping(value="/login", method=RequestMethod.GET)  
	    public String login() {
	        return "login";
	    }
	    
	    // =====================================================
	 	// page logout
	 	// =====================================================
	 	 
	 	@RequestMapping(value="/logout", method=RequestMethod.GET)  
	 	    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
	 			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	 			if (auth != null) {
	 				new SecurityContextLogoutHandler().logout(request, response, auth);
	 			}
	 	        return "redirect/login?logout";
	 	}
	    // =====================================================
	 	// page accès refusé
	 	// =====================================================
	    //@GetMapping("/access-denied")
	    @RequestMapping(value="/access-denied", method=RequestMethod.GET)  
	    public String accessDenied() {
	        return "/error/access-denied";
	    }	
	    
	    
	    // ==========================================================
	    // liste des utilisateurs
	    // ==========================================================
	    @RequestMapping(value = { "/usersSearch" })    
		public String usersSearch(Model model,
				@RequestParam(name="page", defaultValue="0") int p,
				@RequestParam(name="motCle", defaultValue="") String mc) {  
			Page<Usersecurity> usersecurityPages = usersecurityRepository.finduser("%"+mc+"%", new PageRequest(p,4));
			
			int pagesCount = usersecurityPages.getTotalPages();
			int[] pages= new int[pagesCount] ;
			for (int i=0; i<pagesCount;i++) pages[i]=i;
			model.addAttribute("pages", pages);
			model.addAttribute("size", 5);
			model.addAttribute("pageCourante", p);
			model.addAttribute("UsersPage", usersecurityPages); 
			model.addAttribute("motCle", mc);
			return "user/userSearch";
		}
	    // =============================================================
	    // affichage du formulaire
	    // Mise à jour d'un utilisateur en mode get 
	    // =============================================================
	    @RequestMapping(value = { "/userEdit" }) 
		public String userEdit(String username, Model model) {
			Usersecurity user = usersecurityRepository.getOne(username);
			model.addAttribute("usersecurity",user);
			return "user/userEdit";		
		} 
	    // ==============================================================
	    // Mise à jour de l'utilisateur en mode POST
	    // sauvegarde dans la base
	    // ==============================================================
	    @RequestMapping(value = { "/userUpdate" }, method = RequestMethod.POST)    
		public String userUpdate(@Valid Usersecurity usersecurity, BindingResult bindingResult) 
				throws Exception {
	    	
	    	if(bindingResult.hasErrors()) {
				return "user/userEdit";
			}	
	    	System.out.println("=============================================");
	    	System.out.println("passage controller userUpdate");
	    	System.out.println("=============================================");	
			//usersecurityRepository.save(usersecurity);
			usersecurityService.saveUser(usersecurity);	
			return "redirect:/usersSearch";
		}
	    // =================================================================
	    // suppression d'un utilisateur
	    // =================================================================
	    @RequestMapping(value = { "/userDelete" }) 
		public String userDelete(String username,String motCle, int page, int size) {
			usersecurityRepository.delete(username);
			return "redirect:/usersSearch?page="+page+"&size="+size+"&motCle="+motCle;	
		}
	    // =================================================================
	    // affichage de la form pour saisi un nouvel utilisateur
	    // traiyement du get
	    // =================================================================
	    @RequestMapping(value = { "/userForm" }, method = RequestMethod.GET)    
		public String userForm(Model model) {
			model.addAttribute("usersecurity", new Usersecurity());
			return "user/userForm";
		}
	    // ==================================================================
	    // traitement de validation de la saisie d'un nouvel utilisateur
	    // traitement du post
	    // ==================================================================
	    @RequestMapping(value = { "/userSave" }, method = RequestMethod.POST)    
		public String userSave(@Valid Usersecurity usersecurity, 
				BindingResult bindingResult) throws Exception {	    	
			if(bindingResult.hasErrors()) {		
				 return "user/userForm";			 
				//return "redirect:/userForm";
			}
	    	// System.out.println("passage controller userSave");	
			
	    	// ================================================================================
			//le cryptage se fait dans la fonction save déclarée et programmée dans le service;
	    	// ================================================================================
	    	usersecurityService.saveUser(usersecurity);	
			return "redirect:/usersSearch";
		}
	    
	    // ==================================================================
	    // traitement de validation de la saisie d'un nouvel utilisateur
	    // traitement du post
	    // ================================================================== 
	    @RequestMapping(value = { "/test" }, method = RequestMethod.GET)
	    public static void test() {
	    	
	    	org.springframework.security.crypto.password.PasswordEncoder encoder
	    	   = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

	    	for (int i = 0; i < 5; i++) {
	    	      // "123456" - plain text - user input from user interface
	    	      String passwd = encoder.encode("123456");

	    	      // passwd - password from database
	    	      System.out.println(passwd); // print hash

	    	      // true for all 5 iteration
	    	      System.out.println(encoder.matches("123456", passwd));
	    	    }

	      }
	    
	    
	   // ===============================================================================================
	    /*
	    import com.example.model.User;
		import com.example.service.UserService;  = UsersecurityServiceInterface
	    
	    //@RequestMapping(value = "/registration", method = RequestMethod.POST)
		//public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		//	ModelAndView modelAndView = new ModelAndView();
			User userExists = userService.findUserByEmail(user.getEmail());
			if (userExists != null) {
				bindingResult
						.rejectValue("email", "error.user",
								"There is already a user registered with the email provided");
			}
			if (bindingResult.hasErrors()) {
				modelAndView.setViewName("registration");
			} else {
				userService.saveUser(user);
				modelAndView.addObject("successMessage", "User has been registered successfully");
				modelAndView.addObject("user", new User());
				modelAndView.setViewName("registration");
				
			}
			return modelAndView;
		}

		*/
	    

}
