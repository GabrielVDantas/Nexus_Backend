package br.com.nexus.Nexus.entity.project;

import br.com.nexus.Nexus.entity.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(length = 100, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(length = 100, nullable = false)
    private BigDecimal financialTarget;

    @ManyToOne
    private Account account;
}
