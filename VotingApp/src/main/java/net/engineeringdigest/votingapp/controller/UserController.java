package net.engineeringdigest.votingapp.controller;


import net.engineeringdigest.votingapp.model.Candidate;
import net.engineeringdigest.votingapp.model.User;
import net.engineeringdigest.votingapp.service.CandidateService;
import net.engineeringdigest.votingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CandidateService candidateService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            if (user.isAdmin()) {
                return "redirect:/admin/home";
            } else {
                return "redirect:/voting";
            }
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String emailId, @RequestParam String phoneNo, Model model) {

        User users = userService.findByUsername(username);
        if (users != null && users.getPassword().equals(password)) {
            model.addAttribute("error", "You are already registered");
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmailId(emailId);
        user.setPhoneNo(phoneNo);
        user.setAdmin(false); // Default to regular user
        user.setHasVoted(null);
        userService.save(user);
        return "redirect:/login";

    }

    @GetMapping("/voting")
    public String voting(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && !user.isAdmin()) {
            model.addAttribute("message", "You can vote any one of your favourite candidate");
            List<Candidate> candidates = candidateService.findAll();
            model.addAttribute("candidates", candidates);
            return "votingPage";
        }
        return "redirect:/login";
    }

    @PostMapping("/vote")
    public String vote(@RequestParam String candidateId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getHasVoted()==null) {
            Candidate candidate = candidateService.findById(candidateId);
            if (candidate != null) {
                user.setHasVoted(candidate.getName());
                userService.save(user);

                candidate.setVoteCount(candidate.getVoteCount() + 1);
                candidateService.save(candidate);
            }
            model.addAttribute("message", "Thanks for your vote, " + user.getUsername() + "!");
        } else {
            model.addAttribute("message", "You already voted");
        }
        List<Candidate> candidates = candidateService.findAll();
        model.addAttribute("candidates", candidates);
        return "votingPage";
    }

    @GetMapping("/admin/home")
    public String adminHome(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.isAdmin()) {
            List<Candidate> candidates = candidateService.findAll();
            model.addAttribute("candidates", candidates);

            List<User> users = userService.findAll();
            model.addAttribute("users", users);
            return "adminHome";
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
