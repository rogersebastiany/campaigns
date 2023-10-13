package br.com.uol.campaigns.auth;

import br.com.uol.campaigns.service.TokenService;
import br.com.uol.campaigns.user.IUserRepository;
import br.com.uol.campaigns.user.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/campanhas")){
            var auth = request.getHeader("Authorization");

            String token = auth.substring("Bearer".length()).trim();

            var subject = this.tokenService.getSubject(token);
            // se subject esta vazio, sai
            if (subject == null) {
                response.sendError(401);
            } else {
                UserModel userModel = this.userRepository.findByUsername(subject);
                // se usuario nao foi encontrado
                if (userModel == null) {
                    response.sendError(401);
                } else {
                    // Usuario encontrado.
                    // ate o momento nao se tem certeza se tem permissao na rota em questao.
                    // por isso passei o id do usuario para ser tratado no controller.
                    request.setAttribute("idUser", userModel.getId());
                    filterChain.doFilter(request, response);
                }
            }
        } else {
            // rotas de login ou cadastro de usuario estao abertas
            filterChain.doFilter(request, response);
        }


    }

}
