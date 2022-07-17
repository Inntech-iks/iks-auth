package com.inn.iks.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "roles")
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    private String name;
    private String description;

  // mappedBy trỏ tới tên biến roles ở trong User.
  @ManyToMany(mappedBy = "roles")
  // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Collection<User> users;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    // Quan hệ n-n với đối tượng ở dưới (users) (1 role có nhiều người)
//    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
//    @ToString.Exclude // Không sử dụng trong toString()
//
//    @JoinTable(name = "user_role", //Tạo ra một join Table tên là "address_person"
//            joinColumns = @JoinColumn(name = "role_id"),  // TRong đó, khóa ngoại chính là address_id trỏ tới class hiện tại (Role)
//            inverseJoinColumns = @JoinColumn(name = "user_id") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới (User)
//    )
//    private Collection<User> users;

  public Role (long id, String name, String description) {
	this.id = id;
	this.name = name;
	this.description = description;
  }
  
  @Override
  public String toString() {
	  return name ;
  }
}
