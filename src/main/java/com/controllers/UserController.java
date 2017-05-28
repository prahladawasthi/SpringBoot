package com.controllers;

import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Value("${user.email.already.exist}")
	private String errorMessage;

	@Value("${user.deleted}")
	private String deletedUserMessage;

	@RequestMapping(value = "/admin")
	public String adminPage(Model model) {

		return "admin";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	@RequestMapping("user")
	public String userHome(Model model) {
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		System.out.println(authorities.toString());
		model.addAttribute("user", new User());
		model.addAttribute("userRoles", userService.findUserRoles());
		model.addAttribute("userList", userService.findAllUsers());

		return "user";
	}

	@RequestMapping(value = "/addUser")
	public String addUser(@Valid User user, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

		} else if (userService.isUserExist(user)) {
			model.addAttribute("errorMessage", this.errorMessage);
		} else {
			userService.saveOrUpdateUser(user);
			model.addAttribute("user", new User());
		}
		model.addAttribute("userRoles", userService.findUserRoles());
		model.addAttribute("userList", userService.findAllUsers());

		return "user";
	}

	@RequestMapping(value = "/search")
	public String searchUser(Model model, @RequestParam String search) {
		model.addAttribute("user", new User());
		model.addAttribute("userList", userService.find(search));

		return "user";
	}

	@RequestMapping(value = "/delete")
	public String deleteUser(Model model, @RequestParam String id) {
		model.addAttribute("user", new User());
		model.addAttribute("deletedUser", userService.deleteUserById(id).getFirstName());
		model.addAttribute("deletedUserMessage", this.deletedUserMessage);
		model.addAttribute("userList", userService.findAllUsers());
		return "user";
	}

	@RequestMapping(value = "/edit")
	public String editUser(Model model, @RequestParam String id) {
		model.addAttribute("user", userService.findByID(id));
		model.addAttribute("userRoles", userService.findUserRoles());
		model.addAttribute("userList", userService.findAllUsers());
		model.addAttribute("edit", true);
		return "user";
	}

	@RequestMapping(value = "/update")
	public String updateUser(@Valid User user, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

		} else {
			userService.saveOrUpdateUser(user);
			model.addAttribute("user", new User());
		}
		model.addAttribute("userRoles", userService.findUserRoles());
		model.addAttribute("userList", userService.findAllUsers());
		model.addAttribute("edit", false);
		return "user";
	}

}