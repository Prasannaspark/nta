//package com.prodapt.networkticketingapplicationproject.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.prodapt.networkticketingapplicationproject.entities.ERole;
//import com.prodapt.networkticketingapplicationproject.entities.Role;
//import com.prodapt.networkticketingapplicationproject.entities.UserEntity;
//import com.prodapt.networkticketingapplicationproject.exceptions.RoleNotFoundException;
//import com.prodapt.networkticketingapplicationproject.security.jwt.JwtUtils;
//import com.prodapt.networkticketingapplicationproject.security.payload.request.LoginRequest;
//import com.prodapt.networkticketingapplicationproject.security.payload.request.SignupRequest;
//import com.prodapt.networkticketingapplicationproject.security.payload.response.JwtResponse;
//import com.prodapt.networkticketingapplicationproject.security.payload.response.MessageResponse;
//import com.prodapt.networkticketingapplicationproject.security.service.UserDetailsImpl;
//import com.prodapt.networkticketingapplicationproject.service.RoleService;
//import com.prodapt.networkticketingapplicationproject.service.UserEntityService;
//
//public class AuthControllerTest {
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private PasswordEncoder encoder;
//
//    @Mock
//    private JwtUtils jwtUtils;
//
//    @Mock
//    private UserEntityService userEntityService;
//
//    @Mock
//    private RoleService roleService;
//
//    @InjectMocks
//    private AuthController authController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testAuthenticateUser_Success() {
//        // Setup
//        LoginRequest loginRequest = new LoginRequest("validUsername", "validPassword");
//        Authentication authentication = mock(Authentication.class);
//        when(authenticationManager.authenticate(any())).thenReturn(authentication);
//        when(jwtUtils.generateJwtToken(authentication)).thenReturn("mocked_jwt_token");
//        UserDetailsImpl userDetails = new UserDetailsImpl(1, "validUsername", "email", "password" ,mock(GrantedAuthority.class));
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//
//        // Execute
//        ResponseEntity<?> responseEntity = authController.authenticateUser(loginRequest);
//
//        // Verify
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody() instanceof JwtResponse);
//        JwtResponse jwtResponse = (JwtResponse) responseEntity.getBody();
//        assertEquals(1, jwtResponse.getId());
//        assertEquals("validUsername", jwtResponse.getUsername());
//        assertEquals("email", jwtResponse.getEmail());
//        assertNotEquals("ROLE_USER", jwtResponse.getRole());
//    }
//
//    @Test
//    void testAuthenticateUser_WithInvalidCredentials() {
//        LoginRequest loginRequest = new LoginRequest("username", "password");
//        // Mock necessary dependencies
//        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException());
//        // Test
//        assertThrows(RuntimeException.class, () -> authController.authenticateUser(loginRequest));
//    }
//
//    @Test
//    void testRegisterUser_WithValidRequest() throws RoleNotFoundException {
//        SignupRequest signupRequest = new SignupRequest("username", "email@example.com", "password");
//        // Mock necessary dependencies
//        when(userEntityService.existsByUsername(signupRequest.getUsername())).thenReturn(false);
//        when(userEntityService.existsByEmail(signupRequest.getEmail())).thenReturn(false);
//        when(roleService.findRoleByName(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(1, ERole.ROLE_USER)));
//        when(userEntityService.addUserEntity(any())).thenReturn(new UserEntity());
//        // Test
//        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
//        assertNotNull(responseEntity.getBody());
//        assertTrue(responseEntity.getBody() instanceof MessageResponse);
//    }
//
//    @Test
//    void testRegisterUser_WithExistingUsername() throws RoleNotFoundException {
//        SignupRequest signupRequest = new SignupRequest("username", "email@example.com", "password");
//        // Mock necessary dependencies
//        when(userEntityService.existsByUsername(signupRequest.getUsername())).thenReturn(true);
//        // Test
//        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
//        assertNotNull(responseEntity.getBody());
//        assertTrue(responseEntity.getBody() instanceof MessageResponse);
//        assertEquals("Error: Username is already taken!", ((MessageResponse) responseEntity.getBody()).getMessage());
//    }
//
//    @Test
//    void testRegisterUser_WithExistingEmail() throws RoleNotFoundException {
//        SignupRequest signupRequest = new SignupRequest("username", "email@example.com", "password");
//        // Mock necessary dependencies
//        when(userEntityService.existsByUsername(signupRequest.getUsername())).thenReturn(false);
//        when(userEntityService.existsByEmail(signupRequest.getEmail())).thenReturn(true);
//        // Test
//        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
//        assertNotNull(responseEntity.getBody());
//        assertTrue(responseEntity.getBody() instanceof MessageResponse);
//        assertEquals("Error: Email is already in use!", ((MessageResponse) responseEntity.getBody()).getMessage());
//    }
//    
//    @Test
//    void testRegisterUser_PasswordEncoding() throws RoleNotFoundException {
//        // Setup
//        SignupRequest signupRequest = new SignupRequest("username", "email@example.com", "password123");
//        when(userEntityService.existsByUsername(signupRequest.getUsername())).thenReturn(false);
//        when(userEntityService.existsByEmail(signupRequest.getEmail())).thenReturn(false);
//        when(roleService.findRoleByName(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(1, ERole.ROLE_USER)));
//        when(encoder.encode(signupRequest.getPassword())).thenReturn("encodedPassword");
//
//        // Execute
//        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
//
//        // Verify
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody() instanceof MessageResponse);
//        assertEquals("User registered successfully!", ((MessageResponse) responseEntity.getBody()).getMessage());
//        verify(userEntityService, times(1)).addUserEntity(any());
//        verify(encoder, times(1)).encode(signupRequest.getPassword());
//    }
//
//}