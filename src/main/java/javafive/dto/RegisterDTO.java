package javafive.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RegisterDTO {

    @Size(min = 5, max = 99, message = "Username phải từ 5 đến 99 ký tự")
    private String username;

    @Size(min = 5, max = 99, message = "Mật khẩu phải từ 5 đến 99 ký tự")
    private String password;

    private String confirmPassword;

    @Builder.Default
    private String fullname = "Người dùng mới";

    @Builder.Default
    @Pattern(regexp = "^(0[3|5|7|8|9])[0-9]{8}$", message = "Số điện thoại không hợp lệ")
    private String mobile = "0369123456"; 


    @Builder.Default
    private String photo = "default-avatar.png";

    @Builder.Default
    @Email(message = "Email không hợp lệ")
    private String email = "example@example.com";
}
