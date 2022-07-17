package com.inn.iks.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    private String username;
    private String password;
    private String email;
    private boolean isDeleted;
    private boolean isDisabled; 
//    private String role;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // Quan hệ n-n với đối tượng ở dưới (role) (1 người có nhiều role)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Không sử dụng trong toString()

    @JoinTable(name = "user_role", //Tạo ra một join Table tên là "user_role"
            joinColumns = @JoinColumn(name = "user_id"),  // Trong đó, khóa ngoại chính là user_id trỏ tới class hiện tại (User)
            inverseJoinColumns = @JoinColumn(name = "role_id") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới (Role)
    )
    private Collection<Role> roles;

//    // mappedBy trỏ tới tên biến users ở trong Role.
//    @ManyToMany(mappedBy = "users")
//    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
//    @EqualsAndHashCode.Exclude
//    @Exclude
//    private Collection<Role> roles;
}
