package gd.software.financial_manager.infrastructure.persistence.relational;

import gd.software.financial_manager.infrastructure.utils.PasswordUtil;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "gd_user")
public class UserRow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @PrePersist
    @PreUpdate
    private void encryptPassword() {
        if (this.password != null) {
            this.password = PasswordUtil.encryptPassword(this.password);
        }
    }

}
