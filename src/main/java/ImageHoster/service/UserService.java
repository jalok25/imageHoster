package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Call the registerUser() method in the UserRepository class to persist the user record in the database
    public void registerUser(User newUser) {
        userRepository.registerUser(newUser);
    }

    //Since we did not have any user in the database, therefore the user with username 'upgrad' and password 'password' was hard-coded
    //This method returned true if the username was 'upgrad' and password is 'password'
    //But now let us change the implementation of this method
    //This method receives the User type object
    //Calls the checkUser() method in the Repository passing the username and password which checks the username and password in the database
    //The Repository returns User type object if user with entered username and password exists in the database
    //Else returns null
    public User login(User user) {
        User existingUser = userRepository.checkUser(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            return existingUser;
        } else {
            return null;
        }
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
        while (i < password.length())
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
