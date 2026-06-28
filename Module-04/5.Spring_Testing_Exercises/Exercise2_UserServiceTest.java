import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock UserRepository userRepository;
    @InjectMocks UserService userService;

    @Test
    void testGetUserById() {
        User user=new User();
        user.setId(1L);
        user.setName("Krishna");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result=userService.getUserById(1L);
        assertEquals("Krishna",result.getName());
    }
}