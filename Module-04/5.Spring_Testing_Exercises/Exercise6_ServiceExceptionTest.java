import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

class ServiceExceptionTest {
 @Test
 void testMissingUser(){
   UserRepository repo=mock(UserRepository.class);
   UserService service=new UserService();
   org.springframework.test.util.ReflectionTestUtils.setField(service,"userRepository",repo);
   when(repo.findById(10L)).thenReturn(Optional.empty());
   assertNull(service.getUserById(10L));
 }
}