//package ru.pavel.bootstrap.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//import ru.pavel.bootstrap.config.exception.LoginException;
//
//import javax.servlet.http.HttpSession;
//
//@Service
//public class AppServiceImpl implements AppService{
//    @Override
//    public void authenticateOrLogout(Model model, HttpSession session, LoginException authenticationException, String authenticationName) {
//        if (authenticationException != null) {
//            try {
//                model.addAttribute("authenticationException", authenticationException);
//                session.removeAttribute("Authentication-Exception");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            model.addAttribute("authenticationException", new LoginException(null));
//        }
//
//        if (authenticationName != null) {
//            try {
//                model.addAttribute("authenticationName", authenticationName);
//                session.removeAttribute("Authentication-Name");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
