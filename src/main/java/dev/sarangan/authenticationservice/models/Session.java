package dev.sarangan.authenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Session extends BaseModel{
    /*
    *   User : Session
    *       1 : M
    *       1 : 1
    *   ----------------
    *       1 : M
    *   ----------------
    *
    * On the M side, create id of the other table.
    * */
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    String token;
}
