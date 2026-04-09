package app.oworld.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "expiers_at")
    private LocalDateTime expiersAt;

    @Column(name = "revoked")
    private Boolean revoked = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "user_agent")
    private String userAgent;

    @PrePersist
    public void prePersist(){
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

}