import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.NoSuchElementException;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(GlobalExceptionHandler.class)
class ControllerAdviceTest{
 @Autowired MockMvc mockMvc;
 @MockBean UserService service;

 @Test
 void testException() throws Exception{
   when(service.getUserById(1L)).thenThrow(new NoSuchElementException());
   mockMvc.perform(get("/users/1"))
     .andExpect(status().isNotFound())
     .andExpect(content().string("User not found"));
 }
}