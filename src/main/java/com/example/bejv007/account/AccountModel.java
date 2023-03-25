package com.example.bejv007.account;


import com.example.bejv007.user.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private UserEntity user;
    private BigDecimal brlBalance;
    private BigDecimal btcBalance;
}
