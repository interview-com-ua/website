package ua.com.itinterview.web.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.service.ResetPasswordService;
import ua.com.itinterview.service.UserService;
import ua.com.itinterview.web.command.ChangePasswordCommand;
import ua.com.itinterview.web.command.ResetPasswordCommand;
import ua.com.itinterview.web.command.UserCommand;
import ua.com.itinterview.web.command.UserEditProfileCommand;
import ua.com.itinterview.web.resource.viewpages.ModeView;
import ua.com.itinterview.web.security.AuthenticationUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserResource extends ValidatedResource
{

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationUtils authenticationUtils;
    @Autowired
    private ResetPasswordService resetPasswordService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView getSignupUserPage()
    {
        return goToSignupPageWithCommand(new UserCommand(), ModeView.CREATE);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createUser(@Valid @ModelAttribute UserCommand userCommand,
                                   BindingResult bindResult, HttpServletRequest request)
    {
        if (bindResult.hasErrors())
        {
            return goToSignupPageWithCommand(userCommand, ModeView.CREATE);
        }
        UserCommand newUserCommand = userService.createUser(userCommand);
        authenticationUtils.loginUser(userCommand.getEmail(), userCommand.getPassword(), request);
        return new ModelAndView("redirect:/user/" + newUserCommand.getId()
                + "/view");

    }

    @PreAuthorize("#userId == principal.info.id")
    @RequestMapping(value = "/user/{id}/view", method = RequestMethod.GET)
    public String getViewUser(@PathVariable("id") Integer userId,
                              Model model)
    {
        UserCommand userCommand = userService.getUserById(userId);
        model.addAttribute("userCommand", userCommand);
        model.addAttribute("changePasswordCommand", new ChangePasswordCommand());
        return "profile_page";
    }

    @PreAuthorize("#userId == principal.info.id")
    @RequestMapping(value = "/user/{id}/edit", method = RequestMethod.GET)
    public String getEditUser(@PathVariable("id") Integer userId,
                              Map<String, Object> map)
    {
        UserEditProfileCommand userEditProfileCommand = new UserEditProfileCommand(
                userService.getUserById(userId));
        map.put("userEditProfileCommand", userEditProfileCommand);
        return "profile_page";
    }

    @RequestMapping(value = "/user/{id}/save", method = RequestMethod.POST)
    public String saveUser(@PathVariable("id") int userId, @Valid @ModelAttribute UserEditProfileCommand userEditProfileCommand,
                           BindingResult bindResult, HttpServletRequest request, Map<String, Object> map)
    {
        if (bindResult.hasErrors())
        {
            map.put("UserEditProfileCommand", userEditProfileCommand);
            return "profile_page";
        }

        userService.updateUser(userId, userEditProfileCommand);
        return "redirect:/user/" + userId + "/view";
    }

    @PreAuthorize("#userId == principal.info.id")
    @RequestMapping(value = "/user/{id}/change_password", method = RequestMethod.POST)
    public String changePassword(@PathVariable("id") Integer userId, @Valid @ModelAttribute ChangePasswordCommand
            changePasswordCommand, BindingResult bindResult, Map<String, Object> map){

        if (bindResult.hasErrors())
        {
            map.put("changePasswordCommand", changePasswordCommand);
            return "profile_page";
        }
        userService.updatePassword(userId, changePasswordCommand);
        return "redirect:/user/" + userId + "/view";
    }

    private ModelAndView goToSignupPageWithCommand(UserCommand userCommand,
                                                   ModeView modeView)
    {
        ModelAndView view = new ModelAndView("signup");
        view.addObject(userCommand);
        view.addObject("mode", modeView);
        return view;
    }

    @RequestMapping(value = "/reset_password", method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute("email") String email){
        resetPasswordService.createHash(email);
        return "reset_password_info";
    }

    @RequestMapping(value = "/confirm_reset_password/{hash}", method = RequestMethod.GET)
    public String getConfirmResetPassword(@PathVariable String hash, Model model, HttpServletResponse response){
        model.addAttribute("hash", hash);
        if (!hash.equals("111111"))
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "confirm_reset_password";
    }

    @RequestMapping(value = "/confirm_reset_password", method = RequestMethod.POST)
    public String confirmResetPassword(@ModelAttribute ResetPasswordCommand resetPasswordCommand){

        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand();
        changePasswordCommand.setOldPassword("password");
        changePasswordCommand.setNewPassword(resetPasswordCommand.getPassword());
        changePasswordCommand.setConfirmPassword(resetPasswordCommand.getConfirmPassword());

        int userId = 10;
        userService.updatePassword(userId, changePasswordCommand);
        return "confirm_reset_password_success";
    }
}
