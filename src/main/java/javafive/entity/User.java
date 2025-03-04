package javafive.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Users")
public class User {
	@Id
	@NotBlank(message = "Tên đăng nhập không được để trống")
	private String username;
	private String password;
	private boolean enabled;
	@NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 3, max = 50, message = "Họ và tên phải từ 3 đến 50 ký tự")
	private String fullname;
	@NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0[3|5|7|8|9])[0-9]{8}$", message = "Số điện thoại không hợp lệ")
	private String mobile;
	private String photo ;
	@NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
	private String email;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)

	private List <Authority> authorities;
	@Override
	public String toString() {
	    return "User{username='" + username + "', fullname='" + fullname + "', enabled=" + enabled + "}";
	}

}
