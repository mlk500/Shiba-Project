package sheba.backend.app.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sheba.backend.app.entities.Admin;
import sheba.backend.app.repositories.AdminRepository;

@Service
public class CustomAdminDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;

    public CustomAdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findAdminByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomAdminDetails(admin);
    }
}
