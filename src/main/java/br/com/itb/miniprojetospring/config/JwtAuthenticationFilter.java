package br.com.itb.miniprojetospring.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.getEmailFromToken(token);
                String tipoUsuario = jwtUtil.getTipoUsuarioFromToken(token);
                Long id = jwtUtil.getIdFromToken(token);
                
                // Adiciona informações do usuário no request
                request.setAttribute("userEmail", email);
                request.setAttribute("userType", tipoUsuario);
                request.setAttribute("userId", id);
            }
        }
        
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Não aplica filtro para endpoints públicos
        return path.startsWith("/auth/") || 
               path.startsWith("/alunos/esqueci-senha") ||
               path.startsWith("/gerenciadores/esqueci-senha") ||
               path.startsWith("/administradores/esqueci-senha");
    }
}