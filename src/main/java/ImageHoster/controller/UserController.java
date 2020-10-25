package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.model.UserProfile;
import ImageHoster.service.ImageService;
import ImageHoster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    //This controller method is called when the request pattern is of type 'users/registration'
    //This method declares User type and UserProfile type object
    //Sets the user profile with UserProfile type object
    //Adds User type object to a model and returns 'users/registration.html' file
    @RequestMapping("users/registration")
    public String registration(Model model) {
        User user = new User();
        UserProfile profile = new UserProfile();
        user.setProfile(profile);
        model.addAttribute("User", user);
        return "users/registration";
    }

    //This controller method is called when the request pattern is of type 'users/registration' and also the incoming request is of POST type
    //This method calls the business logic and after the user record is persisted in the database, directs to login page
    @RequestMapping(value = "users/registration", method = RequestMethod.POST)
    public String registerUser(User user, Model model) {
        String password = user.getPassword();
        boolean correct_password = passwordCriteriaSatisfied(password);
        if(correct_password) {
            userService.registerUser(user);
            return "users/login";
        }
        else {
            String errorMessage = "Password must contain atleast 1 alphabet, 1 number & 1 special character";
            UserProfile profile = new UserProfile();
            user.setProfile(profile);
            model.addAttribute("User", user);
            model.addAttribute("passwordTypeError", errorMessage);
            return "users/registration";
        }
    }

    //This controller method is called when the request pattern is of type 'users/login'
    @RequestMapping("users/login")
    public String login() {
        return "users/login";
    }

    //This controller method is called when the request pattern is of type 'users/login' and also the incoming request is of POST type
    //The return type of the business logic is changed to User type instead of boolean type. The login() method in the business logic checks whether the user with entered username and password exists in the database and returns the User type object if user with entered username and password exists in the database, else returns null
    //If user with entered username and password exists in the database, add the logged in user in the Http Session and direct to user homepage displaying all the images in the application
    //If user with entered username and password does not exist in the database, redirect to the same login page
    @RequestMapping(value = "users/login", method = RequestMethod.POST)
    public String loginUser(User user, HttpSession session) {
        User existingUser = userService.login(user);
        if (existingUser != null) {
            session.setAttribute("loggeduser", existingUser);
            return "redirect:/images";
        } else {
            return "users/login";
        }
    }

    //This controller method is called when the request pattern is of type 'users/logout' and also the incoming request is of POST type
    //The method receives the Http Session and the Model type object
    //session is invalidated
    //All the images are fetched from the database and added to the model with 'images' as the key
    //'index.html' file is returned showing the landing page of the application and displaying all the images in the application
    @RequestMapping(value = "users/logout", method = RequestMethod.POST)
    public String logout(Model model, HttpSession session) {
        session.invalidate();

        List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "index";
    }

    //This function is called at the time of user registration
    //It'll check the minimum password criteria that has to be satisfied
    //In order to successfully register a User.
    //It'll take the password as the input and do necessary check and if the condition satisfies
    //it'll return true otherwise false.
    public boolean passwordCriteriaSatisfied(String password) {
        if(password.length() < 3) {
            return  false;
        }
        boolean hasChar = false;
        boolean hasInt = false;
        boolean hasSpecialChar = false;
        String specialCharacters = "!@#$%^&*()-+',./:;<=>?[]_`{|}";
        int i = 0;
        while (i <  password.length())
        {
            if (Character.isDigit(password.charAt(i)))
            {
                hasInt = true;
            }
            else if (Character.isAlphabetic(password.charAt(i))) {
                hasChar = true;
            }
            else if (specialCharacters.contains(Character.toString(password.charAt(i)))) {
                hasSpecialChar = true;
            }
            if (hasChar && hasInt && hasSpecialChar)
            {
                return true;
            }
            i++;
        }
        return false;
    }
}
