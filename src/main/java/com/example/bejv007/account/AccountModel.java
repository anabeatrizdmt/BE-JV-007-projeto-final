package com.example.bejv007.account;


import com.example.bejv007.user.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "db_account")

public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    private BigDecimal brlBalance;
    @Column(precision = 16, scale = 8)
    private BigDecimal btcBalance;
}
