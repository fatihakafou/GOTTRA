package com.robert.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.robert.model.Usersecurity;
import com.robert.model.Usersroles;
import com.robert.repository.UsersrolesRepository;

@Controller
public class RolesController {
	
	@Autowired
	private UsersrolesRepository usersrolesRepository;
	
	
	// ==========================================================
    // liste des roles
    // ==========================================================
    @RequestMapping(value = { "/usersRoles" })    
	public String userRoles(Model model,
			@RequestParam(name="page", defaultValue="0") int p,
			@RequestParam(name="motCle", defaultValue="") String mc) {  
    	Page<Usersroles> rolesPages = usersrolesRepository.findroles("%"+mc+"%", new PageRequest(p,4)); 
		int pagesCount = rolesPages.getTotalPages();
		int[] pages= new int[pagesCount] ;
		for (int i=0; i<pagesCount;i++) pages[i]=i;
		model.addAttribute("pages", pages);
		model.addAttribute("size", 5);
		model.addAttribute("pageCourante", p);
		model.addAttribute("Usersroles", rolesPages); 
		model.addAttribute("motCle", mc);
		return "user/userRoles";
	}
    // =================================================================
    // suppression d'un role à l'utilisateur
    // =================================================================
    @RequestMapping(value = { "/roleDelete" }) 
	public String roleDelete(int id,String motCle, int page, int size) {
    	System.out.println("=============================================");
    	System.out.println("roleDelete, id=" + id);
    	System.out.println("=============================================");	
    	usersrolesRepository.delete(id );
		return "redirect:/usersRoles?page="+page+"&size="+size+"&motCle="+motCle;	
	}
	
    // =================================================================
    // affichage de la form pour saisi un nouvel utilisateur
    // traiyement du get
    // =================================================================
    @RequestMapping(value = { "/roleForm" }, method = RequestMethod.GET)    
	public String userForm(Model model) {
		model.addAttribute("usersroles", new Usersroles());
		return "user/roleForm";
    }
    
    // ==================================================================
    // traitement de validation de la saisie d'un nouveau role 
    // traitement du post
    // ==================================================================
    // Méthode avec gestion des erreurs manuels
    /*
    @RequestMapping(value = { "/roleSave" }, method = RequestMethod.POST)    
	public String userSave(@Valid Usersroles role, 
			BindingResult bindingResult,Model model) throws Exception {
    	System.out.println("=============================================");
    	System.out.println(bindingResult.hasErrors());
    	System.out.println("=============================================");
		if(bindingResult.hasErrors()) {
			model.addAttribute("role",role);
			model.addAttribute("errorMessage", "erreur de saisie");
			 return "user/roleForm";
			 
			//return "redirect:/userForm";
		}	
		usersrolesRepository.save(role); 
		return "redirect:/usersRoles";
	}
    **/
    @RequestMapping(value = { "/roleSave" }, method = RequestMethod.POST)    
	public String userSave(@Valid Usersroles usersroles, 
			BindingResult bindingResult) throws Exception {
    	
		if(bindingResult.hasErrors()) {
			 return "user/roleForm";
		}	
		usersrolesRepository.save(usersroles); 
		return "redirect:/usersRoles";
	}
    

}
