package com.majorczyk.endpoints;

import com.majorczyk.database.UserRepository;
import com.majorczyk.exceptions.ServiceFault;
import com.majorczyk.exceptions.ServiceFaultException;
import com.majorczyk.model.User;
import com.majorczyk.security.TokenGenerator;
import com.majorczyk.security.ValidationUtils;
import com.majorczyk.soap.generated.Login;
import com.majorczyk.soap.generated.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by Piotr on 2018-01-26.
 */
@Endpoint
public class LoginEndpoint {

    private final String NAMESPACE = "com/majorczyk/soap/account";

    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    UserRepository userRepository;

    /**
     * Funkcja obslugujaca logowanie
     * @param request obiekt zadania zawierajacy login oraz haslo uzytkownika
     * @return token wygenerowany przez serwer uzywany w dalszej uwierzytelnionej komunikacji
     */
    @PayloadRoot(namespace = NAMESPACE, localPart = "Login")
    @ResponsePayload
    public LoginResponse getToken(@RequestPayload Login request) {
        LoginResponse response = new LoginResponse();
        User user = userRepository.findByLogin(request.getLogin());
        if (user != null) {
//            Auth auth = request.getAuth();
            if (user.getLogin().equals(request.getLogin()) && ValidationUtils.validatePassword(user.getPassword(), request.getPassword())) {
                String token = null;
                try {
                    token = tokenGenerator.encrypt(user.getLogin());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.setToken(token);
            } else {
                throw new ServiceFaultException("ERROR", new ServiceFault("400", "Niepoprawny dane logowania"));
            }
        } else {
            throw new ServiceFaultException("ERROR", new ServiceFault("404", "Uzytkownik nie istnieje"));
        }

        return response;
    }
}
